package com.iluwatar.bindingproperties;

import java.util.HashMap;

/**
 * This class's instances are bindable double-precession float
 * point numbers.
 */
public class BindableDouble extends BindableField<Double> {

  public BindableDouble(Double value) {
    this.value = value;
    notifyMap = new HashMap<>();
  }

  @Override
  public void setValue(Double newValue) {
    if (blockPropagation) {
      return;
    }
    beforePropertyChanged(newValue);
    value = newValue;
  }

  @Override
  public Double getValue() {
    return value;
  }
}
