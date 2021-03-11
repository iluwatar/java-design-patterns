package com.iluwatar.money.exception;

public class BalanceDoesNotExistForAccountException extends Exception {

  public BalanceDoesNotExistForAccountException() {
  }

  public BalanceDoesNotExistForAccountException(String message) {
    super(message);
  }

  public BalanceDoesNotExistForAccountException(String message, Throwable cause) {
    super(message, cause);
  }

  public BalanceDoesNotExistForAccountException(Throwable cause) {
    super(cause);
  }

  public BalanceDoesNotExistForAccountException(String message, Throwable cause,
                                                boolean enableSuppression,
                                                boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
