package com.iluwatar.money.exception;

/**
 * This class represents PrimaryBalanceNotFoundException.
 */
public class PrimaryBalanceNotFoundException extends Exception {

  public PrimaryBalanceNotFoundException() {
    super();
  }

  public PrimaryBalanceNotFoundException(String message) {
    super(message);
  }

  public PrimaryBalanceNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public PrimaryBalanceNotFoundException(Throwable cause) {
    super(cause);
  }

  protected PrimaryBalanceNotFoundException(String message, Throwable cause,
                                            boolean enableSuppression,
                                            boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
