package com.iluwatar.publish.subscribe.publisher;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.iluwatar.publish.subscribe.LoggerExtension;
import com.iluwatar.publish.subscribe.model.Message;
import com.iluwatar.publish.subscribe.model.Topic;
import java.lang.reflect.Field;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class PublisherTest {

  @RegisterExtension public LoggerExtension loggerExtension = new LoggerExtension();

  private static final String TOPIC_WEATHER = "WEATHER";
  private static final String TOPIC_CUSTOMER_SUPPORT = "CUSTOMER_SUPPORT";

  @Test
  void testRegisterTopic() throws NoSuchFieldException, IllegalAccessException {
    Topic topic = new Topic(TOPIC_CUSTOMER_SUPPORT);
    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(topic);

    Field field = publisher.getClass().getDeclaredField("topics");
    field.setAccessible(true);
    Object value = field.get(publisher);
    assertInstanceOf(Set.class, value);

    Set<?> topics = (Set<?>) field.get(publisher);
    assertEquals(1, topics.size());
  }

  @Test
  void testPublish() {
    Topic topic = new Topic(TOPIC_WEATHER);
    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(topic);

    Message message = new Message("weather");
    assertDoesNotThrow(() -> publisher.publish(topic, message));
  }

  @Test
  void testPublishUnregisteredTopic() {
    Topic topic = new Topic(TOPIC_WEATHER);
    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(topic);

    Topic topicUnregistered = new Topic(TOPIC_CUSTOMER_SUPPORT);
    Message message = new Message("support");
    publisher.publish(topicUnregistered, message);
    assertEquals(
        "This topic is not registered: CUSTOMER_SUPPORT",
        loggerExtension.getFormattedMessages().getFirst());
  }
}
