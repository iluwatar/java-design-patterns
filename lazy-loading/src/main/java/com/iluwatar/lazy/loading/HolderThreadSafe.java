package com.iluwatar.lazy.loading;

/**
 * 
 * Same as HolderNaive but with added synchronization. This implementation is thread safe, but each
 * {@link #getHeavy()} call costs additional synchronization overhead.
 *
 */
public class HolderThreadSafe {

  private Heavy heavy;

  /**
   * Constructor
   */
  public HolderThreadSafe() {
    System.out.println("HolderThreadSafe created");
  }

  /**
   * Get heavy object
   */
  public synchronized Heavy getHeavy() {
    if (heavy == null) {
      heavy = new Heavy();
    }
    return heavy;
  }
}
