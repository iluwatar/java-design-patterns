package com.iluwatar.eda.framework;

import com.iluwatar.eda.event.Event;

/**
 * Channels are delivery points for messages. Every {@link Channel} is responsible for a single type
 * of message
 */
public interface Channel<E extends Message> {
  void dispatch(Event message);
}