package com.iluwatar;
/**
 * An exception for when the user tries to add two diffrent currencies.
 */
public class CannotAddTwoCurrienciesException extends Exception {
  /**
   * Constructs an exception with the specified message.
   *
   * @param message the message shown in the terminal (as a String).
   */
  public CannotAddTwoCurrienciesException(String message) {
    super(message);
  }
}