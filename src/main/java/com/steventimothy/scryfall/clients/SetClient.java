package com.steventimothy.scryfall.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SetClient extends BaseClient {

  /**
   * The path to get to the sets.
   */
  private static final String SETS_PATH = "/sets";

  /**
   * Gets the list of sets returned from scryfall.
   * @return A list of set maps returned from scryfall.
   */
  public List<Map<String, Object>> getSets() {
    checkForRequestedTimeout();
    List<Map<String, Object>> sets = (List<Map<String, Object>>) restTemplate.exchange(RequestEntity.get(UriComponentsBuilder.fromUriString(getBasePath() + SETS_PATH)
          .build().toUri())
          .accept(MediaType.APPLICATION_JSON)
          .build(), Map.class).getBody().get("data");

    if (sets != null) {
      return sets;
    }
    else {
      return new ArrayList<>();
    }
  }

  /**
   * Gets the svg icon for the set from scryfall.
   * @param uri The uri to the svg icon file.
   * @return The bytes of the svg icon.
   */
  public byte[] getSetIcon(String uri) {
    checkForRequestedTimeout();
    return restTemplate.getForObject(uri, byte[].class);
  }


  @Autowired
  public SetClient(RestTemplate restTemplate) {
    super(restTemplate);
  }
}
