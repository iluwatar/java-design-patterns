package com.iluwatar.servicetoworker;

import lombok.extern.slf4j.Slf4j;

/**
 * GiantView displays the giant.
 */
@Slf4j
public class GiantView {

  /**
   * Display the GiantModel simply.
   *
   * @param giant the giant
   */
  public void displayGiant(GiantModel giant) {
    LOGGER.info(giant.toString());
  }
}
