package com.iluwatar.specialcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvalidUser implements ReceiptViewModel {

  private static final Logger LOGGER = LoggerFactory.getLogger(InvalidUser.class);

  private final String userName;

  public InvalidUser(String userName) {
    this.userName = userName;
  }

  @Override
  public void show() {
    LOGGER.info("Invalid user: " + userName);
  }
}
