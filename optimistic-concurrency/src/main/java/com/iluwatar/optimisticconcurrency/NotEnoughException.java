package com.iluwatar.optimisticconcurrency;

/**
 * Exception to throw when tries more than there is in stock.
 */
public class NotEnoughException extends Exception {

  // uid
  private static final long serialVersionUID = 1234567L;

  /**
   * message for not enough in stock.
   */
  private static final String NOT_ENOUGH =
      "There are not enough products in stock!";

  /**
   * Construct an instance of NotEnoughException.
   */
  public NotEnoughException() {
    super(NOT_ENOUGH);
  }
}
