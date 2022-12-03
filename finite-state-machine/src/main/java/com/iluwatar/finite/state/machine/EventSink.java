package com.iluwatar.finite.state.machine;

/**
 * Interface for handling Events that happen inside FSM states.
 */
public interface EventSink {

  /**
   * Used to inform FSM about changes to state.
   *
   * @param event -> affects FSM state
   */
  void castEvent(Event event);
}
