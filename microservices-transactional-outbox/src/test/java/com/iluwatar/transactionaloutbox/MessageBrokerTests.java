package com.iluwatar.transactionaloutbox;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MessageBrokerTests {
  private final MessageBroker messageBroker = new MessageBroker();

  @Test
  void shouldSendMessageSuccessfully() {
    var event = new OutboxEvent("TEST_EVENT", "test_payload");
    messageBroker.sendMessage(event);
    assertFalse(Thread.interrupted(), "Thread should not be interrupted");
  }

  @Test
  void shouldHandleInterruptedException() {
    var event = new OutboxEvent("TEST_EVENT", "test_payload");
    Thread.currentThread().interrupt();

    messageBroker.sendMessage(event);

    assertTrue(Thread.interrupted(), "Thread interrupt flag should be preserved");
  }
}
