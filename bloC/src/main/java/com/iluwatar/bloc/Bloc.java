package com.iluwatar.bloc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bloc implements ListenerManager<State> {
  private State currentState;
  private final List<StateListener<State>> listeners = new ArrayList<>();

  public Bloc() {
    this.currentState = new State(0);
  }

  @Override
  public void addListener(StateListener<State> listener) {
    listeners.add(listener);
    listener.onStateChange(currentState);
  }

  @Override
  public void removeListener(StateListener<State> listener) {
    listeners.remove(listener);
  }

  @Override
  public List<StateListener<State>> getListeners() {
    return Collections.unmodifiableList(listeners);
  }

  private void emitState(State newState) {
    currentState = newState;
    for (StateListener<State> listener : listeners) {
      listener.onStateChange(currentState);
    }
  }

  public void increment() {
    emitState(new State(currentState.getValue() + 1));
  }

  public void decrement() {
    emitState(new State(currentState.getValue() - 1));
  }
}

