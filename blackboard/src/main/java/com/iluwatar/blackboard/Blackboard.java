package com.iluwatar.blackboard;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

/**
 * Blackboard is a structured global memory where a solution to the problem
 * under consideration is incrementally constructed.
 */

@Slf4j
public class Blackboard {
  /**
   * The feature of a blackboard.
   */
  private int[] features;

  /**
   * The total number of features.
   */
  public int dimension;

  /**
   * Initialize a blackboard with features filled with zero elements.
   */
  public Blackboard(int dimension) {
    features = new int[dimension];
    this.dimension = dimension;
  }

  /**
   * Return the state of feature of this blackboard as string.
   */
  public String currentState() {
    final StringBuilder res = new StringBuilder(String.valueOf(features[0]));
    for (int i = 1; i < features.length; i++) {
      res.append(',').append(features[i]);
    }
    return res.toString();
  }

  /**
   * Print the state of feature of this blackboard as string.
   */
  public void showState() {
    LOGGER.info("Current state is {}", currentState());
  }

  /**
   * Get the target.
   */
  public int access(int target) {
    return features[target];
  }

  /**
   * Apply every operator of operatorList on the feature.
   */
  public void update(ArrayList<Operator> operatorList) {
    for (final Operator tempOperator : operatorList) {
      features[tempOperator.pos] += tempOperator.operateValue;
    }
  }
}
