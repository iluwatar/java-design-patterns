package com.iluwatar.optimisticconcurrency;

/**
 * Exception to throw when tries to update an object using outdated version.
 */
public class OptimisticLockException extends Exception {

  // uid
  private static final long serialVersionUID = 1234567L;

  /**
   * Construct an instance of OptimisticLockException.
   * @param message message to display.
   */
  public OptimisticLockException(final String message) {
    super(message);
  }
}
