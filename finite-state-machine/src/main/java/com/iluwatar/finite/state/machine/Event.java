package com.iluwatar.finite.state.machine;

/**
 * Events that can happen and change current FSM state.
 */
public enum Event {
  /**
   * Name is correct.
   * */
  CORRECT,

  /**
   * Name is incorrect.
   * */
  INCORRECT,

  /**
   * Clear name.
   * */
  CLEAR
}
