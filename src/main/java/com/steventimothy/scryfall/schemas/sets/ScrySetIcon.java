package com.steventimothy.scryfall.schemas.sets;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.net.URI;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
@Accessors(fluent = true)
@Data
public class ScrySetIcon {

  /**
   * The path to the downloaded svg icon file.
   */
  private String iconPath;

  /**
   * The uri to the svg icon.
   */
  private URI iconUri;
}
