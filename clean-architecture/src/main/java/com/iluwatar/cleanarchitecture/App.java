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

import lombok.extern.slf4j.Slf4j;

/**
 * Clean Architecture ensures separation of concerns by organizing code, into layers and making it
 * scalable and maintainable.
 *
 * <p>In the example there are Entities (Core Models) – Product, Cart, Order handle business logic.
 * Use Cases (Application Logic) – ShoppingCartService manages operations like adding items and
 * checkout. Interfaces & Adapters – Repositories (CartRepository, OrderRepository) abstract data
 * handling, while controllers (CartController, OrderController) manage interactions.
 */
@Slf4j
public final class App {

  private App() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(final String[] args) {
    ProductRepository productRepository = new InMemoryProductRepository();
    CartRepository cartRepository = new InMemoryCartRepository();
    OrderRepository orderRepository = new InMemoryOrderRepository();

    ShoppingCartService shoppingCartUseCase =
        new ShoppingCartService(productRepository, cartRepository, orderRepository);

    CartController cartController = new CartController(shoppingCartUseCase);
    OrderController orderController = new OrderController(shoppingCartUseCase);

    String userId = "user123";
    cartController.addItemToCart(userId, "1", 1);
    cartController.addItemToCart(userId, "2", 2);

    Order order = orderController.checkout(userId);
    LOGGER.info("Total: ${}", cartController.calculateTotal(userId));

    LOGGER.info(
        "Order placed! Order ID: {}, Total: ${}", order.getOrderId(), order.getTotalPrice());
  }
}
