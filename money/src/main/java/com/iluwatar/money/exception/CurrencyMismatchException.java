package com.iluwatar.money.exception;

/**
 * This class represents CurrencyMismatchException.
 */
public class CurrencyMismatchException extends Exception {

  public CurrencyMismatchException() {
    super();
  }

  public CurrencyMismatchException(String message) {
    super(message);
  }

  public CurrencyMismatchException(String message, Throwable cause) {
    super(message, cause);
  }

  public CurrencyMismatchException(Throwable cause) {
    super(cause);
  }

  protected CurrencyMismatchException(String message, Throwable cause,
                                      boolean enableSuppression,
                                      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
