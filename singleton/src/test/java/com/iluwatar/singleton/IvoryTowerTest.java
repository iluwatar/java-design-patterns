package com.iluwatar.singleton;

/**
 * Date: 12/29/15 - 19:23 PM
 *
 * @author Jeroen Meulemeester
 */
public class IvoryTowerTest extends SingletonTest<IvoryTower> {

  /**
   * Create a new singleton test instance using the given 'getInstance' method
   */
  public IvoryTowerTest() {
    super(IvoryTower::getInstance);
  }

}