package com.steventimothy.scryfall.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
public abstract class BaseClient {

  /**
   * The base path to the scryfall api.
   */
  private static final String SCRYFALL_BASE_PATH = "https://api.scryfall.com";

  /**
   * The time the last call was sent to the api. Scryfall only wants at most 10 calls per second.
   */
  private static Instant lastCall = null;

  /**
   * The rest template used to make the calls.
   */
  protected final RestTemplate restTemplate;

  /**
   * Gets the base path.
   * @return The base path to the scryfall api.
   */
  protected String getBasePath() {
    return SCRYFALL_BASE_PATH;
  }

  /**
   * Checks for a need to timeout the execution of imports so that we do not go over
   * the suggested 10 calls per second.
   */
  protected void checkForRequestedTimeout() {
    if (lastCall != null) {
      long millis = 100L - Duration.between(lastCall, Instant.now()).toMillis();

      if (millis > 0) {
        try {
          Thread.sleep(millis);
        }
        catch (InterruptedException ex) {
          log.error("An error occurred during a timout.");
        }
      }
    }

    //Set the time of the last call to now.
    lastCall = Instant.now();
  }


  public BaseClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }
}
