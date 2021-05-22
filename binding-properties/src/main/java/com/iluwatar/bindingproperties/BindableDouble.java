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

  /**
   * Set the value of this double-precession float point number.
   */
  @Override
  public void setValue(Double newValue) {
    if (blockPropagation) {
      return;
    }
    beforePropertyChanged(newValue);
    value = newValue;
  }

  /**
   * Get the value of this double-precession number float point number.
   */
  @Override
  public Double getValue() {
    return value;
  }
}
