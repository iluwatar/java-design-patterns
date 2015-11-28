package com.iluwatar.eda;

import com.iluwatar.eda.event.Event;
import com.iluwatar.eda.framework.Channel;
import com.iluwatar.eda.framework.DynamicRouter;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link Event Dispatcher} handles routing of {@link Event} messages
 * to associated channels.
 * A {@link HashMap} is used to store the association between events and their respective handlers.
 *
 */
public class EventDispatcher implements DynamicRouter<Event> {

  private Map<Class<? extends Event>, Channel> handlers;

  public EventDispatcher() {
    handlers = new HashMap<>();
  }

  /**
   * Links an {@link Event} to a specific {@link Channel}
   *
   * @param contentType The {@link Event} to be registered
   * @param channel     The {@link Channel} that will be handling the {@link Event}
   */
  public void registerChannel(Class<? extends Event> contentType,
                              Channel<?> channel) {
    handlers.put(contentType, channel);
  }

  /**
   * Dispatches an {@link Event} depending on it's type.
   *
   * @param content The {@link Event} to be dispatched
   */
  public void dispatch(Event content) {
    handlers.get(content.getClass()).dispatch(content);
  }
}