package com.iluwatar.collectorstate;

/**
 * State Machine which transfers according to the event.
 */
public class StateMachine {
  State currentState;
  int capacity = 1000;
  String[] digits = new String[capacity];

  /**
   * change state.

   * @param state Enum State
   */
  public void changeState(State state) {
    currentState = state;
  }
}
