package com.iluwatar.publish.subscribe.subscriber;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.publish.subscribe.LoggerExtension;
import com.iluwatar.publish.subscribe.model.Message;
import com.iluwatar.publish.subscribe.model.Topic;
import com.iluwatar.publish.subscribe.publisher.Publisher;
import com.iluwatar.publish.subscribe.publisher.PublisherImpl;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SubscriberTest {

  @RegisterExtension public LoggerExtension loggerExtension = new LoggerExtension();

  private static final String TOPIC_WEATHER = "WEATHER";
  private static final String TOPIC_TEMPERATURE = "TEMPERATURE";
  private static final String TOPIC_CUSTOMER_SUPPORT = "CUSTOMER_SUPPORT";

  @Test
  void testSubscribeToMultipleTopics() {

    Topic topicWeather = new Topic(TOPIC_WEATHER);
    Topic topicTemperature = new Topic(TOPIC_TEMPERATURE);
    Subscriber weatherSubscriber = new WeatherSubscriber();

    topicWeather.addSubscriber(weatherSubscriber);
    topicTemperature.addSubscriber(weatherSubscriber);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(topicWeather);
    publisher.registerTopic(topicTemperature);

    publisher.publish(topicWeather, new Message("earthquake"));
    publisher.publish(topicTemperature, new Message("-2C"));

    waitForOutput();
    assertEquals(2, loggerExtension.getFormattedMessages().size());
  }

  @Test
  void testOnlyReceiveSubscribedTopic() {

    Topic weatherTopic = new Topic(TOPIC_WEATHER);
    Subscriber weatherSubscriber = new WeatherSubscriber();
    weatherTopic.addSubscriber(weatherSubscriber);

    Topic customerSupportTopic = new Topic(TOPIC_CUSTOMER_SUPPORT);
    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);
    publisher.registerTopic(customerSupportTopic);

    publisher.publish(customerSupportTopic, new Message("support@test.de"));

    waitForOutput();
    assertEquals(0, loggerExtension.getFormattedMessages().size());
  }

  @Test
  void testMultipleSubscribersOnSameTopic() throws InterruptedException {

    Topic weatherTopic = new Topic(TOPIC_WEATHER);
    Subscriber weatherSubscriber1 = new WeatherSubscriber();
    weatherTopic.addSubscriber(weatherSubscriber1);

    Subscriber weatherSubscriber2 = new WeatherSubscriber();
    weatherTopic.addSubscriber(weatherSubscriber2);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);

    publisher.publish(weatherTopic, new Message("tornado"));

    waitForOutput();
    assertEquals(2, loggerExtension.getFormattedMessages().size());
    assertEquals(
        "Weather Subscriber: " + weatherSubscriber1.hashCode() + " issued message: tornado",
        getMessage(weatherSubscriber1.hashCode()));
    assertEquals(
        "Weather Subscriber: " + weatherSubscriber2.hashCode() + " issued message: tornado",
        getMessage(weatherSubscriber2.hashCode()));
  }

  @Test
  void testMultipleSubscribersOnDifferentTopics() throws InterruptedException {

    Topic weatherTopic = new Topic(TOPIC_WEATHER);
    Subscriber weatherSubscriber = new WeatherSubscriber();
    weatherTopic.addSubscriber(weatherSubscriber);

    Topic customerSupportTopic = new Topic(TOPIC_CUSTOMER_SUPPORT);
    Subscriber customerSupportSubscriber = new CustomerSupportSubscriber();
    customerSupportTopic.addSubscriber(customerSupportSubscriber);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);
    publisher.registerTopic(customerSupportTopic);

    publisher.publish(weatherTopic, new Message("flood"));
    publisher.publish(customerSupportTopic, new Message("support@test.at"));

    waitForOutput();
    assertEquals(2, loggerExtension.getFormattedMessages().size());
    assertEquals(
        "Weather Subscriber: " + weatherSubscriber.hashCode() + " issued message: flood",
        getMessage(weatherSubscriber.hashCode()));
    assertEquals(
        "Customer Support Subscriber: "
            + customerSupportSubscriber.hashCode()
            + " sent the email to: support@test.at",
        getMessage(customerSupportSubscriber.hashCode()));
  }

  @Test
  void testInvalidContentOnTopics() throws InterruptedException {

    Topic weatherTopic = new Topic(TOPIC_WEATHER);
    Subscriber weatherSubscriber = new WeatherSubscriber();
    weatherTopic.addSubscriber(weatherSubscriber);

    Topic customerSupportTopic = new Topic(TOPIC_CUSTOMER_SUPPORT);
    Subscriber customerSupportSubscriber = new CustomerSupportSubscriber();
    customerSupportTopic.addSubscriber(customerSupportSubscriber);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);
    publisher.registerTopic(customerSupportTopic);

    publisher.publish(weatherTopic, new Message(123));
    publisher.publish(customerSupportTopic, new Message(34.56));

    waitForOutput();
    assertTrue(loggerExtension.getFormattedMessages().getFirst().contains("Unknown content type"));
    assertTrue(loggerExtension.getFormattedMessages().get(1).contains("Unknown content type"));
  }

  @Test
  void testUnsubscribe() throws InterruptedException {

    Topic weatherTopic = new Topic(TOPIC_WEATHER);
    Subscriber weatherSubscriber = new WeatherSubscriber();
    weatherTopic.addSubscriber(weatherSubscriber);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);

    publisher.publish(weatherTopic, new Message("earthquake"));

    weatherTopic.removeSubscriber(weatherSubscriber);
    publisher.publish(weatherTopic, new Message("tornado"));

    waitForOutput();
    assertEquals(1, loggerExtension.getFormattedMessages().size());
    assertTrue(loggerExtension.getFormattedMessages().getFirst().contains("earthquake"));
    assertFalse(loggerExtension.getFormattedMessages().getFirst().contains("tornado"));
  }

  private String getMessage(int subscriberHash) {
    Optional<String> message =
        loggerExtension.getFormattedMessages().stream()
            .filter(str -> str.contains(String.valueOf(subscriberHash)))
            .findFirst();
    assertTrue(message.isPresent());
    return message.get();
  }

  private void waitForOutput() {
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
