/*
 * This project is licensed under the MIT license.
 * Module model-view-viewmodel is using ZK framework
 * licensed under LGPL (see lgpl-3.0.txt).
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

/**
 * Controller for handling order-related operations.
 *
 * <p>This class provides an endpoint for users to checkout their cart
 * and place an order.</p>
 */
public class OrderController {
  /**
   * Service for managing shopping cart operations.
   */
  private final ShoppingCartService shoppingCartUseCase;


  /**
   * Constructs an {@code OrderController} with the given shopping cart service.
   *
   * @param shoppingCartUse The shopping cart service used to process orders.
   */
  public OrderController(final ShoppingCartService shoppingCartUse) {
    this.shoppingCartUseCase = shoppingCartUse;
  }

  /**
   * Processes the checkout for a given user and creates an order.
   *
   * @param userId The ID of the user checking out.
   * @return The created {@link Order} after checkout.
   */
  public Order checkout(final String userId) {
    return shoppingCartUseCase.checkout(userId);
  }
}
