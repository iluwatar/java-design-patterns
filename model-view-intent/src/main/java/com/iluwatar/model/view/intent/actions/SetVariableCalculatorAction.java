package com.iluwatar.model.view.intent.actions;

import lombok.Data;

/**
 * SetVariable {@link CalculatorAction}.
 */
@Data
public final class SetVariableCalculatorAction implements CalculatorAction {

  /**
   * Subclass tag.
   */
  public static final String TAG = "SET_VARIABLE";

  /**
   * Used by {@link com.iluwatar.model.view.intent.CalculatorViewModel}.
   */
  final Double variable;

  /**
   * Makes checking subclass type trivial.
   */
  @Override
  public String tag() {
    return TAG;
  }
}
