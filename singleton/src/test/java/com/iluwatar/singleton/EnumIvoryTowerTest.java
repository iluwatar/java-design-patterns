package com.iluwatar.singleton;

/**
 * Date: 12/29/15 - 19:20 PM
 *
 * @author Jeroen Meulemeester
 */
public class EnumIvoryTowerTest extends SingletonTest<EnumIvoryTower> {

  /**
   * Create a new singleton test instance using the given 'getInstance' method
   */
  public EnumIvoryTowerTest() {
    super(() -> EnumIvoryTower.INSTANCE);
  }

}
