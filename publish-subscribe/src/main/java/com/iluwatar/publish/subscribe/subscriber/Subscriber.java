package com.iluwatar.publish.subscribe.subscriber;

import com.iluwatar.publish.subscribe.model.Message;

public interface Subscriber {
  void onMessage(Message message);
}
