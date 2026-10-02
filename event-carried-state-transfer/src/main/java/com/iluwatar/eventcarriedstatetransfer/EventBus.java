/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.eventcarriedstatetransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

/**
 * Minimal in-memory, synchronous publish/subscribe channel.
 *
 * <p>Producers publish events, subscribers register for an event class and receive every event of
 * that class in subscription order. In production this role is played by a message broker such as
 * Kafka or RabbitMQ; here it is kept synchronous so the pattern stays easy to follow and to test.
 */
@Slf4j
public class EventBus {

  private final Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

  /**
   * Registers a listener for events of the given class.
   *
   * <p>Dispatch is by exact runtime class, so the listener receives only events whose class is
   * exactly {@code eventType}, never those of a subclass.
   *
   * @param eventType the class of events to receive
   * @param listener the listener to notify
   * @param <E> the event type
   */
  public <E> void subscribe(Class<E> eventType, EventListener<? super E> listener) {
    var subscribers = listeners.computeIfAbsent(eventType, key -> new ArrayList<>());
    subscribers.add(listener);
    LOGGER.info("Subscriber {} registered for {}", subscribers.size(), eventType.getSimpleName());
  }

  /**
   * Delivers the event to every listener subscribed to its class.
   *
   * <p>Only listeners subscribed to the event's exact runtime class are notified; a listener
   * subscribed to a supertype of the event does not receive it.
   *
   * @param event the event to publish
   * @throws NullPointerException if the event is {@code null}
   */
  public void publish(Object event) {
    Objects.requireNonNull(event, "event");
    var subscribers = listeners.getOrDefault(event.getClass(), List.of());
    if (subscribers.isEmpty()) {
      LOGGER.warn("No subscribers for {}", event.getClass().getSimpleName());
      return;
    }
    LOGGER.info(
        "Publishing {} to {} subscriber(s)", event.getClass().getSimpleName(), subscribers.size());
    for (var listener : subscribers) {
      deliver(listener, event);
    }
  }

  @SuppressWarnings("unchecked")
  private static <E> void deliver(EventListener<E> listener, Object event) {
    listener.onEvent((E) event);
  }
}
