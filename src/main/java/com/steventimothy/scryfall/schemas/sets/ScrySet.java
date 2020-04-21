package com.steventimothy.scryfall.schemas.sets;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

/**
 * Information pulled from https://scryfall.com/docs/api/sets
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@NoArgsConstructor
@Accessors(fluent = true)
@Data
public class ScrySet {

  /**
   * The block or group name code for this set, if any.
   */
  private String block;

  /**
   * The block code for this set, if any.
   */
  private String blockCode;

  /**
   * The number of cards in this set.
   */
  @NonNull
  private Integer cardCount;

  /**
   * The unique three to six-letter code for this set.
   */
  @NonNull
  private String code;

  /**
   * True if this set was only released on Magic Online.
   */
  @NonNull
  private Boolean digital;

  /**
   * True if this set contains only foil cards.
   */
  @NonNull
  private Boolean foilOnly;

  /**
   * A URI to an SVG file for this set’s icon on Scryfall’s CDN.
   */
  private URI iconSvgUri;

  /**
   * A unique ID for this set on Scryfall that will not change.
   */
  @NonNull
  private UUID id;

  /**
   * The unique code for this set on MTGO, which may differ from the regular code.
   */
  private String mtgoCode;

  /**
   * The English name of the set.
   */
  @NonNull
  private String name;

  /**
   * The set code for the parent set, if any. promo and token sets often have a parent set.
   */
  private String parentSetCode;

  /**
   * The date the set was released or the first card was printed in the set.
   */
  private Instant releasedAt;

  /**
   * A link to this set’s permapage on Scryfall’s website.
   */
  @NonNull
  private URI scryfallUri;

  /**
   * A Scryfall API URI that you can request to begin paginating over the cards in this set.
   */
  @NonNull
  private URI searchUri;

  /**
   * A computer-readable classification for this set.
   */
  @NonNull
  private ScrySetType setType;

  /**
   * This set’s ID on TCGplayer’s API, also known as the groupId.
   */
  private Integer tcgPlayerId;

  /**
   * A link to this set object on Scryfall’s API.
   */
  @NonNull
  private URI uri;
}
