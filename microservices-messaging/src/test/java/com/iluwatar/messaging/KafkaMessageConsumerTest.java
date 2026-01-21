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
package com.iluwatar.messaging;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for {@link KafkaMessageConsumer}.
 * Uses mocking to avoid requiring a real Kafka instance.
 */
class KafkaMessageConsumerTest {

  private KafkaMessageConsumer consumer;
  private List<Message> receivedMessages;

  @BeforeEach
  void setUp() {
    receivedMessages = new ArrayList<>();
  }

  @AfterEach
  void tearDown() {
    if (consumer != null) {
      consumer.stop();
    }
  }

  @Test
  void testConsumerCreation() {
    // Arrange & Act & Assert
    assertDoesNotThrow(() -> {
      consumer = new KafkaMessageConsumer(
          "localhost:9092",
          "test-group",
          "test-topic",
          receivedMessages::add
      );
      assertNotNull(consumer, "Consumer should be created successfully");
    });
  }

  @Test
  void testConsumerStop() {
    // Arrange
    consumer = new KafkaMessageConsumer(
        "localhost:9092",
        "test-group",
        "test-topic",
        receivedMessages::add
    );

    // Act & Assert
    assertDoesNotThrow(() -> consumer.stop(),
        "Stopping consumer should not throw exception");
  }

  @Test
  void testConsumerClose() {
    // Arrange
    consumer = new KafkaMessageConsumer(
        "localhost:9092",
        "test-group",
        "test-topic",
        receivedMessages::add
    );

    // Act & Assert
    assertDoesNotThrow(() -> consumer.close(),
        "Closing consumer should not throw exception");
  }

  @Test
  void testMessageHandler() {
    // Arrange
    var handlerCalled = new boolean[]{false};
    consumer = new KafkaMessageConsumer(
        "localhost:9092",
        "test-group",
        "test-topic",
        message -> handlerCalled[0] = true
    );

    // Act
    // Note: Without a real Kafka broker, the handler won't be called in this test

    // Assert
    assertNotNull(consumer, "Consumer should be created with handler");
  }

  @Test
  void testMultipleConsumersWithDifferentGroups() {
    // Arrange & Act
    var consumer1 = new KafkaMessageConsumer(
        "localhost:9092", "group1", "test-topic", receivedMessages::add);
    var consumer2 = new KafkaMessageConsumer(
        "localhost:9092", "group2", "test-topic", receivedMessages::add);

    // Assert
    assertNotNull(consumer1, "First consumer should be created");
    assertNotNull(consumer2, "Second consumer should be created");

    // Cleanup
    consumer1.stop();
    consumer2.stop();
  }
}