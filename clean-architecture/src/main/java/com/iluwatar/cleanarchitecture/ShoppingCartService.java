/*
 * This project is licensed under the MIT license.
 * Module model-view-viewmodel is using
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
 * Service class for managing shopping cart operations.
 *
 * <p>This class provides functionalities to add and remove items from the cart,
 * calculate the total price, and handle checkout operations.</p>
 */
public class ShoppingCartService {
  /** Repository for managing product data. */
  private final ProductRepository productRepository;
  /** Repository for managing cart data. */
  private final CartRepository cartRepository;
  /** Repository for managing order data. */
  private final OrderRepository orderRepository;


  /**
   * Constructs a ShoppingCartService with the required repositories.
   *
   * @param pRepository The repository to fetch product details.
   * @param cRepository The repository to manage cart operations.
   * @param oRepository The repository to handle order persistence.
   */
  public ShoppingCartService(final ProductRepository pRepository,
                                 final CartRepository cRepository,
                                 final OrderRepository oRepository) {
    this.productRepository = pRepository;
    this.cartRepository = cRepository;
    this.orderRepository = oRepository;
  }

  /**
   * Adds an item to the user's shopping cart.
   *
   * @param userId The ID of the user.
   * @param productId The ID of the product to be added.
   * @param quantity The quantity of the product.
   */
  public void addItemToCart(
      final String userId, final String productId, final int quantity) {
    Product product = productRepository.getProductById(productId);
    if (product != null) {
      cartRepository.addItemToCart(userId, product, quantity);
    }
  }
  /**
   * Removes an item from the user's shopping cart.
   *
   * @param userId The ID of the user.
   * @param productId The ID of the product to be removed.
   */
  public void removeItemFromCart(final String userId, final String productId) {
    cartRepository.removeItemFromCart(userId, productId);
  }

  /**
   * Calculates the total cost of items in the user's shopping cart.
   *
   * @param userId The ID of the user.
   * @return The total price of all items in the cart.
   */
  public double calculateTotal(final String userId) {
    return cartRepository.calculateTotal(userId);
  }

  /**
   * Checks out the user's cart and creates an order.
   *
   * <p>This method retrieves the cart items, generates an order ID,
   * creates a new order, saves it, and clears the cart.</p>
   *
   * @param userId The ID of the user.
   * @return The created order containing purchased items.
   */
  public Order checkout(final String userId) {
    List<Cart> items = cartRepository.getItemsInCart(userId);
    String orderId = "ORDER-" + System.currentTimeMillis();
    Order order = new Order(orderId, items);
    orderRepository.saveOrder(order);
    cartRepository.clearCart(userId);
    return order;
  }
}
