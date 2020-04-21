package com.steventimothy.scryfall.schemas.sets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Accessors(fluent = true)
@Getter
public enum ScrySetType {

  CORE(1, "core"),
  EXPANSION(2, "expansion"),
  MASTERS(3, "masters"),
  MASTERPIECE(4, "masterpiece"),
  FROM_THE_VAULT(5, "from_the_vault"),
  SPELLBOOK(6, "spellbook"),
  PREMIUM_DECK(7, "premium_deck"),
  DUEL_DECK(8, "duel_deck"),
  DRAFT_INNOVATION(9, "draft_innovation"),
  TREASURE_CHEST(10, "treasure_chest"),
  COMMANDER(11, "commander"),
  PLANECHASE(12, "planechase"),
  ARCHENEMY(13, "archenemy"),
  VANGUARD(14, "vanguard"),
  FUNNY(15, "funny"),
  STARTER(16, "starter"),
  BOX(17, "box"),
  PROMO(18, "promo"),
  TOKEN(19, "token"),
  MEMORABILIA(20, "memorabilia");

  private final Integer id;
  private final String value;

  public static ScrySetType getInstance(Integer id) {
    if (id == null) {
      return null;
    }
    else if (CORE.id().equals(id)) {
      return CORE;
    }
    else if (EXPANSION.id().equals(id)) {
      return EXPANSION;
    }
    else if (MASTERS.id().equals(id)) {
      return MASTERS;
    }
    else if (MASTERPIECE.id().equals(id)) {
      return MASTERPIECE;
    }
    else if (FROM_THE_VAULT.id().equals(id)) {
      return FROM_THE_VAULT;
    }
    else if (SPELLBOOK.id().equals(id)) {
      return SPELLBOOK;
    }
    else if (PREMIUM_DECK.id().equals(id)) {
      return PREMIUM_DECK;
    }
    else if (DUEL_DECK.id().equals(id)) {
      return DUEL_DECK;
    }
    else if (DRAFT_INNOVATION.id().equals(id)) {
      return DRAFT_INNOVATION;
    }
    else if (TREASURE_CHEST.id().equals(id)) {
      return TREASURE_CHEST;
    }
    else if (COMMANDER.id().equals(id)) {
      return COMMANDER;
    }
    else if (PLANECHASE.id().equals(id)) {
      return PLANECHASE;
    }
    else if (ARCHENEMY.id().equals(id)) {
      return ARCHENEMY;
    }
    else if (VANGUARD.id().equals(id)) {
      return VANGUARD;
    }
    else if (FUNNY.id().equals(id)) {
      return FUNNY;
    }
    else if (STARTER.id().equals(id)) {
      return STARTER;
    }
    else if (BOX.id().equals(id)) {
      return BOX;
    }
    else if (PROMO.id().equals(id)) {
      return PROMO;
    }
    else if (TOKEN.id().equals(id)) {
      return TOKEN;
    }
    else if (MEMORABILIA.id().equals(id)) {
      return MEMORABILIA;
    }
    else {
      throw new UnsupportedOperationException("Unmapped set type id: " + id);
    }
  }

  public static ScrySetType getInstance(String value) {
    if (value == null) {
      return null;
    }
    else if (CORE.value().equals(value)) {
      return CORE;
    }
    else if (EXPANSION.value().equals(value)) {
      return EXPANSION;
    }
    else if (MASTERS.value().equals(value)) {
      return MASTERS;
    }
    else if (FROM_THE_VAULT.value().equals(value)) {
      return FROM_THE_VAULT;
    }
    else if (MASTERPIECE.value().equals(value)) {
      return MASTERPIECE;
    }
    else if (SPELLBOOK.value().equals(value)) {
      return SPELLBOOK;
    }
    else if (PREMIUM_DECK.value().equals(value)) {
      return PREMIUM_DECK;
    }
    else if (DUEL_DECK.value().equals(value)) {
      return DUEL_DECK;
    }
    else if (DRAFT_INNOVATION.value().equals(value)) {
      return DRAFT_INNOVATION;
    }
    else if (TREASURE_CHEST.value().equals(value)) {
      return TREASURE_CHEST;
    }
    else if (COMMANDER.value().equals(value)) {
      return COMMANDER;
    }
    else if (PLANECHASE.value().equals(value)) {
      return PLANECHASE;
    }
    else if (ARCHENEMY.value().equals(value)) {
      return ARCHENEMY;
    }
    else if (VANGUARD.value().equals(value)) {
      return VANGUARD;
    }
    else if (FUNNY.value().equals(value)) {
      return FUNNY;
    }
    else if (STARTER.value().equals(value)) {
      return STARTER;
    }
    else if (BOX.value().equals(value)) {
      return BOX;
    }
    else if (PROMO.value().equals(value)) {
      return PROMO;
    }
    else if (TOKEN.value().equals(value)) {
      return TOKEN;
    }
    else if (MEMORABILIA.value().equals(value)) {
      return MEMORABILIA;
    }
    else {
      throw new UnsupportedOperationException("Unmapped set type value: " + value);
    }
  }
}
