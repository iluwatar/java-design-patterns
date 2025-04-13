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

/**
 * Controller class for handling shopping cart operations.
 *
 * <p>This class provides methods to add, remove, and calculate the total price of items in a user's
 * shopping cart.
 */
public class CartController {

  /** Service layer responsible for cart operations. */
  private final ShoppingCartService shoppingCartUseCase;

  /**
   * Constructs a CartController with the specified shopping cart service.
   *
   * @param shoppingCart The shopping cart service to handle cart operations.
   */
  public CartController(final ShoppingCartService shoppingCart) {
    this.shoppingCartUseCase = shoppingCart;
  }

  /**
   * Adds an item to the user's cart.
   *
   * @param userId The ID of the user.
   * @param productId The ID of the product to be added.
   * @param quantity The quantity of the product.
   */
  public void addItemToCart(final String userId, final String productId, final int quantity) {
    shoppingCartUseCase.addItemToCart(userId, productId, quantity);
  }

  /**
   * Removes an item from the user's cart.
   *
   * @param userId The ID of the user.
   * @param productId The ID of the product to be removed.
   */
  public void removeItemFromCart(final String userId, final String productId) {
    shoppingCartUseCase.removeItemFromCart(userId, productId);
  }

  /**
   * Calculates the total cost of items in the user's cart.
   *
   * @param userId The ID of the user.
   * @return The total price of all items in the cart.
   */
  public double calculateTotal(final String userId) {
    return shoppingCartUseCase.calculateTotal(userId);
  }
}
