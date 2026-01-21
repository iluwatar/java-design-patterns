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
package com.iluwatar.transactionaloutbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests for {@link OutboxEvent}. */
class OutboxEventTests {

  @Test
  void newOutboxEventShouldBeUnprocessed() {
    var eventType = "CUSTOMER_CREATED";
    var payload = "{\"customerId\":1}";

    var event = new OutboxEvent(eventType, payload);

    assertNotNull(event);
    assertEquals(eventType, event.getEventType());
    assertEquals(payload, event.getPayload());
    assertFalse(event.isProcessed());
    assertNotNull(event.toString(), "toString should include createdAt value");
  }

  @Test
  void processedEventShouldBeMarkedAsProcessed() {
    var event = new OutboxEvent("TEST_EVENT", "payload");
    event.setId(1);
    event.setProcessed(true);

    assertTrue(event.isProcessed());
    assertEquals(Integer.valueOf(1), event.getId());
  }

  @Test
  void eventsShouldMaintainSequentialOrder() {
    var event1 = new OutboxEvent("EVENT_1", "payload1");
    var event2 = new OutboxEvent("EVENT_2", "payload2");

    event1.setSequenceNumber(1L);
    event2.setSequenceNumber(2L);

    assertEquals(Long.valueOf(1L), event1.getSequenceNumber());
    assertEquals(Long.valueOf(2L), event2.getSequenceNumber());
    assertTrue(event1.getSequenceNumber() < event2.getSequenceNumber());
  }

  @Test
  void shouldFormatToStringWithAllFields() {
    var event = new OutboxEvent("TEST_EVENT", "payload");
    event.setId(123);
    event.setSequenceNumber(456L);
    event.setProcessed(true);

    var toString = event.toString();

    assertTrue(toString.contains("id=123"), "toString should contain id");
    assertTrue(toString.contains("eventType='TEST_EVENT'"), "toString should contain eventType");
    assertTrue(toString.contains("payload='payload'"), "toString should contain payload");
    assertTrue(toString.contains("processed=true"), "toString should contain processed status");
    assertTrue(toString.contains("createdAt="), "toString should contain createdAt timestamp");
  }

  @Test
  void defaultConstructorShouldInitializeBasicFields() {
    var event = new OutboxEvent();

    assertNotNull(event.toString(), "toString should include createdAt value");
    assertFalse(event.isProcessed(), "Should not be processed by default");
  }
}
