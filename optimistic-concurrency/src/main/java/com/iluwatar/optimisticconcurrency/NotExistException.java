package com.iluwatar.optimisticconcurrency;

/**
 * Exception to throw when tries buy non-existent product.
 */
public class NotExistException extends Exception {

  // uid
  private static final long serialVersionUID = 1234567L;

  /**
   * message for when product not found.
   */
  private static final String NOT_EXIST =
      "Product doesn't exist!";

  /**
   * Construct an instance of NotExistException.
   */
  public NotExistException() {
    super(NOT_EXIST);
  }
}
