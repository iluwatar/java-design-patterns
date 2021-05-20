package com.iluwatar.collectorstate;

import lombok.extern.slf4j.Slf4j;


/**
 * This is a simple client-server design model implemented by multithreaded
 * simulation of client-side and service-side communication.
 */

@Slf4j
public final class App {

  private App(){}

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(final String[] args) {
    int expectedDigit = 8;
    int minimumDigit = 1;
    var collectorState = new DigitCollectorState(expectedDigit, minimumDigit);
    LOGGER.info(collectorState.getDescription());
  }
}