package com.iluwatar.event.aggregator;

/**
 * Date: 12/12/15 - 10:57 AM
 *
 * @author Jeroen Meulemeester
 */
public class ScoutTest extends EventEmitterTest<Scout> {

  /**
   * Create a new test instance, using the correct object factory
   */
  public ScoutTest() {
    super(Weekday.TUESDAY, Event.WARSHIPS_APPROACHING, Scout::new, Scout::new);
  }

}