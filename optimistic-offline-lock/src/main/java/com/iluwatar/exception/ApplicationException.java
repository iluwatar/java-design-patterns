package com.iluwatar.exception;

/**
 * Exception happens in application during business-logic execution.
 */
public class ApplicationException extends RuntimeException {

  /**
   * Inherited constructor with exception message.
   *
   * @param message exception message
   */
  public ApplicationException(String message) {
    super(message);
  }
}
