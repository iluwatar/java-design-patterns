package com.iluwatar.singleton;

/**
 * Date: 12/29/15 - 19:26 PM
 *
 * @author Jeroen Meulemeester
 */
public class ThreadSafeDoubleCheckLockingTest extends SingletonTest<ThreadSafeDoubleCheckLocking> {

  /**
   * Create a new singleton test instance using the given 'getInstance' method
   */
  public ThreadSafeDoubleCheckLockingTest() {
    super(ThreadSafeDoubleCheckLocking::getInstance);
  }

}