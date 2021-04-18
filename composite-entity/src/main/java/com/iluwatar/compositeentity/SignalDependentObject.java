package com.iluwatar.compositeentity;

/**
 * The second DependentObject to show message.
 */

public class SignalDependentObject extends DependentObject<String> {

  private String signal;

  @Override
  public void setData(String message) {
    this.signal = message;
  }

  @Override
  public String getData() {
    return signal;
  }
}