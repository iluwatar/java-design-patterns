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

package com.iluwatar.orderservice.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Order entity representing an order in the system.
 */
public class Order {
  private Long id;
  private String customerName;
  private String customerEmail;
  private List<OrderItem> items;
  private Double totalAmount;
  private String status;
  private LocalDateTime createdAt;

  /**
   * Default constructor.
   */
  public Order() {
    this.createdAt = LocalDateTime.now();
    this.status = "PENDING";
  }

  /**
   * Constructor with essential fields.
   *
   * @param customerName  the customer name
   * @param customerEmail the customer email
   * @param items         the order items
   */
  public Order(String customerName, String customerEmail, List<OrderItem> items) {
    this();
    this.customerName = customerName;
    this.customerEmail = customerEmail;
    this.items = items;
    calculateTotalAmount();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public List<OrderItem> getItems() {
    return items;
  }

  public void setItems(List<OrderItem> items) {
    this.items = items;
    calculateTotalAmount();
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Calculate the total amount based on order items.
   */
  private void calculateTotalAmount() {
    if (items != null) {
      this.totalAmount = items.stream()
          .mapToDouble(item -> item.getPrice() * item.getQuantity())
          .sum();
    }
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", customerName='" + customerName + '\'' +
        ", customerEmail='" + customerEmail + '\'' +
        ", items=" + items +
        ", totalAmount=" + totalAmount +
        ", status='" + status + '\'' +
        ", createdAt=" + createdAt +
        '}';
  }
}
