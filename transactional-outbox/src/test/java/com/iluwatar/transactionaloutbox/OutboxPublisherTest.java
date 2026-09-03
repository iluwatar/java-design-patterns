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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OutboxPublisherTest {

  @Autowired private OrderService orderService;

  @Autowired private OutboxPublisher outboxPublisher;

  @Autowired private OutboxRepository outboxRepository;

  @Autowired private MessageConsumer messageConsumer;

  @BeforeEach
  void setUp() {
    outboxRepository.deleteAll();
    messageConsumer.clearMessages();
  }

  @Test
  void testProcessOutboxEventsWhenNoPendingEvents() {
    List<OutboxEvent> processed = outboxPublisher.processOutboxEvents();
    assertTrue(processed.isEmpty());
  }

  @Test
  void testProcessOutboxEventsPublishesAndUpdatesStatusToProcessed() {
    orderService.createOrder("Jane Smith", "Tablet", 499.00);

    List<OutboxEvent> pendingBefore = outboxRepository.findByStatus(EventStatus.PENDING);
    assertEquals(1, pendingBefore.size());

    List<OutboxEvent> processedEvents = outboxPublisher.processOutboxEvents();
    assertEquals(1, processedEvents.size());

    List<OutboxEvent> pendingAfter = outboxRepository.findByStatus(EventStatus.PENDING);
    assertEquals(0, pendingAfter.size());

    List<OutboxEvent> processedAfter = outboxRepository.findByStatus(EventStatus.PROCESSED);
    assertEquals(1, processedAfter.size());
    assertNotNull(processedAfter.get(0).getProcessedAt());

    assertEquals(1, messageConsumer.getConsumedMessages().size());
  }
}
