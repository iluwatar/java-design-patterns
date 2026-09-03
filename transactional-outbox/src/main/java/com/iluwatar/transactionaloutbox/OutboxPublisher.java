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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** Background publisher polling PENDING outbox events and dispatching to message broker. */
@Component
public class OutboxPublisher {

  private static final Logger LOGGER = LoggerFactory.getLogger(OutboxPublisher.class);

  private final OutboxRepository outboxRepository;
  private final MessageBroker messageBroker;

  public OutboxPublisher(OutboxRepository outboxRepository, MessageBroker messageBroker) {
    this.outboxRepository = outboxRepository;
    this.messageBroker = messageBroker;
  }

  /**
   * Periodically polls pending outbox events from database and dispatches them to the message
   * broker.
   */
  @Scheduled(fixedDelay = 5000)
  public void publishOutboxEvents() {
    processOutboxEvents();
  }

  /**
   * Process pending outbox events from the database and dispatch them to the message broker.
   *
   * @return list of processed outbox events
   */
  public List<OutboxEvent> processOutboxEvents() {
    List<OutboxEvent> pendingEvents = outboxRepository.findByStatus(EventStatus.PENDING);
    if (pendingEvents.isEmpty()) {
      return pendingEvents;
    }

    LOGGER.info("Found [{}] PENDING outbox events to publish", pendingEvents.size());

    for (OutboxEvent event : pendingEvents) {
      try {
        messageBroker.publish("order-events", event.getPayload());
        event.setStatus(EventStatus.PROCESSED);
        event.setProcessedAt(LocalDateTime.now(ZoneOffset.UTC));
        outboxRepository.save(event);
        LOGGER.info("Successfully published outbox event ID [{}]", event.getId());
      } catch (Exception e) {
        LOGGER.error("Failed to publish outbox event ID [{}]", event.getId(), e);
        event.setStatus(EventStatus.FAILED);
        outboxRepository.save(event);
      }
    }

    return pendingEvents;
  }
}
