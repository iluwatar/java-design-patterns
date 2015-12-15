package com.iluwatar.event.aggregator;

/**
 * Date: 12/12/15 - 10:57 AM
 *
 * @author Jeroen Meulemeester
 */
public class LordVarysTest extends EventEmitterTest<LordVarys> {

  /**
   * Create a new test instance, using the correct object factory
   */
  public LordVarysTest() {
    super(Weekday.SATURDAY, Event.TRAITOR_DETECTED, LordVarys::new, LordVarys::new);
  }

}