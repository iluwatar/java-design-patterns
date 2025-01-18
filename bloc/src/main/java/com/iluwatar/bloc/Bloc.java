package com.iluwatar.bloc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Bloc class is responsible for managing the current state and notifying registered listeners
 * whenever the state changes. It implements the ListenerManager interface, allowing listeners
 * to be added, removed, and notified of state changes.
 */
public class Bloc implements ListenerManager<State> {

  private State currentState;
  private final List<StateListener<State>> listeners = new ArrayList<>();

  /**
   * Constructs a new Bloc instance with an initial state of value 0.
   */
  public Bloc() {
    this.currentState = new State(0);
  }

  /**
   * Adds a listener to receive state change notifications.
   *
   * @param listener the listener to add
   */
  @Override
  public void addListener(StateListener<State> listener) {
    listeners.add(listener);
    listener.onStateChange(currentState);
  }

  /**
   * Removes a listener from receiving state change notifications.
   *
   * @param listener the listener to remove
   */
  @Override
  public void removeListener(StateListener<State> listener) {
    listeners.remove(listener);
  }

  /**
   * Returns an unmodifiable list of all registered listeners.
   *
   * @return an unmodifiable list of listeners
   */
  @Override
  public List<StateListener<State>> getListeners() {
    return Collections.unmodifiableList(listeners);
  }

  /**
   * Emits a new state and notifies all registered listeners of the change.
   *
   * @param newState the new state to emit
   */
  private void emitState(State newState) {
    currentState = newState;
    for (StateListener<State> listener : listeners) {
      listener.onStateChange(currentState);
    }
  }

  /**
   * Increments the current state value by 1 and notifies listeners of the change.
   */
  public void increment() {
    emitState(new State(currentState.value() + 1));
  }

  /**
   * Decrements the current state value by 1 and notifies listeners of the change.
   */
  public void decrement() {
    emitState(new State(currentState.value() - 1));
  }
}
