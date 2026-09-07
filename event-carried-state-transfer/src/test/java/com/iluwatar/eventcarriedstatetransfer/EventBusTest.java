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
package com.iluwatar.eventcarriedstatetransfer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class EventBusTest {

  private final EventBus bus = new EventBus();

  @Test
  void deliversEventsToSubscribersOfTheirType() {
    var received = new ArrayList<String>();
    bus.subscribe(String.class, received::add);

    bus.publish("hello");
    bus.publish("world");

    assertEquals(List.of("hello", "world"), received);
  }

  @Test
  void doesNotDeliverEventsOfOtherTypes() {
    var received = new ArrayList<String>();
    bus.subscribe(String.class, received::add);

    bus.publish(42);

    assertTrue(received.isEmpty());
  }

  @Test
  void publishingWithoutSubscribersIsHarmless() {
    assertDoesNotThrow(() -> bus.publish("nobody listens"));
  }

  @Test
  void rejectsNullEvents() {
    var exception = assertThrows(NullPointerException.class, () -> bus.publish(null));

    assertEquals("event", exception.getMessage());
  }

  @Test
  void deliversInSubscriptionOrder() {
    var order = new ArrayList<String>();
    bus.subscribe(String.class, event -> order.add("first:" + event));
    bus.subscribe(String.class, event -> order.add("second:" + event));

    bus.publish("e");

    assertEquals(List.of("first:e", "second:e"), order);
  }
}
