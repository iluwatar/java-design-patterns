package com.iluwatar.model.view.intent;

import lombok.Data;

/**
 * Current state of calculator.
 */
@Data
public class CalculatorModel {

  /**
   * Current calculator variable used for operations.
   **/
  final Double variable;

  /**
   * Current calculator output -> is affected by operations.
   **/
  final Double output;
}
