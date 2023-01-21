package com.iluwatar.model.view.intent;

import com.iluwatar.model.view.intent.actions.AdditionCalculatorAction;
import com.iluwatar.model.view.intent.actions.CalculatorAction;
import com.iluwatar.model.view.intent.actions.DivisionCalculatorAction;
import com.iluwatar.model.view.intent.actions.MultiplicationCalculatorAction;
import com.iluwatar.model.view.intent.actions.SetVariableCalculatorAction;
import com.iluwatar.model.view.intent.actions.SubtractionCalculatorAction;

/**
 * Handle transformations to {@link CalculatorModel}
 * based on intercepted {@link CalculatorAction}.
 */
public final class CalculatorViewModel {

  /**
   * Current calculator model (can be changed).
   */
  private CalculatorModel model =
      new CalculatorModel(0.0, 0.0);

  /**
   * Handle calculator action.
   *
   * @param action -> transforms calculator model.
   */
  void handleAction(final CalculatorAction action) {
    switch (action.tag()) {
      case AdditionCalculatorAction.TAG:
        add();
        break;

      case SubtractionCalculatorAction.TAG:
        subtract();
        break;

      case MultiplicationCalculatorAction.TAG:
        multiply();
        break;

      case DivisionCalculatorAction.TAG:
        divide();
        break;

      case SetVariableCalculatorAction.TAG:
        SetVariableCalculatorAction setVariableAction =
            (SetVariableCalculatorAction) action;
        setVariable(setVariableAction.getVariable());
        break;

      default:
        break;
    }
  }

  /**
   * Getter.
   *
   * @return current calculator model.
   */
  public CalculatorModel getCalculatorModel() {
    return model;
  }

  /**
   * Set new calculator model variable.
   *
   * @param variable -> value of new calculator model variable.
   */
  private void setVariable(final Double variable) {
    model = new CalculatorModel(
        variable,
        model.getOutput()
    );
  }

  /**
   * Add variable to model output.
   */
  private void add() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() + model.getVariable()
    );
  }

  /**
   * Subtract variable from model output.
   */
  private void subtract() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() - model.getVariable()
    );
  }

  /**
   * Multiply model output by variable.
   */
  private void multiply() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() * model.getVariable()
    );
  }

  /**
   * Divide model output by variable.
   */
  private void divide() {
    model = new CalculatorModel(
        model.getVariable(),
        model.getOutput() / model.getVariable()
    );
  }
}
