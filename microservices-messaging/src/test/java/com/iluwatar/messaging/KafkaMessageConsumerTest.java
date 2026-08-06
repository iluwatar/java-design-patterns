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
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for {@link KafkaMessageConsumer}. */
class KafkaMessageConsumerTest {

  private MockConsumer<String, String> mockConsumer;
  private KafkaMessageConsumer kafkaMessageConsumer;
  private AtomicBoolean handlerCalled;

  @BeforeEach
  void setUp() {
    mockConsumer = new MockConsumer<>(OffsetResetStrategy.EARLIEST);
    handlerCalled = new AtomicBoolean(false);
    kafkaMessageConsumer =
        new KafkaMessageConsumer(mockConsumer, "test-topic", msg -> handlerCalled.set(true));
  }

  @Test
  void testConsumerCanBeInstantiated() {
    assertNotNull(kafkaMessageConsumer, "KafkaMessageConsumer should be instantiated");
  }

  @Test
  void testRunProcessesValidMessageAndStops() throws Exception {
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    Message msg = new Message("Order Created: 123");
    String jsonStr = mapper.writeValueAsString(msg);

    TopicPartition tp = new TopicPartition("test-topic", 0);
    mockConsumer.updateBeginningOffsets(
        new HashMap<>() {
          {
            put(tp, 0L);
          }
        });

    mockConsumer.schedulePollTask(
        () -> {
          mockConsumer.rebalance(Collections.singletonList(tp));
          mockConsumer.addRecord(new ConsumerRecord<>("test-topic", 0, 0L, "key", jsonStr));
        });

    mockConsumer.schedulePollTask(() -> kafkaMessageConsumer.stop());

    kafkaMessageConsumer.run();

    assertTrue(handlerCalled.get(), "Handler should have been invoked");
    assertTrue(mockConsumer.closed(), "Consumer should be closed");
  }

  @Test
  void testRunHandlesInvalidJsonMessage() {
    TopicPartition tp = new TopicPartition("test-topic", 0);
    mockConsumer.updateBeginningOffsets(
        new HashMap<>() {
          {
            put(tp, 0L);
          }
        });

    mockConsumer.schedulePollTask(
        () -> {
          mockConsumer.rebalance(Collections.singletonList(tp));
          mockConsumer.addRecord(
              new ConsumerRecord<>("test-topic", 0, 0L, "key", "{invalid json"));
        });

    mockConsumer.schedulePollTask(() -> kafkaMessageConsumer.stop());

    assertDoesNotThrow(() -> kafkaMessageConsumer.run());
    assertTrue(mockConsumer.closed(), "Consumer should be closed");
  }

  @Test
  void testCloseStopsConsumer() {
    assertDoesNotThrow(() -> kafkaMessageConsumer.close());
  }
}
