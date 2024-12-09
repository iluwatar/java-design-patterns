package com.iluwatar;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class EventPublisherTest {

  @Test
  void testPublishEvent() {
    EventPublisher eventPublisher = new EventPublisher();
    OrderCreatedEvent event = new OrderCreatedEvent("12345");
    eventPublisher.publishEvent(event);
  }
}
