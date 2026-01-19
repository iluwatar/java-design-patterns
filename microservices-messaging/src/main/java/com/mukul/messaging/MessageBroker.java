package com.mukul.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * MessageBroker acts as the intermediary that routes messages between producers and consumers.
 * It implements the publish-subscribe pattern where multiple consumers can subscribe to topics
 * and receive messages published to those topics.
 */
public class MessageBroker {
  private static final Logger LOGGER = LoggerFactory.getLogger(MessageBroker.class);
  private final Map<String, List<Consumer<Message>>> subscribers = new ConcurrentHashMap<>();

  /**
   * Subscribe to a topic to receive messages.
   *
   * @param topic   the topic to subscribe to
   * @param handler the message handler to process received messages
   */
  public void subscribe(String topic, Consumer<Message> handler) {
    subscribers.computeIfAbsent(topic, k -> new ArrayList<>()).add(handler);
    LOGGER.info("New subscriber added to topic: {}", topic);
  }

  /**
   * Publish a message to a topic.
   *
   * @param topic   the topic to publish to
   * @param message the message to publish
   */
  public void publish(String topic, Message message) {
    LOGGER.info("Publishing message to topic '{}': {}", topic, message.getId());
    List<Consumer<Message>> handlers = subscribers.get(topic);
    if (handlers != null) {
      handlers.forEach(handler -> handler.accept(message));
    } else {
      LOGGER.warn("No subscribers found for topic: {}", topic);
    }
  }

  /**
   * Get the number of subscribers for a topic.
   *
   * @param topic the topic to check
   * @return the number of subscribers
   */
  public int getSubscriberCount(String topic) {
    List<Consumer<Message>> handlers = subscribers.get(topic);
    return handlers != null ? handlers.size() : 0;
  }
}