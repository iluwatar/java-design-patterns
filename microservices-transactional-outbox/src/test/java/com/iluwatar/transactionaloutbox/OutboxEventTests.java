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
