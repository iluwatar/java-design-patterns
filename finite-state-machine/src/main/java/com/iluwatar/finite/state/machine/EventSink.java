package com.iluwatar.finite.state.machine;

/**
 * Interface for handling Events that happen inside FSM states.
 */
public interface EventSink {

  void castEvent(Event event);
}
