package com.steventimothy.scryfall.services;

import com.steventimothy.scryfall.BaseComponent;
import com.steventimothy.scryfall.schemas.sets.ScrySetImportDetails;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class SetServiceTest extends BaseComponent {

  @Autowired
  private SetService setService;

  @Test
  public void getImportDetails() {

    ScrySetImportDetails scrySetImportDetails = setService.getImportDetails("src/main/resources/images/sets");

    assertThat(scrySetImportDetails)
          .isNotNull();
  }
}
