package com.iluwatar.eda.framework;

import com.iluwatar.eda.event.Event;

/**
 * This interface can be implemented to handle different types of messages.
 * Every handler is responsible for a single of type message
 */
public interface Handler<E extends Message> {

  /**
   * The onEvent method should implement and handle behavior related to the event.
   * This can be as simple as calling another service to handle the event on publishing the event on
   * a queue to be consumed by other sub systems.
   * @param event the {@link Event} object to be handled.
   */
  void onEvent(Event event);
}