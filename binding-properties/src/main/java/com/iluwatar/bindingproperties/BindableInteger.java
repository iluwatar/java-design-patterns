package com.iluwatar.bindingproperties;

import java.util.HashMap;

/**
 * This class's instances are bindable double-precession integers.
 */
public class BindableInteger extends BindableField<Integer> {

  public BindableInteger(Integer value) {
    this.value = value;
    notifyMap = new HashMap<>();
  }

  /**
   * Set the value of this integer.
   */
  @Override
  public void setValue(Integer newValue) {
    if (blockPropagation) {
      return;
    }
    beforePropertyChanged(newValue);
    value = newValue;
  }

  /**
   * Get the value of this integer.
   */
  @Override
  public Integer getValue() {
    return value;
  }
}
