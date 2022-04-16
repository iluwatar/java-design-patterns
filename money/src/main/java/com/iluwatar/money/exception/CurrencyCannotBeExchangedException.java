package com.iluwatar.money.exception;

/**
 * This class represents CurrencyCannotBeExchangedException.
 */
public class CurrencyCannotBeExchangedException extends Exception {

  public CurrencyCannotBeExchangedException() {
    super();
  }

  public CurrencyCannotBeExchangedException(String message) {
    super(message);
  }

  public CurrencyCannotBeExchangedException(String message, Throwable cause) {
    super(message, cause);
  }

  public CurrencyCannotBeExchangedException(Throwable cause) {
    super(cause);
  }

  protected CurrencyCannotBeExchangedException(String message, Throwable cause,
                                               boolean enableSuppression,
                                               boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
