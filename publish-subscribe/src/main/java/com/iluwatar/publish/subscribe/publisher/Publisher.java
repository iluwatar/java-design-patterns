package com.iluwatar.publish.subscribe.publisher;

import com.iluwatar.publish.subscribe.model.Message;
import com.iluwatar.publish.subscribe.model.Topic;

/** This class represents a Publisher. */
public interface Publisher {

  /**
   * Register a topic in the publisher.
   *
   * @param topic the topic to be registered
   */
  void registerTopic(Topic topic);

  /**
   * Register a topic in the publisher.
   *
   * @param topic the topic to publish the message under
   * @param message message with content to be published
   */
  void publish(Topic topic, Message message);
}
