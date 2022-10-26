package com.iluwatar.model.view.intent.actions;

/**
 *  SetVariable {@link CalculatorAction}.
 * */
public class SetVariableCalculatorAction implements CalculatorAction {
  public static final String TAG = "SET_VARIABLE";

  public Double variable;

  public SetVariableCalculatorAction(Double variable){
    this.variable = variable;
  }

  @Override
  public String tag() {
    return TAG;
  }
}
