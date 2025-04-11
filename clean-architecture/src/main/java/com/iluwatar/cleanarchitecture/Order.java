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
package com.iluwatar.cleanarchitecture;

import java.util.List;
import lombok.Getter;

/**
 * Represents an order placed by a user containing the ordered items and total price.
 *
 * <p>An order includes a unique order ID, a list of cart items and the total price of the order.
 */
@Getter
public class Order {
  /** The unique identifier for this order. */
  private final String orderId;

  /** The list of items included in this order. */
  private final List<Cart> items;

  /** The list of items included in this order. */
  private final double totalPrice;

  /**
   * Constructs an {@code Order} with the given order ID and list of cart items. The total price is
   * based on the individual item prices in the cart.
   *
   * @param id The unique identifier for the order.
   * @param item The list of cart items included in the order.
   */
  public Order(final String id, final List<Cart> item) {
    this.orderId = id;
    this.items = item;
    this.totalPrice = items.stream().mapToDouble(Cart::getTotalPrice).sum();
  }
}
