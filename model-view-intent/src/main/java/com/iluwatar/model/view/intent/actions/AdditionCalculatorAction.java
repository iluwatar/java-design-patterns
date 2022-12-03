package com.iluwatar.model.view.intent.actions;

/**
 * Addition {@link CalculatorAction}.
 * */
public class AdditionCalculatorAction implements CalculatorAction {
  /**
   * Subclass tag.
   * */
  public static final String TAG = "ADDITION";

  /**
   * Makes checking subclass type trivial.
   * */
  @Override
  public String tag() {
    return TAG;
  }
}
