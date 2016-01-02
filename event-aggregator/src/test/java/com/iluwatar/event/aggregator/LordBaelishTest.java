package com.iluwatar.event.aggregator;

/**
 * Date: 12/12/15 - 10:57 AM
 *
 * @author Jeroen Meulemeester
 */
public class LordBaelishTest extends EventEmitterTest<LordBaelish> {

  /**
   * Create a new test instance, using the correct object factory
   */
  public LordBaelishTest() {
    super(Weekday.FRIDAY, Event.STARK_SIGHTED, LordBaelish::new, LordBaelish::new);
  }

}