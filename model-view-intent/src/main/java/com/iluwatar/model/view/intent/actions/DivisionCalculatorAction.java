package com.iluwatar.model.view.intent.actions;

/**
 *  Division {@link CalculatorAction}.
 * */
public class DivisionCalculatorAction implements CalculatorAction {
  /**
   * Subclass tag.
   * */
  public static final String TAG = "DIVISION";

  /**
   * Makes checking subclass type trivial.
   * */
  @Override
  public String tag() {
    return TAG;
  }
}
