package com.iluwatar.publish.subscribe.model;

import com.iluwatar.publish.subscribe.subscriber.Subscriber;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/** This class represents a Topic that topic name and subscribers. */
public class Topic {

  private final TopicName name;
  private final Set<Subscriber> subscribers = new CopyOnWriteArraySet<>();

  /**
   * Creates a new instance of the Topic class.
   *
   * @param name The name of the topic.
   */
  public Topic(TopicName name) {
    this.name = name;
  }

  /**
   * Get the name of the topic.
   *
   * @return topic name
   */
  public TopicName getName() {
    return name;
  }

  /**
   * Add a subscriber to the list of subscribers.
   *
   * @param subscriber subscriber to add
   */
  public void addSubscriber(Subscriber subscriber) {
    subscribers.add(subscriber);
  }

  /**
   * Remove a subscriber to the list of subscribers.
   *
   * @param subscriber subscriber to remove
   */
  public void removeSubscriber(Subscriber subscriber) {
    subscribers.remove(subscriber);
  }

  /**
   * Publish a message to subscribers.
   *
   * @param message message with content to publish
   */
  public void publish(Message message) {
    for (Subscriber subscriber : subscribers) {
      subscriber.onMessage(message);
    }
  }
}
