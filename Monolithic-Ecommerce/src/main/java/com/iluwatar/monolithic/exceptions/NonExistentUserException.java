package com.iluwatar.monolithic.exceptions;

import java.io.Serial;
/**
 * Custom Exception class for enhanced readability.
 * */
public class NonExistentUserException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = -7660909426227843633L;
  /**
   * Exception Constructor that is readable through code and provides the message inputted into it.
   * */
  public NonExistentUserException(String msg) {
    super(msg);
  }
}
