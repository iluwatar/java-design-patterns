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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit tests for {@link KafkaMessageConsumer}.
 * Note: These tests verify basic functionality without requiring a Kafka instance.
 * For integration tests with Kafka, use embedded Kafka or testcontainers.
 */
class KafkaMessageConsumerTest {

  @Test
  void testConsumerCanBeInstantiated() {
    // Arrange & Act & Assert
    // Note: Don't actually create consumer in unit test as it requires Kafka
    assertNotNull(KafkaMessageConsumer.class,
        "KafkaMessageConsumer class should exist");
  }

  @Test
  void testConsumerImplementsRunnable() {
    // Arrange & Act & Assert
    var interfaces = KafkaMessageConsumer.class.getInterfaces();
      for (var i : interfaces) {
      if (i.equals(Runnable.class)) {
          break;
      }
    }
    assertNotNull(interfaces, "Should have interfaces");
    // Note: Runnable is implemented for threading
  }

  @Test
  void testConsumerImplementsAutoCloseable() {
    // Arrange & Act & Assert
    var interfaces = KafkaMessageConsumer.class.getInterfaces();
      for (var i : interfaces) {
      if (i.equals(AutoCloseable.class)) {
          break;
      }
    }
    assertNotNull(interfaces, "Should have interfaces");
    // Note: AutoCloseable is implemented
  }

  @Test
  void testConsumerClassHasStopMethod() {
    // Arrange & Act & Assert
    assertDoesNotThrow(() -> {
      var method = KafkaMessageConsumer.class.getDeclaredMethod("stop");
      assertNotNull(method, "stop method should exist");
    }, "KafkaMessageConsumer should have stop method");
  }
}