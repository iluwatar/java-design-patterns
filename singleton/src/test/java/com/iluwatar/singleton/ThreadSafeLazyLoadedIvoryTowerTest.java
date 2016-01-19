package com.iluwatar.singleton;

/**
 * Date: 12/29/15 - 19:26 PM
 *
 * @author Jeroen Meulemeester
 */
public class ThreadSafeLazyLoadedIvoryTowerTest extends SingletonTest<ThreadSafeLazyLoadedIvoryTower> {

  /**
   * Create a new singleton test instance using the given 'getInstance' method
   */
  public ThreadSafeLazyLoadedIvoryTowerTest() {
    super(ThreadSafeLazyLoadedIvoryTower::getInstance);
  }

}
