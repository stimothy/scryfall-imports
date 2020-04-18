package com.steventimothy.scryfall.schemas.sets;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
@Accessors(fluent = true)
@Data
public class ScrySetImportDetails {

  /**
   * List of sets that were imported.
   */
  private List<ScrySet> sets;

  /**
   * A map of the set icons where the key is the scryfall id of the set and the
   * value is the SetIcon object.
   */
  private Map<UUID, ScrySetIcon> setIconMap;
}
