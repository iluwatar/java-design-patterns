package com.iluwatar.monolithic.exceptions;

import java.io.Serial;
/**
 * Custom Exception class for enhanced readability.
 * */
public class InsufficientStockException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = 1005208208127745099L;
  /**
   * Exception Constructor that is readable through code and provides the message inputted into it.
   * */
  public InsufficientStockException(String message) {
    super(message);
  }
}
