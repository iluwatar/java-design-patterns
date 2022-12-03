package com.iluwatar.model.view.intent.actions;

/**
 * SetVariable {@link CalculatorAction}.
 */
public final class SetVariableCalculatorAction implements CalculatorAction {

  /**
   * Subclass tag.
   */
  public static final String TAG = "SET_VARIABLE";

  /**
   * Used by {@link com.iluwatar.model.view.intent.CalculatorViewModel}.
   */
  private final Double variable;


  /**
   * Constructor.
   *
   * @param variableParam -> variable parameter value.
   * */
  public SetVariableCalculatorAction(final Double variableParam) {
    this.variable = variableParam;
  }

  /**
   * Simple getter.
   *
   * @return variable.
   * */
  public Double getVariable() {
    return variable;
  }

  /**
   * Makes checking subclass type trivial.
   */
  @Override
  public String tag() {
    return TAG;
  }
}
