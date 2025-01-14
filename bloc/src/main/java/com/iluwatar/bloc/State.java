package com.iluwatar.bloc;

import lombok.Getter;

/**
 * The {@code State} class represents a state with an integer value.
 * This class encapsulates the value and provides methods to retrieve it.
 */
@Getter
public class State {
  /**
   * -- GETTER --
   *  Returns the value of the state.
   *
   */
  private final int value;

  /**
   * Constructs a {@code State} with the specified value.
   *
   * @param value the value of the state
   */
  public State(int value) {
    this.value = value;
  }

}
