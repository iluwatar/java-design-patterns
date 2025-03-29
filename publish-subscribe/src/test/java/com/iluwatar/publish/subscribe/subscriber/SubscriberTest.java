package com.iluwatar.publish.subscribe.subscriber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.publish.subscribe.LoggerExtension;
import com.iluwatar.publish.subscribe.model.CustomerSupportContent;
import com.iluwatar.publish.subscribe.model.Message;
import com.iluwatar.publish.subscribe.model.Topic;
import com.iluwatar.publish.subscribe.model.TopicName;
import com.iluwatar.publish.subscribe.model.WeatherContent;
import com.iluwatar.publish.subscribe.publisher.Publisher;
import com.iluwatar.publish.subscribe.publisher.PublisherImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SubscriberTest {

  @RegisterExtension public LoggerExtension loggerExtension = new LoggerExtension();

  private static final String WEATHER_SUB_NAME = "weatherSubscriber";
  private static final String CUSTOMER_SUPPORT_SUB_NAME = "customerSupportSubscriber";

  @Test
  void testSubscribeSuccess() {

    Topic topic = new Topic(TopicName.WEATHER);
    Subscriber weatherSubscriber = new WeatherSubscriber(WEATHER_SUB_NAME);
    topic.addSubscriber(weatherSubscriber);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(topic);

    publisher.publish(topic, new Message(WeatherContent.earthquake));
    assertEquals(
        "Subscriber: weatherSubscriber issued message: earthquake tsunami warning",
        loggerExtension.getFormattedMessages().getFirst());
  }

  @Test
  void testOnlyReceiveSubscribedTopic() {

    Topic weatherTopic = new Topic(TopicName.WEATHER);
    Subscriber weatherSubscriber = new WeatherSubscriber(WEATHER_SUB_NAME);
    weatherTopic.addSubscriber(weatherSubscriber);

    Topic customerSupportTopic = new Topic(TopicName.CUSTOMER_SUPPORT);
    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);
    publisher.registerTopic(customerSupportTopic);

    publisher.publish(customerSupportTopic, new Message(CustomerSupportContent.DE));
    assertEquals(0, loggerExtension.getFormattedMessages().size());
  }

  @Test
  void testMultipleSubscribersOnSameTopic() {

    Topic weatherTopic = new Topic(TopicName.WEATHER);
    Subscriber weatherSubscriber1 = new WeatherSubscriber(WEATHER_SUB_NAME + "1");
    weatherTopic.addSubscriber(weatherSubscriber1);

    Subscriber weatherSubscriber2 = new WeatherSubscriber(WEATHER_SUB_NAME + "2");
    weatherTopic.addSubscriber(weatherSubscriber2);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);

    publisher.publish(weatherTopic, new Message(WeatherContent.tornado));
    assertEquals(
        "Subscriber: weatherSubscriber1 issued message: tornado use storm cellars",
        loggerExtension.getFormattedMessages().getFirst());
    assertEquals(
        "Subscriber: weatherSubscriber2 issued message: tornado use storm cellars",
        loggerExtension.getFormattedMessages().get(1));
  }

  @Test
  void testMultipleSubscribersOnDifferentTopics() {

    Topic weatherTopic = new Topic(TopicName.WEATHER);
    Subscriber weatherSubscriber = new WeatherSubscriber(WEATHER_SUB_NAME);
    weatherTopic.addSubscriber(weatherSubscriber);

    Topic customerSupportTopic = new Topic(TopicName.CUSTOMER_SUPPORT);
    Subscriber customerSupportSubscriber = new CustomerSupportSubscriber(CUSTOMER_SUPPORT_SUB_NAME);
    customerSupportTopic.addSubscriber(customerSupportSubscriber);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);
    publisher.registerTopic(customerSupportTopic);

    publisher.publish(weatherTopic, new Message(WeatherContent.flood));
    publisher.publish(customerSupportTopic, new Message(CustomerSupportContent.AT));
    assertEquals(
        "Subscriber: weatherSubscriber issued message: flood start evacuation",
        loggerExtension.getFormattedMessages().getFirst());
    assertEquals(
        "Subscriber: customerSupportSubscriber sent the email to: customer.support@test.at",
        loggerExtension.getFormattedMessages().get(1));
  }

  @Test
  void testInvalidContentOnTopics() {

    Topic weatherTopic = new Topic(TopicName.WEATHER);
    Subscriber weatherSubscriber = new WeatherSubscriber(WEATHER_SUB_NAME);
    weatherTopic.addSubscriber(weatherSubscriber);

    Topic customerSupportTopic = new Topic(TopicName.CUSTOMER_SUPPORT);
    Subscriber customerSupportSubscriber = new CustomerSupportSubscriber(CUSTOMER_SUPPORT_SUB_NAME);
    customerSupportTopic.addSubscriber(customerSupportSubscriber);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);
    publisher.registerTopic(customerSupportTopic);

    publisher.publish(weatherTopic, new Message(CustomerSupportContent.DE));
    publisher.publish(customerSupportTopic, new Message(WeatherContent.earthquake));
    assertTrue(loggerExtension.getFormattedMessages().getFirst().contains("Unknown content type"));
    assertTrue(loggerExtension.getFormattedMessages().get(1).contains("Unknown content type"));
  }

  @Test
  void testUnsubscribe() {

    Topic weatherTopic = new Topic(TopicName.WEATHER);
    Subscriber weatherSubscriber = new WeatherSubscriber(WEATHER_SUB_NAME);
    weatherTopic.addSubscriber(weatherSubscriber);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);

    publisher.publish(weatherTopic, new Message(WeatherContent.earthquake));

    weatherTopic.removeSubscriber(weatherSubscriber);
    publisher.publish(weatherTopic, new Message(WeatherContent.tornado));

    assertEquals(1, loggerExtension.getFormattedMessages().size());
    assertTrue(loggerExtension.getFormattedMessages().getFirst().contains("earthquake"));
    assertFalse(loggerExtension.getFormattedMessages().getFirst().contains("tornado"));
  }
}
