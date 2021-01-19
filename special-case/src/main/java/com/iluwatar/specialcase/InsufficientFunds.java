package com.iluwatar.specialcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InsufficientFunds implements ReceiptViewModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(InsufficientFunds.class);

  private String userName;
  private Double amount;
  private String itemName;

  /**
   * Constructor of InsufficientFunds.
   *
   * @param userName of the user
   * @param amount of the user's account
   * @param itemName of the item
   */
  public InsufficientFunds(String userName, Double amount, String itemName) {
    this.userName = userName;
    this.amount = amount;
    this.itemName = itemName;
  }

  @Override
  public void show() {
    LOGGER.info("Insufficient funds: " + amount + " of user: " + userName
        + " for buying item: " + itemName);
  }
}
