package com.iluwatar.publish.subscribe.publisher;

import com.iluwatar.publish.subscribe.model.Message;
import com.iluwatar.publish.subscribe.model.Topic;

public interface Publisher {

    void registerTopic(Topic topic);

    void publish(Topic topic, Message message);
}
