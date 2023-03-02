package com.iluwatar.model.view.intent.actions;

/**
 *  Subtraction {@link CalculatorAction}.
 * */
public class SubtractionCalculatorAction implements CalculatorAction {
  /**
   * Subclass tag.
   * */
  public static final String TAG = "SUBTRACTION";

  /**
   * Makes checking subclass type trivial.
   * */
  @Override
  public String tag() {
    return TAG;
  }
}
