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

  @Override
  public void setValue(Integer newValue) {
    if (blockPropagation) {
      return;
    }
    beforePropertyChanged(newValue);
    value = newValue;
  }

  @Override
  public Integer getValue() {
    return value;
  }
}
