package com.steventimothy.scryfall.services;

import com.steventimothy.scryfall.clients.SetClient;
import com.steventimothy.scryfall.schemas.sets.ScrySet;
import com.steventimothy.scryfall.schemas.sets.ScrySetIcon;
import com.steventimothy.scryfall.schemas.sets.ScrySetImportDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Component
public class SetService {

  /**
   * The client used to request set information.
   */
  private SetClient setClient;

  /**
   * Gets the import details including future releases without downloading the icons.
   * @return The set details obtained from scryfall.
   */
  public ScrySetImportDetails getImportDetails() {
    return getImportDetails(null, false);
  }

  /**
   * Gets the import details without downloading the icons.
   * @param excludeFutureReleases Flag to exclude future releases.
   * @return The set details obtained from scryfall.
   */
  public ScrySetImportDetails getImportDetails(boolean excludeFutureReleases) {
    return getImportDetails(null, excludeFutureReleases);
  }

  /**
   * Gets the import details including future releases.
   * @param iconDirPath The path to where to download the icon files.
   *                    If this path is null the icons will not be downloaded.
   * @return The set details obtained from scryfall.
   */
  public ScrySetImportDetails getImportDetails(String iconDirPath) {
    return getImportDetails(iconDirPath, false);
  }

  /**
   * Gets the import details.
   * @param iconDirPath The path to where to download the icon files.
   *                    If this path is null the icons will not be downloaded.
   * @param excludeFutureReleases Flag to exclude future releases.
   * @return The set details obtained from scryfall.
   */
  public ScrySetImportDetails getImportDetails(String iconDirPath, boolean excludeFutureReleases) {
    log.info("Fetching scryfall sets");
    List<ScrySet> sets = mapToSets(setClient.getSets(), excludeFutureReleases);
    Map<UUID, ScrySetIcon> setIconMap = null;

    if (iconDirPath != null) {
      setIconMap = getSetIcons(iconDirPath, sets);
    }

    return new ScrySetImportDetails()
          .sets(sets)
          .setIconMap(setIconMap);
  }

  /**
   * Maps the raw sets to ScrySet objects.
   * @param rawSets The raw set maps to map.
   * @param excludeFutureReleases The flag used to exclude future releases.
   * @return The list of ScrySets that were mapped. This will be ordered first by dependencies
   * (All non-dependent sets, meaning they do not have parent set ids, will be first. Followed by
   * all dependent sets.). Second they will be ordered withing there independent/dependent group by
   * release date.
   */
  private List<ScrySet> mapToSets(List<Map<String, Object>> rawSets, Boolean excludeFutureReleases) {
    return rawSets.stream()
          .map(rawSet -> {
            URI iconSvgUri = mapToUri((String) rawSet.get("icon_svg_uri"));
            Instant releasedAt = mapToDate((String) rawSet.get("released_at"));
            URI scryfallUri = mapToUri((String) rawSet.get("scryfall_uri"));
            URI searchUri = mapToUri((String) rawSet.get("search_uri"));
            URI uri = mapToUri((String) rawSet.get("uri"));

            return new ScrySet()
                  .block((String) rawSet.get("block"))
                  .blockCode((String) rawSet.get("block_code"))
                  .cardCount((Integer) rawSet.get("card_count"))
                  .code((String) rawSet.get("code"))
                  .digital((Boolean) rawSet.get("digital"))
                  .foilOnly((Boolean) rawSet.get("foil_only"))
                  .iconSvgUri(iconSvgUri)
                  .id(UUID.fromString((String) rawSet.get("id")))
                  .mtgoCode((String) rawSet.get("mtgo_code"))
                  .name((String) rawSet.get("name"))
                  .parentSetCode((String) rawSet.get("parent_set_code"))
                  .releasedAt(releasedAt)
                  .scryfallUri(scryfallUri)
                  .searchUri(searchUri)
                  .setType((String) rawSet.get("set_type"))
                  .tcgPlayerId((Integer) rawSet.get("tcgplayer_id"))
                  .uri(uri);
          })
          .filter(set -> (excludeFutureReleases && set.releasedAt().compareTo(Instant.now()) <= 0) || !excludeFutureReleases)
          .sorted(this::sortSets)
          .collect(Collectors.toList());
  }

  /**
   * Maps a string URI to the uri object.
   * @param uri The string representation of the uri.
   * @return The uri object for that string.
   */
  private URI mapToUri(String uri) {
    if (uri != null) {
      return URI.create(uri);
    }
    else {
      return null;
    }
  }

  /**
   * Maps the string date to an Instant.
   * @param date The date (GMC-8) string to map.
   * @return The instant of that date.
   */
  private Instant mapToDate(String date) {
    if (date != null) {
      return Instant.parse(date + "T00:00:00-08:00");
    }
    else {
      return null;
    }
  }

  /**
   * Helper function used to sort the sets.
   * @param scrySet1 The first set to compare.
   * @param scrySet2 The second set to compare.
   * @return -1 if set1 is considered < set2. 0 if they are the same. 1 if set2 is considered < set1.
   */
  private int sortSets(ScrySet scrySet1, ScrySet scrySet2) {
    if (scrySet1 == null && scrySet2 == null) {
      return 0;
    }
    else if (scrySet1 != null && scrySet2 == null) {
      return -1;
    }
    else if (scrySet1 == null) {
      return 1;
    }
    else if ((scrySet1.parentSetCode() != null && scrySet2.parentSetCode() != null) ||
          (scrySet1.parentSetCode() == null && scrySet2.parentSetCode() == null)) {
      return scrySet1.releasedAt().compareTo(scrySet2.releasedAt());
    }
    else if (scrySet1.parentSetCode() == null) {
      return -1;
    }
    else {
      return 1;
    }
  }

  /**
   * Retrieves icons of sets that are missing.
   * @param iconDirPath The path to download the set icon svg files to.
   * @param sets The sets to check for icons and download them if not.
   * @return A map where the key is the sets id, and the value is the path to
   * the already downloaded icon svg, or the newly downloaded svg.
   */
  private Map<UUID, ScrySetIcon> getSetIcons(String iconDirPath, List<ScrySet> sets) {
    log.info("Downloading missing set icons");
    Map<UUID, ScrySetIcon> setIconMap = new HashMap<>();

    for (ScrySet set : sets) {
      String iconFileName = getSetIconFileName(set.iconSvgUri());
      String iconPath = iconDirPath + "/" + iconFileName;

      if (!new File(iconPath).exists()) {
        log.info("Downloading {}", iconFileName);
        byte[] iconBytes = setClient.getSetIcon(set.iconSvgUri().toString());

        try {
          Files.write(Paths.get(iconPath), iconBytes);
        }
        catch (IOException ex) {
            log.error("Could not write svg file to: {}", iconPath);
        }
      }

        setIconMap.put(set.id(), new ScrySetIcon()
              .iconPath(iconPath)
              .iconUri(set.iconSvgUri()));
    }

    return setIconMap;
  }

  /**
   * Gets the icon's filename given its icon uri.
   * @param iconUri The uri to the icon svg.
   * @return The filename of the svg.
   * NOTE: there are sets that contain an icon file named con.svg.
   * This file is an invalid filename for Windows OS. So we rename this file
   * To be conflux.svg.
   */
  private String getSetIconFileName(URI iconUri) {
    String filename = iconUri.toString()
          .substring(0, iconUri.toString().indexOf('?'))
          .replace("https://img.scryfall.com/sets/", "");

    switch (filename) {
      case "con.svg":
        return "conflux.svg";
      default:
        return filename;
    }
  }
}
