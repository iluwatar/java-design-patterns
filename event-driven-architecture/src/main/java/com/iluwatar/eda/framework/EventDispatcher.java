package com.iluwatar.eda.framework;

import com.iluwatar.eda.event.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the routing of {@link Event} messages to associated handlers.
 * A {@link HashMap} is used to store the association between events and their respective handlers.
 *
 */
public class EventDispatcher {

  private Map<Class<? extends Event>, Handler<?>> handlers;

  public EventDispatcher() {
    handlers = new HashMap<>();
  }

  /**
   * Links an {@link Event} to a specific {@link Handler}.
   *
   * @param eventType The {@link Event} to be registered
   * @param handler   The {@link Handler} that will be handling the {@link Event}
   */
  public void registerChannel(Class<? extends Event> eventType,
                              Handler<?> handler) {
    handlers.put(eventType, handler);
  }

  /**
   * Dispatches an {@link Event} depending on it's type.
   *
   * @param event The {@link Event} to be dispatched
   */
  public void onEvent(Event event) {
    handlers.get(event.getClass()).onEvent(event);
  }

}