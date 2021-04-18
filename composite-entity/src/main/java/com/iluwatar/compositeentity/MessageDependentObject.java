package com.iluwatar.compositeentity;

/**
 * The first DependentObject to show message.
 */

public class MessageDependentObject extends DependentObject<String> {

  @Override
  public void setData(String message) {
    this.data = message;
  }

  @Override
  public String getData() {
    return data;
  }
}