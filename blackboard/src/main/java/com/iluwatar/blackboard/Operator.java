package com.iluwatar.blackboard;

/**
 * Operator is a pair of integers, representing the feature the operator wants to modify
 * and the value it wants to add to the feature.
 */

public class Operator {
  /**
   * The feature the operator wants to modify.
   */
  public int pos;

  /**
   * The value it wants to add to the feature.
   */
  public int operateValue;

  /**
   * Initialize an operator.
   */
  public Operator(int pos, int operateValue) {
    this.operateValue = operateValue;
    this.pos = pos;
  }
}
