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

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventPoller {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventPoller.class);
  private static final int MAX_RETRIES = 3;
  private static final long RETRY_DELAY_MS = 1000;
  private static final java.security.SecureRandom RANDOM = new java.security.SecureRandom();

  @Getter
  private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

  private final EntityManager entityManager;
  private final OutboxRepository outboxRepository;
  private final MessageBroker messageBroker;
  @Getter private long processedEventsCount = 0;
  @Getter private long failedEventsCount = 0;

  public EventPoller(EntityManager entityManager, MessageBroker messageBroker) {
    this.entityManager = entityManager;
    this.outboxRepository = new OutboxRepository(entityManager);
    this.messageBroker = messageBroker;
  }

  public void start() {
    scheduler.scheduleAtFixedRate(this::processOutboxEvents, 0, 2, TimeUnit.SECONDS);
    LOGGER.info("EventPoller started.");
  }

  public void stop() {
    scheduler.shutdown();
    LOGGER.info(
        "EventPoller stopped with {} events processed and {} failures.",
        processedEventsCount,
        failedEventsCount);
  }

  void processOutboxEvents() {
    LOGGER.info("Polling for unprocessed events...");
    entityManager.getTransaction().begin();
    try {
      List<OutboxEvent> events = outboxRepository.findUnprocessedEvents();
      if (events.isEmpty()) {
        LOGGER.info("No new events found.");
      } else {
        LOGGER.info("Found {} events to process.", events.size());
        for (var event : events) {
          processEventWithRetry(event);
        }
      }
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      LOGGER.error("Error processing outbox events, rolling back transaction.", e);
      entityManager.getTransaction().rollback();
      failedEventsCount++;
    }
  }

  private void processEventWithRetry(OutboxEvent event) {
    int retryCount = 0;
    while (retryCount < MAX_RETRIES) {
      try {
        messageBroker.sendMessage(event);
        outboxRepository.markAsProcessed(event);
        processedEventsCount++;
        LOGGER.info("Successfully processed event.");
        return;
      } catch (Exception e) {
        retryCount++;
        if (retryCount < MAX_RETRIES) {
          LOGGER.warn(
              "Failed to process event (attempt {}/{}). Retrying...", retryCount, MAX_RETRIES);
          try {
            long sleepTime =
                Math.min(RETRY_DELAY_MS * (1L << (retryCount - 1)) + RANDOM.nextLong(100), 10000L);
            Thread.sleep(sleepTime);
          } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            break;
          }
        } else {
          LOGGER.error("Failed to process event after {} attempts", MAX_RETRIES);
          failedEventsCount++;
        }
      }
    }
  }
}
