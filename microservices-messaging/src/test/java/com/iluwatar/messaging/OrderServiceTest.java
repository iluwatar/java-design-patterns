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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit tests for {@link OrderService}.
 * Tests follow Arrange-Act-Assert pattern.
 */
class OrderServiceTest {

  private KafkaMessageProducer mockProducer;
  private OrderService orderService;

  @BeforeEach
  void setUp() {
    // Arrange - Create mock producer to avoid Kafka dependency
    mockProducer = mock(KafkaMessageProducer.class);
    orderService = new OrderService(mockProducer);
  }

  @Test
  void testCreateOrder() {
    // Arrange
    var orderId = "ORDER-001";

    // Act
    assertDoesNotThrow(() -> orderService.createOrder(orderId));

    // Assert
    verify(mockProducer, times(1)).publish(eq("order-topic"), any(Message.class));
  }

  @Test
  void testUpdateOrder() {
    // Arrange
    var orderId = "ORDER-002";

    // Act
    assertDoesNotThrow(() -> orderService.updateOrder(orderId));

    // Assert
    verify(mockProducer, times(1)).publish(eq("order-topic"), any(Message.class));
  }

  @Test
  void testCancelOrder() {
    // Arrange
    var orderId = "ORDER-003";

    // Act
    assertDoesNotThrow(() -> orderService.cancelOrder(orderId));

    // Assert
    verify(mockProducer, times(1)).publish(eq("order-topic"), any(Message.class));
  }

  @Test
  void testMultipleOrderOperations() {
    // Arrange
    var orderId = "ORDER-004";

    // Act
    orderService.createOrder(orderId);
    orderService.updateOrder(orderId);
    orderService.cancelOrder(orderId);

    // Assert
    verify(mockProducer, times(3)).publish(eq("order-topic"), any(Message.class));
  }

  @Test
  void testCreateOrderWithDifferentIds() {
    // Act
    orderService.createOrder("ORDER-001");
    orderService.createOrder("ORDER-002");
    orderService.createOrder("ORDER-003");

    // Assert
    verify(mockProducer, times(3)).publish(eq("order-topic"), any(Message.class));
  }
}