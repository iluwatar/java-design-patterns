package com.iluwatar.publish.subscribe.publisher;

import com.iluwatar.publish.subscribe.model.Message;
import com.iluwatar.publish.subscribe.model.Topic;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublisherImpl implements Publisher {

  private static final Logger logger = LoggerFactory.getLogger(PublisherImpl.class);
  private final Set<Topic> topics = new HashSet<>();

  @Override
  public void registerTopic(Topic topic) {
    topics.add(topic);
  }

  @Override
  public void publish(Topic topic, Message message) {
    if (!topics.contains(topic)) {
      logger.error("This topic is not registered: {}", topic.getName());
      return;
    }
    topic.publish(message);
  }
}
