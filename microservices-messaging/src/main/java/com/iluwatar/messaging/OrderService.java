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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OrderService is a message producer that publishes order-related messages using Kafka.
 */
public class OrderService {
  private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
  private static final String ORDER_TOPIC = "order-topic";

  private final KafkaMessageProducer producer;

  public OrderService(KafkaMessageProducer producer) {
    this.producer = producer;
  }

  /**
   * Creates an order and publishes a message to notify other services.
   *
   * @param orderId the ID of the order to create
   */
  public void createOrder(String orderId) {
    LOGGER.info("Creating order: {}", orderId);
    Message message = new Message("Order Created: " + orderId);
    producer.publish(ORDER_TOPIC, message);
    LOGGER.info("Order creation message published for: {}", orderId);
  }

  /**
   * Updates an order and publishes a message to notify other services.
   *
   * @param orderId the ID of the order to update
   */
  public void updateOrder(String orderId) {
    LOGGER.info("Updating order: {}", orderId);
    Message message = new Message("Order Updated: " + orderId);
    producer.publish(ORDER_TOPIC, message);
    LOGGER.info("Order update message published for: {}", orderId);
  }

  /**
   * Cancels an order and publishes a message to notify other services.
   *
   * @param orderId the ID of the order to cancel
   */
  public void cancelOrder(String orderId) {
    LOGGER.info("Cancelling order: {}", orderId);
    Message message = new Message("Order Cancelled: " + orderId);
    producer.publish(ORDER_TOPIC, message);
    LOGGER.info("Order cancellation message published for: {}", orderId);
  }
}