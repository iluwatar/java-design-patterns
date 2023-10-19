package com.iluwatar.messaging.exception;

public class OrderWithZeroItemException extends Exception {

  public OrderWithZeroItemException() {
    super("No Item Present in order");
  }
}
