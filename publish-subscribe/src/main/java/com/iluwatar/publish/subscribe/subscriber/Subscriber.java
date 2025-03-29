package com.iluwatar.publish.subscribe.subscriber;

import com.iluwatar.publish.subscribe.model.Message;

/** This class represents a Subscriber. */
public interface Subscriber {

  /**
   * On message method will trigger when the subscribed event is published.
   *
   * @param message the message contains the content of the published event
   */
  void onMessage(Message message);
}
