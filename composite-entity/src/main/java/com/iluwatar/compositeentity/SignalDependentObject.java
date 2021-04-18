package com.iluwatar.compositeentity;

/**
 * The second DependentObject to show message.
 */

public class SignalDependentObject extends DependentObject<String> {

  @Override
  public void setData(String signal) {
    this.data = signal;
  }

  @Override
  public String getData() {
    return data;
  }
}