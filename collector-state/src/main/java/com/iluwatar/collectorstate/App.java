package com.iluwatar.collectorstate;

import lombok.extern.slf4j.Slf4j;


/**
 * This is a brief structural introduction to Collector-State pattern using state machine
 * with self-defined message and states.
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
    int minimumDigit = 2;
    var collectorState = new DigitCollectorState(expectedDigit, minimumDigit);
    var m = new StateMachine();
    var msg = new DigitMsg();
    // case1: more than minimum
    for (int i = 0; i < expectedDigit - 1; i++) {
      collectorState.onDigit(m, msg);
    }
    collectorState.onDigitTimeout(m);
    // restart
    collectorState.restartDigitTimer();
    // case2: less than minimum
    collectorState.onDigit(m, msg);
    collectorState.onDigitTimeout(m);
    LOGGER.info(collectorState.getDescription());
  }
}