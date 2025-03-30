/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriberTest {

  private static final Logger logger = LoggerFactory.getLogger(SubscriberTest.class);
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
  void testMultipleSubscribersOnSameTopic() {

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
  void testMultipleSubscribersOnDifferentTopics() {

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
  void testInvalidContentOnTopics() {

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
  void testUnsubscribe() {

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
      logger.error("Interrupted!", e);
      Thread.currentThread().interrupt();
    }
  }
}
