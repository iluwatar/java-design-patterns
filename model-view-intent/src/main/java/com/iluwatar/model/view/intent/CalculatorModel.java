package com.iluwatar.model.view.intent;

/**
 * Current state of calculator.
 */
public class CalculatorModel {

  /**
   * Current calculator variable used for operations.
   **/
  private final Double variable;

  /**
   * Current calculator output -> is affected by operations.
   **/
  private final Double output;

  /**
   * Create calculator model with starting values.
   *
   * @param newOutput   -> starting output value.
   * @param newVariable -> starting variable value.
   **/
  public CalculatorModel(final Double newOutput, final Double newVariable) {
    this.output = newOutput;
    this.variable = newVariable;
  }

  /**
   * Getter.
   *
   * @return variable param.
   **/
  public final Double getVariable() {
    return variable;
  }

  /**
   * Getter.
   *
   * @return variable param.
   **/
  public final Double getOutput() {
    return output;
  }

  /**
   * Simple copy method,
   * easier to make operations on model while keeping it immutable.
   *
   * @param newOutput   -> starting output value.
   * @param newVariable -> starting variable value.
   * @return new CalculatorModel instance.
   **/
  public final CalculatorModel copy(
      final Double newOutput,
      final Double newVariable
  ) {
    return new CalculatorModel(newOutput, newVariable);
  }
}
