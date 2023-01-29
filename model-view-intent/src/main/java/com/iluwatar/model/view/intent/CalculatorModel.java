package com.iluwatar.model.view.intent;

import lombok.Data;
import lombok.Getter;

/**
 * Current state of calculator.
 */
@Data
public class CalculatorModel {

  /**
   * Current calculator variable used for operations.
   **/
  @Getter
  private final Double variable;

  /**
   * Current calculator output -> is affected by operations.
   **/
  @Getter
  private final Double output;
}
