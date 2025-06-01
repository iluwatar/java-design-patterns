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

class PublisherTest {

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
