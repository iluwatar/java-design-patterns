package com.iluwatar.model.view.intent;

import com.iluwatar.model.view.intent.actions.AdditionCalculatorAction;
import com.iluwatar.model.view.intent.actions.CalculatorAction;
import com.iluwatar.model.view.intent.actions.DivisionCalculatorAction;
import com.iluwatar.model.view.intent.actions.MultiplicationCalculatorAction;
import com.iluwatar.model.view.intent.actions.SetVariableCalculatorAction;
import com.iluwatar.model.view.intent.actions.SubtractionCalculatorAction;

/**
 * Handle transformations to {@link CalculatorModel} based on intercepted {@link CalculatorAction}.
 * */
public class CalculatorViewModel {
  private CalculatorModel model = new CalculatorModel(0.0, 0.0);

  void handleAction(CalculatorAction action) {
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
        SetVariableCalculatorAction setVariableAction = (SetVariableCalculatorAction) action;
        setVariable(setVariableAction.variable);
        break;

      default:
        break;
    }
  }

  public CalculatorModel getCalculatorModel() {
    return model;
  }

  private void setVariable(Double variable) {
    model = model.copy(model.output, variable);
  }

  private void add() {
    model = model.copy(model.output + model.variable, model.variable);
  }

  private void subtract() {
    model = model.copy(model.output - model.variable, model.variable);
  }

  private void multiply() {
    model = model.copy(model.output * model.variable, model.variable);
  }

  private void divide() {
    model = model.copy(model.output / model.variable, model.variable);
  }
}
