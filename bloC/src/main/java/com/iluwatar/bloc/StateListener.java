package com.iluwatar.bloc;

/**
 * The {@code StateListener} interface defines the contract for listening to state changes.
 * Implementations of this interface should handle state changes and define actions to take when the state changes.
 *
 * @param <T> the type of state that this listener will handle
 */
public interface StateListener<T> {

  /**
   * This method is called when the state has changed.
   *
   * @param state the updated state
   */
  void onStateChange(T state);
}
