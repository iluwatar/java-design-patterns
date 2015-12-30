package com.iluwatar.threadpool;

/**
 * Date: 12/30/15 - 18:23 PM
 *
 * @author Jeroen Meulemeester
 */
public class CoffeeMakingTaskTest extends TaskTest<CoffeeMakingTask> {

  /**
   * Create a new test instance
   */
  public CoffeeMakingTaskTest() {
    super(CoffeeMakingTask::new, 100);
  }

}
