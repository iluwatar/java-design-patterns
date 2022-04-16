package com.iluwatar.money.exception;

/**
 * This class represents SubtractionCannotOccurException.
 */
public class SubtractionCannotOccurException extends Exception {

  public SubtractionCannotOccurException() {
    super();
  }

  public SubtractionCannotOccurException(String message) {
    super(message);
  }

  public SubtractionCannotOccurException(String message, Throwable cause) {
    super(message, cause);
  }

  public SubtractionCannotOccurException(Throwable cause) {
    super(cause);
  }

  protected SubtractionCannotOccurException(String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
