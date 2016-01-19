package com.iluwatar.lazy.loading;

/**
 * 
 * Simple implementation of the lazy loading idiom. However, this is not thread safe.
 *
 */
public class HolderNaive {

  private Heavy heavy;

  /**
   * Constructor
   */
  public HolderNaive() {
    System.out.println("HolderNaive created");
  }

  /**
   * Get heavy object
   */
  public Heavy getHeavy() {
    if (heavy == null) {
      heavy = new Heavy();
    }
    return heavy;
  }
}
