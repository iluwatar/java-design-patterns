package com.iluwatar.model.view.intent.actions;

/**
 *  Multiplication {@link CalculatorAction}.
 * */
public class MultiplicationCalculatorAction implements CalculatorAction {
  /**
   * Subclass tag.
   * */
  public static final String TAG = "MULTIPLICATION";

  /**
   * Makes checking subclass type trivial.
   * */
  @Override
  public String tag() {
    return TAG;
  }
}
