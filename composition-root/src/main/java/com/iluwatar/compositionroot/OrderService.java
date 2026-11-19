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
package com.iluwatar.compositionroot;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Places orders. Every collaborator arrives through the constructor; the service does not create,
 * look up or configure any of them.
 */
public class OrderService {

  private final PricingService pricing;
  private final OrderRepository repository;
  private final Notifier notifier;
  private final AtomicInteger nextId = new AtomicInteger(1);

  /**
   * Creates the service.
   *
   * @param pricing prices each order
   * @param repository stores placed orders
   * @param notifier tells the customer the order was placed
   */
  public OrderService(PricingService pricing, OrderRepository repository, Notifier notifier) {
    this.pricing = pricing;
    this.repository = repository;
    this.notifier = notifier;
  }

  /**
   * Prices, stores and confirms an order.
   *
   * @param customer who places the order
   * @param sku the product
   * @param quantity how many units
   * @return the placed order
   */
  public Order placeOrder(String customer, String sku, int quantity) {
    var total = pricing.priceInCents(sku, quantity);
    var order = new Order(nextId.getAndIncrement(), customer, sku, quantity, total);
    repository.save(order);
    notifier.send(customer, "Order #" + order.id() + " confirmed, total " + total + " cents");
    return order;
  }
}
