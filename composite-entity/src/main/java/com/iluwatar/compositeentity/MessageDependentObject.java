package com.iluwatar.compositeentity;

/**
 * The first DependentObject to show message.
 */

public class MessageDependentObject extends DependentObject<String> {

  private String data;

  public void setData(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }
}