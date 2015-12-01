package com.iluwatar.eda.framework;

/**
 * A {@link Message} is an object with a specific type that is associated
 * to a specific {@link Handler}.
 */
public interface Message {

  /**
   * Returns the message type as a {@link Class} object. In this example the message type is
   * used to handle events by their type.
   * @return the message type as a {@link Class}.
   */
  Class<? extends Message> getType();
}
