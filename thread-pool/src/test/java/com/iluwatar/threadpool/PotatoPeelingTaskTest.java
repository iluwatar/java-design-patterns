package com.iluwatar.threadpool;

/**
 * Date: 12/30/15 - 18:23 PM
 *
 * @author Jeroen Meulemeester
 */
public class PotatoPeelingTaskTest extends TaskTest<PotatoPeelingTask> {

  /**
   * Create a new test instance
   */
  public PotatoPeelingTaskTest() {
    super(PotatoPeelingTask::new, 200);
  }

}