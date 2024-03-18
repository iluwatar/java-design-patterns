package com.iluwatar.corruption.system;

/**
 * The class represents an general exception for the shop.
 */
public class ShopException extends Exception {
  public ShopException(String message) {
    super(message);
  }

  /**
   * Throws an exception that the order is already placed but has an incorrect data.
   *
   * @param lhs the incoming order
   * @param rhs the existing order
   * @return the exception
   * @throws ShopException the exception
   */
  public static ShopException throwIncorrectData(String lhs, String rhs) throws ShopException {
    throw new ShopException("The order is already placed but has an incorrect data:\n"
        + "Incoming order:  " + lhs + "\n"
        + "Existing order:  " + rhs);
  }

}
