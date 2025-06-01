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
package com.iluwatar.publish.subscribe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import com.iluwatar.publish.subscribe.subscriber.Subscriber;
import com.iluwatar.publish.subscribe.subscriber.WeatherSubscriber;
import java.lang.reflect.Field;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TopicTest {

  private static final String TOPIC_WEATHER = "WEATHER";

  @Test
  void testTopic() {
    Topic topic = new Topic(TOPIC_WEATHER);
    assertEquals(TOPIC_WEATHER, topic.getTopicName());
  }

  @Test
  void testSubscribing() throws NoSuchFieldException, IllegalAccessException {

    Topic topic = new Topic(TOPIC_WEATHER);
    Subscriber sub = new WeatherSubscriber();
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
