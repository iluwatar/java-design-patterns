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

package com.iluwatar.orderservice.service;

import com.iluwatar.orderservice.model.Order;
import com.iluwatar.orderservice.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing orders.
 * Provides business logic for order operations.
 */
@Service
public class OrderService {

  private final List<Order> orders = new ArrayList<>();
  private Long nextId = 1L;

  /**
   * Constructor that initializes some sample orders.
   */
  public OrderService() {
    List<OrderItem> items1 = List.of(
        new OrderItem(1L, "Laptop", 1, 999.99),
        new OrderItem(2L, "Book", 2, 49.99)
    );
    Order order1 = new Order("John Doe", "john@example.com", items1);
    order1.setId(nextId++);
    orders.add(order1);

    List<OrderItem> items2 = List.of(
        new OrderItem(3L, "Coffee Mug", 3, 19.99)
    );
    Order order2 = new Order("Jane Smith", "jane@example.com", items2);
    order2.setId(nextId++);
    orders.add(order2);
  }

  /**
   * Get all orders.
   *
   * @return list of all orders
   */
  public List<Order> getAllOrders() {
    return new ArrayList<>(orders);
  }

  /**
   * Get order by ID.
   *
   * @param id the order ID
   * @return the order if found, empty otherwise
   */
  public Optional<Order> getOrderById(Long id) {
    return orders.stream()
        .filter(order -> order.getId().equals(id))
        .findFirst();
  }

  /**
   * Create a new order.
   *
   * @param order the order to create
   * @return the created order
   */
  public Order createOrder(Order order) {
    order.setId(nextId++);
    order.setStatus("CONFIRMED");
    orders.add(order);
    return order;
  }

  /**
   * Update order status.
   *
   * @param id     the order ID
   * @param status the new status
   * @return the updated order if found, empty otherwise
   */
  public Optional<Order> updateOrderStatus(Long id, String status) {
    Optional<Order> orderOpt = getOrderById(id);
    if (orderOpt.isPresent()) {
      Order order = orderOpt.get();
      order.setStatus(status);
      return Optional.of(order);
    }
    return Optional.empty();
  }

  /**
   * Cancel an order.
   *
   * @param id the order ID
   * @return true if order was cancelled, false otherwise
   */
  public boolean cancelOrder(Long id) {
    Optional<Order> orderOpt = getOrderById(id);
    if (orderOpt.isPresent()) {
      orderOpt.get().setStatus("CANCELLED");
      return true;
    }
    return false;
  }
}
