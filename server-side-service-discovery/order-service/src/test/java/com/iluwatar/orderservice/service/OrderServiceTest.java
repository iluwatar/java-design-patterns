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
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR the USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.orderservice.service;

import com.iluwatar.orderservice.model.Order;
import com.iluwatar.orderservice.model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for OrderService.
 */
class OrderServiceTest {

  private OrderService orderService;

  @BeforeEach
  void setUp() {
    orderService = new OrderService();
  }

  @Test
  void testGetAllOrders() {
    List<Order> orders = orderService.getAllOrders();
    assertNotNull(orders);
    assertEquals(2, orders.size());
  }

  @Test
  void testGetOrderById() {
    Optional<Order> order = orderService.getOrderById(1L);
    assertTrue(order.isPresent());
    assertEquals("John Doe", order.get().getCustomerName());
  }

  @Test
  void testGetOrderByIdNotFound() {
    Optional<Order> order = orderService.getOrderById(999L);
    assertFalse(order.isPresent());
  }

  @Test
  void testCreateOrder() {
    List<OrderItem> items = List.of(
        new OrderItem(1L, "Test Product", 2, 50.0)
    );
    Order newOrder = new Order("Test Customer", "test@example.com", items);
    Order createdOrder = orderService.createOrder(newOrder);
    
    assertNotNull(createdOrder.getId());
    assertEquals("CONFIRMED", createdOrder.getStatus());
    assertEquals(3, orderService.getAllOrders().size());
  }

  @Test
  void testUpdateOrderStatus() {
    Optional<Order> updatedOrder = orderService.updateOrderStatus(1L, "SHIPPED");
    assertTrue(updatedOrder.isPresent());
    assertEquals("SHIPPED", updatedOrder.get().getStatus());
  }

  @Test
  void testUpdateOrderStatusNotFound() {
    Optional<Order> updatedOrder = orderService.updateOrderStatus(999L, "SHIPPED");
    assertFalse(updatedOrder.isPresent());
  }

  @Test
  void testCancelOrder() {
    boolean cancelled = orderService.cancelOrder(1L);
    assertTrue(cancelled);
    
    Optional<Order> order = orderService.getOrderById(1L);
    assertTrue(order.isPresent());
    assertEquals("CANCELLED", order.get().getStatus());
  }

  @Test
  void testCancelOrderNotFound() {
    boolean cancelled = orderService.cancelOrder(999L);
    assertFalse(cancelled);
  }
}
