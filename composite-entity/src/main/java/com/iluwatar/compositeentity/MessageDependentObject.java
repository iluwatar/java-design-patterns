package com.iluwatar.compositeentity;

/**
 * The first DependentObject to show message.
 */

public class MessageDependentObject extends DependentObject<String> {

  private String message;

  @Override
  public void setData(String message) {
    this.message = message;
  }

  @Override
  public String getData() {
    return message;
  }
}