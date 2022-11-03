package com.iluwatar.finite.state.machine;

/**
 * Common data model for all FSM states.
 */
public class DataModel {

  private String currentString = "";

  public void clear() {
    currentString = "";
  }

  public void addCharacter(char character) {
    currentString += character;
  }

  public String getCurrentString() {
    return currentString;
  }
}
