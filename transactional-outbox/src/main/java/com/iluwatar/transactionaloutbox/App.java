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

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Transactional Outbox Pattern Demonstration Application.
 *
 * <p>The Transactional Outbox pattern ensures reliable event publishing in microservices by
 * persisting events into a database outbox table within the same database transaction as the
 * business entity update. A background polling process then dispatches pending outbox events to a
 * message broker.
 */
@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class App implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  private final OrderService orderService;
  private final OutboxPublisher outboxPublisher;
  private final MessageConsumer messageConsumer;

  /**
   * Main entry point for the Spring Boot Application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Override
  public void run(String... args) {
    LOGGER.info("Starting Transactional Outbox Pattern demonstration...");

    // 1. Create order and outbox record in a single transaction
    var order1 = orderService.createOrder("Alice", "Laptop", 1200.00);
    var order2 = orderService.createOrder("Bob", "Headphones", 150.00);

    LOGGER.info("Created orders with IDs: [{}], [{}]", order1.getId(), order2.getId());

    // 2. Poll and publish outbox events to message broker
    LOGGER.info("Triggering OutboxPublisher to process pending events...");
    outboxPublisher.processOutboxEvents();

    // 3. Inspect consumed events
    LOGGER.info(
        "Total messages consumed by MessageConsumer: {}",
        messageConsumer.getConsumedMessages().size());
  }
}
