package com.iluwatar.eda.event;

import com.iluwatar.eda.framework.EventDispatcher;
import com.iluwatar.eda.framework.Message;

/**
 * The {@link Event} class serves as a base class for defining custom events happening with your
 * system. In this example we have two types of events defined.
 * <ul>
 *   <li>{@link UserCreatedEvent} - used when a user is created</li>
 *   <li>{@link UserUpdatedEvent} - used when a user is updated</li>
 * </ul>
 * Events can be distinguished using the {@link #getType() getType} method.
 */
public class Event implements Message {

  /**
   * Returns the event type as a {@link Class} object
   * In this example, this method is used by the {@link EventDispatcher} to
   * dispatch events depending on their type.
   *
   * @return the Event type as a {@link Class}.
   */
  public Class<? extends Message> getType() {
    return getClass();
  }
}