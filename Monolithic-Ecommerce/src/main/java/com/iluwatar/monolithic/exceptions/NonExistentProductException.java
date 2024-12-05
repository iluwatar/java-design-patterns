package com.iluwatar.monolithic.exceptions;

import java.io.Serial;
/**
 * Custom Exception class for enhanced readability.
 * */
public class NonExistentProductException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = -593425162052345565L;
  /**
   * Exception Constructor that is readable through code and provides the message inputted into it.
   * */
  public NonExistentProductException(String msg) {
    super(msg);
  }
}
