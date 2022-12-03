package com.iluwatar.finite.state.machine;

/**
 * Common data model for all FSM states.
 */
public class DataModel {

  /**
   * Order inputs sum.
   */
  private String currentString = "";

  /**
   * Clear model value.
   */
  public void clear() {
    currentString = "";
  }


  /**
   * Add character to the model.
   *
   * @param character -> character value.
   */
  public void addCharacter(final char character) {
    currentString += character;
  }

  /**
   * Get current ordered sum of inputs.
   *
   * @return string of ordered inputs.
   */
  public String getCurrentString() {
    return currentString;
  }
}
