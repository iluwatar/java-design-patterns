package com.iluwatar.money.exception;

public class BalanceCouldNotBeCreatedException extends Exception {

  public BalanceCouldNotBeCreatedException() {
  }

  public BalanceCouldNotBeCreatedException(String message) {
    super(message);
  }

  public BalanceCouldNotBeCreatedException(String message, Throwable cause) {
    super(message, cause);
  }

  public BalanceCouldNotBeCreatedException(Throwable cause) {
    super(cause);
  }

  public BalanceCouldNotBeCreatedException(String message, Throwable cause,
                                           boolean enableSuppression,
                                           boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
