package com.iluwatar.money.exception;

public class InsufficientFundsException extends Exception {

  public InsufficientFundsException() {
    super();
  }

  public InsufficientFundsException(String message) {
    super(message);
  }

  public InsufficientFundsException(String message, Throwable cause) {
    super(message, cause);
  }

  public InsufficientFundsException(Throwable cause) {
    super(cause);
  }

  protected InsufficientFundsException(String message, Throwable cause,
                                       boolean enableSuppression,
                                       boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
