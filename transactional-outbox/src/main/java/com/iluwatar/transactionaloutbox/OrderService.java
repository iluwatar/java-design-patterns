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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service responsible for managing orders and writing outbox events atomically. */
@Service
@RequiredArgsConstructor
public class OrderService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

  private final OrderRepository orderRepository;
  private final OutboxRepository outboxRepository;

  /**
   * Creates a new order and inserts an OutboxEvent into the database atomically within a single
   * transaction boundary.
   *
   * @param customerName name of the customer
   * @param productName name of the product
   * @param amount purchase amount
   * @return persisted Order entity
   */
  @Transactional
  public Order createOrder(String customerName, String productName, double amount) {
    var now = LocalDateTime.now(ZoneOffset.UTC);

    var order =
        Order.builder()
            .customerName(customerName)
            .productName(productName)
            .amount(amount)
            .status(OrderStatus.CREATED)
            .createdAt(now)
            .build();

    var savedOrder = orderRepository.save(order);
    LOGGER.info("Saved order with ID [{}] in database", savedOrder.getId());

    var outboxEvent =
        OutboxEvent.builder()
            .aggregateType("Order")
            .aggregateId(String.valueOf(savedOrder.getId()))
            .eventType("ORDER_CREATED")
            .payload(
                String.format(
                    "{\"orderId\":%d,\"customerName\":\"%s\",\"productName\":\"%s\",\"amount\":%.2f}",
                    savedOrder.getId(), customerName, productName, amount))
            .status(EventStatus.PENDING)
            .createdAt(now)
            .build();

    outboxRepository.save(outboxEvent);
    LOGGER.info("Saved OutboxEvent for Order ID [{}] in database", savedOrder.getId());

    return savedOrder;
  }
}
