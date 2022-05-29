package com.iluwatar.optimisticconcurrency;

/**
 * Exception to throw when tries buy with negative amount.
 */
public class NegativeAmountException extends Exception {

  // uid
  private static final long serialVersionUID = 1234567L;

  /**
   * message for when buy amount is negative.
   */
  private static final String NEGATIVE_AMOUNT =
      "Amount to buy should be more than zero!";

  /**
   * Construct an instance of NegativeAmountException.
   */
  public NegativeAmountException() {
    super(NEGATIVE_AMOUNT);
  }
}
