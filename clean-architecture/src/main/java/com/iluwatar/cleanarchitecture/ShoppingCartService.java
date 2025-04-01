/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using
 * ZK framework licensed under LGPL (see lgpl-3.0.txt).
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

/**
 * ShoppingCartService
 */
public class ShoppingCartService {
  private final ProductRepository productRepository;
  private final CartRepository cartRepository;
  private final OrderRepository orderRepository;


  /**
   *
   * @param productRepository
   * @param cartRepository
   * @param orderRepository
   */
  public ShoppingCartService(ProductRepository productRepository,
                                 CartRepository cartRepository,
                                 OrderRepository orderRepository) {
    this.productRepository = productRepository;
    this.cartRepository = cartRepository;
    this.orderRepository = orderRepository;
  }

  /**
   *
   * @param userId
   * @param productId
   * @param quantity
   */
  public void addItemToCart(String userId, String productId, int quantity) {
    Product product = productRepository.getProductById(productId);
    if (product != null) {
      cartRepository.addItemToCart(userId, product, quantity);
    }
  }
  public void removeItemFromCart(String userId, String productId) {
    cartRepository.removeItemFromCart(userId, productId);
  }

  public double calculateTotal(String userId) {
    return cartRepository.calculateTotal(userId);
  }

  public Order checkout(String userId) {
    List<Cart> items = cartRepository.getItemsInCart(userId);
    String orderId = "ORDER-" + System.currentTimeMillis(); // simple order ID generation
    Order order = new Order(orderId, items);
    orderRepository.saveOrder(order);
    cartRepository.clearCart(userId);
    return order;
  }
}
