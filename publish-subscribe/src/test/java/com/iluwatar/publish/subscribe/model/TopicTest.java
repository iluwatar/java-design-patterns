package com.iluwatar.publish.subscribe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.iluwatar.publish.subscribe.subscriber.Subscriber;
import com.iluwatar.publish.subscribe.subscriber.WeatherSubscriber;
import java.lang.reflect.Field;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class TopicTest {

  @Test
  void testTopic() {
    Topic topic = new Topic(TopicName.WEATHER);
    assertEquals(TopicName.WEATHER, topic.getName());
  }

  @Test
  void testSubscribing() throws NoSuchFieldException, IllegalAccessException {

    final String subscriberName = "subscriberName";
    Topic topic = new Topic(TopicName.WEATHER);
    Subscriber sub = new WeatherSubscriber(subscriberName);
    topic.addSubscriber(sub);

    Field field = topic.getClass().getDeclaredField("subscribers");
    field.setAccessible(true);
    Object value = field.get(topic);
    assertInstanceOf(Set.class, value);

    Set<?> subscribers = (Set<?>) field.get(topic);
    assertEquals(1, subscribers.size());

    topic.removeSubscriber(sub);
    assertEquals(0, subscribers.size());
  }
}
