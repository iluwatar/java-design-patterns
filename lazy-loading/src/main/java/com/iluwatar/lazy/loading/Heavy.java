package com.iluwatar.lazy.loading;

/**
 * 
 * Heavy objects are expensive to create.
 *
 */
public class Heavy {

  /**
   * Constructor
   */
  public Heavy() {
    System.out.println("Creating Heavy ...");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("... Heavy created");
  }
}
