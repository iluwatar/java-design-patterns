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

// ABOUTME: Repository interface for managing shopping cart operations.
// ABOUTME: Defines contract for cart data access including add, remove, and calculate operations.
package com.iluwatar.cleanarchitecture

/**
 * Repository interface for managing cart operations.
 */
interface CartRepository {
    /**
     * Adds an item to the user's cart.
     *
     * @param userId The ID of the user.
     * @param product The product to be added.
     * @param quantity The quantity of the product.
     */
    fun addItemToCart(
        userId: String,
        product: Product,
        quantity: Int,
    )

    /**
     * Removes an item from the user's cart.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product to be removed.
     */
    fun removeItemFromCart(
        userId: String,
        productId: String,
    )

    /**
     * Retrieves the list of items in the user's cart.
     *
     * @param userId The ID of the user.
     * @return A list of items in the cart.
     */
    fun getItemsInCart(userId: String): List<Cart>

    /**
     * Calculates the total price of the items in the user's cart.
     *
     * @param userId The ID of the user.
     * @return The total price of all items in the cart.
     */
    fun calculateTotal(userId: String): Double

    /**
     * Clears all items from the user's cart.
     *
     * @param userId The ID of the user.
     */
    fun clearCart(userId: String)
}