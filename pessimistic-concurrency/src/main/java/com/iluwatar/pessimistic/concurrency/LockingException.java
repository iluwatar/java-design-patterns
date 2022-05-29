package com.iluwatar.pessimistic.concurrency;
/** Exception to throw when there is a bad encounter with locking. */

public class LockingException extends Exception {

  /**
   * Construct an instance of Locking Exception.
   *
   * @param message - message to display
   */
  public LockingException(String message) {
    super(message);
  }
}
