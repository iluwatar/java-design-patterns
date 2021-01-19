package com.iluwatar.specialcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutOfStock implements ReceiptViewModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(OutOfStock.class);

  private String userName;
  private String itemName;

  public OutOfStock(String userName, String itemName) {
    this.userName = userName;
    this.itemName = itemName;
  }

  @Override
  public void show() {
    LOGGER.info("Out of stock: " + itemName + " for user = " + userName + " to buy");
  }
}
