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

// ABOUTME: Controller class for handling shopping cart operations (interface adapter layer).
// ABOUTME: Provides methods to add, remove, and calculate total price of items in a cart.
package com.iluwatar.cleanarchitecture

/**
 * Controller class for handling shopping cart operations.
 *
 * This class provides methods to add, remove, and calculate the total price of items in a user's
 * shopping cart.
 *
 * @property shoppingCartUseCase Service layer responsible for cart operations.
 */
class CartController(
    private val shoppingCartUseCase: ShoppingCartService,
) {
    /**
     * Adds an item to the user's cart.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product to be added.
     * @param quantity The quantity of the product.
     */
    fun addItemToCart(
        userId: String,
        productId: String,
        quantity: Int,
    ) {
        shoppingCartUseCase.addItemToCart(userId, productId, quantity)
    }

    /**
     * Removes an item from the user's cart.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product to be removed.
     */
    fun removeItemFromCart(
        userId: String,
        productId: String,
    ) {
        shoppingCartUseCase.removeItemFromCart(userId, productId)
    }

    /**
     * Calculates the total cost of items in the user's cart.
     *
     * @param userId The ID of the user.
     * @return The total price of all items in the cart.
     */
    fun calculateTotal(userId: String): Double = shoppingCartUseCase.calculateTotal(userId)
}