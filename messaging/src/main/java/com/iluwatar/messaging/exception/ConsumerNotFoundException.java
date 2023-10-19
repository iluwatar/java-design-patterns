package com.iluwatar.messaging.exception;

public class ConsumerNotFoundException extends Exception{

  public ConsumerNotFoundException(long consumerId) {
    super(consumerId + "Is Not found");
  }
}
