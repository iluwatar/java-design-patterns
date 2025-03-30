package com.iluwatar.publish.subscribe.model;

import com.iluwatar.publish.subscribe.subscriber.Subscriber;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArraySet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/** This class represents a Topic that topic name and subscribers. */
@Getter
@Setter
@RequiredArgsConstructor
public class Topic {

  private final String topicName;
  private final Set<Subscriber> subscribers = new CopyOnWriteArraySet<>();

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
      CompletableFuture.runAsync(() -> subscriber.onMessage(message));
    }
  }
}
