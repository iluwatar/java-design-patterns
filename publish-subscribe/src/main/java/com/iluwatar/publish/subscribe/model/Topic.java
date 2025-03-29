package com.iluwatar.publish.subscribe.model;

import com.iluwatar.publish.subscribe.subscriber.Subscriber;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class Topic {

  private final TopicName name;
  private final Set<Subscriber> subscribers = new CopyOnWriteArraySet<>();

  public Topic(TopicName name) {
    this.name = name;
  }

  public TopicName getName() {
    return name;
  }

  public void addSubscriber(Subscriber subscriber) {
    subscribers.add(subscriber);
  }

  public void removeSubscriber(Subscriber subscriber) {
    subscribers.remove(subscriber);
  }

  public void publish(Message message) {
    for (Subscriber subscriber : subscribers) {
      subscriber.onMessage(message);
    }
  }
}
