package com.iluwatar;
/**
 * An exception for when the user tries to subtract two diffrent currencies or subtract an amount he doesn't have.
 */
public class CannotSubtractException extends Exception {
  /**
   * Constructs an exception with the specified message.
   *
   * @param message the message shown in the terminal (as a String).
   */
  public CannotSubtractException(String message) {
    super(message);
  }

}