package com.iluwatar.singleton;

/**
 * Date: 12/29/15 - 19:22 PM
 *
 * @author Jeroen Meulemeester
 */
public class InitializingOnDemandHolderIdiomTest extends SingletonTest<InitializingOnDemandHolderIdiom> {

  /**
   * Create a new singleton test instance using the given 'getInstance' method
   */
  public InitializingOnDemandHolderIdiomTest() {
    super(InitializingOnDemandHolderIdiom::getInstance);
  }

}