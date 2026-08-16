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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for {@link App}. Tests main application entry point. */
class AppTest {

  @BeforeEach
  void setUp() {
    // Speed up sleeps so tests finish instantly
    App.sleepMs = 0;
  }

  @AfterEach
  void tearDown() {
    // Restore default so other contexts are unaffected
    App.sleepMs = 2000;
  }

  @Test
  void testAppConstructor() {
    assertNotNull(new App(), "App should be instantiable");
  }

  @Test
  void testRunWithMockObjects() {
    // Build mock-backed producer and consumers — no Kafka broker required
    MockProducer<String, String> mockProducer =
        new MockProducer<>(true, new StringSerializer(), new StringSerializer());
    KafkaMessageProducer producer = new KafkaMessageProducer(mockProducer);

    MockConsumer<String, String> mc1 = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
    MockConsumer<String, String> mc2 = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
    MockConsumer<String, String> mc3 = new MockConsumer<>(OffsetResetStrategy.EARLIEST);

    KafkaMessageConsumer inventoryConsumer =
        new KafkaMessageConsumer(mc1, "order-topic", msg -> {});
    KafkaMessageConsumer paymentConsumer = new KafkaMessageConsumer(mc2, "order-topic", msg -> {});
    KafkaMessageConsumer notificationConsumer =
        new KafkaMessageConsumer(mc3, "order-topic", msg -> {});

    // Stop consumers immediately so their poll loops exit right away in the executor threads
    inventoryConsumer.stop();
    paymentConsumer.stop();
    notificationConsumer.stop();

    // sleepMs == 0, so all Thread.sleep(sleepMs) return instantly — full run() coverage
    assertDoesNotThrow(
        () -> App.run(producer, inventoryConsumer, paymentConsumer, notificationConsumer),
        "App.run() should complete without throwing");
  }
}
