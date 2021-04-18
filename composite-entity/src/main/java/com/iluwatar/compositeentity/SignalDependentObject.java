package com.iluwatar.compositeentity;

/**
 * The second DependentObject to show message.
 */

public class SignalDependentObject extends DependentObject<String> {

  private String data;

  public void setData(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }
}