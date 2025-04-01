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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link CartRepository} that stores cart items in memory.
 *
 * <p>This class maintains a map of user carts where each user has a
 * list of cart items.</p>
 */
public class InMemoryCartRepository implements CartRepository {
  /**
   * A map storing user carts with their respective cart items.
   */
  private final Map<String, List<Cart>> userCarts = new HashMap<>();

  /**
   * Adds an item to the user's cart.
   *
   * @param userId  The ID of the user.
   * @param product The product to be added.
   * @param quantity The quantity of the product.
   */
  @Override
  public void addItemToCart(
      final String userId, final Product product, final int quantity) {
    List<Cart> cart = userCarts.getOrDefault(userId, new ArrayList<>());
    cart.add(new Cart(product, quantity));
    userCarts.put(userId, cart);
  }

  /**
   * Removes an item from the user's cart.
   *
   * @param userId The ID of the user.
   * @param productId The ID of the product to be removed.
   */
  @Override
  public void removeItemFromCart(final String userId, final String productId) {
    List<Cart> cart = userCarts.get(userId);
    if (cart != null) {
      cart.removeIf(item -> item.getProduct().getId().equals(productId));
    }
  }

  /**
   * Retrieves all items in the user's cart.
   *
   * @param userId The ID of the user.
   * @return A list of {@link Cart} items in the user's cart.
   */
  @Override
  public List<Cart> getItemsInCart(final String userId) {
    return userCarts.getOrDefault(userId, new ArrayList<>());
  }

  /**
   * Calculates the total price of items in the user's cart.
   *
   * @param userId The ID of the user.
   * @return The total price of the cart.
   */
  @Override
  public double calculateTotal(final String userId) {
    return userCarts.getOrDefault(userId, new ArrayList<>())
        .stream()
        .mapToDouble(Cart::getTotalPrice)
        .sum();
  }

  /**
   * Clears all items from the user's cart.
   *
   * @param userId The ID of the user.
   */
  @Override
  public void clearCart(final String userId) {
    userCarts.remove(userId);
  }
}
