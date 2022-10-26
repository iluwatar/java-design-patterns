package com.iluwatar.model.view.intent;

/**
 * Current state of calculator.
 * */
public class CalculatorModel {
  public final Double variable;
  public final Double output;

  public CalculatorModel(Double output, Double variable) {
    this.output = output;
    this.variable = variable;
  }

  public CalculatorModel copy(Double output, Double variable) {
    return new CalculatorModel(output, variable);
  }
}
