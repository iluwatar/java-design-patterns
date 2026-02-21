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

package com.iluwatar.orderservice.controller;

import com.iluwatar.orderservice.model.Order;
import com.iluwatar.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

/**
 * REST Controller for Order operations.
 * Provides endpoints for managing orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  /**
   * Get all orders.
   *
   * @return list of all orders
   */
  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders() {
    List<Order> orders = orderService.getAllOrders();
    return ResponseEntity.ok(orders);
  }

  /**
   * Get order by ID.
   *
   * @param id the order ID
   * @return the order if found, 404 otherwise
   */
  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
    Optional<Order> order = orderService.getOrderById(id);
    return order.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Create a new order.
   *
   * @param order the order to create
   * @return the created order
   */
  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    Order createdOrder = orderService.createOrder(order);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
  }

  /**
   * Update order status.
   *
   * @param id      the order ID
   * @param request the request containing the new status
   * @return the updated order if found, 404 otherwise
   */
  @PutMapping("/{id}/status")
  public ResponseEntity<Order> updateOrderStatus(@PathVariable("id") Long id, @RequestBody Map<String, String> request) {
    String status = request.get("status");
    Optional<Order> updatedOrder = orderService.updateOrderStatus(id, status);
    return updatedOrder.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Cancel an order.
   *
   * @param id the order ID
   * @return 204 if cancelled, 404 if not found
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> cancelOrder(@PathVariable("id") Long id) {
    boolean cancelled = orderService.cancelOrder(id);
    return cancelled ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  /**
   * Health check endpoint.
   *
   * @return service status
   */
  @GetMapping("/health")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("Order Service is running!");
  }
}
