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

// ABOUTME: Service class for managing shopping cart operations (use case layer).
// ABOUTME: Provides add, remove, calculate total, and checkout functionalities.
package com.iluwatar.cleanarchitecture

/**
 * Service class for managing shopping cart operations.
 *
 * This class provides functionalities to add and remove items from the cart, calculate the total
 * price, and handle checkout operations.
 *
 * @property productRepository Repository for managing product data.
 * @property cartRepository Repository for managing cart data.
 * @property orderRepository Repository for managing order data.
 */
class ShoppingCartService(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
) {
    /**
     * Adds an item to the user's shopping cart.
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
        productRepository.getProductById(productId)?.let { product ->
            cartRepository.addItemToCart(userId, product, quantity)
        }
    }

    /**
     * Removes an item from the user's shopping cart.
     *
     * @param userId The ID of the user.
     * @param productId The ID of the product to be removed.
     */
    fun removeItemFromCart(
        userId: String,
        productId: String,
    ) {
        cartRepository.removeItemFromCart(userId, productId)
    }

    /**
     * Calculates the total cost of items in the user's shopping cart.
     *
     * @param userId The ID of the user.
     * @return The total price of all items in the cart.
     */
    fun calculateTotal(userId: String): Double = cartRepository.calculateTotal(userId)

    /**
     * Checks out the user's cart and creates an order.
     *
     * This method retrieves the cart items, generates an order ID, creates a new order, saves it,
     * and clears the cart.
     *
     * @param userId The ID of the user.
     * @return The created order containing purchased items.
     */
    fun checkout(userId: String): Order {
        val items = cartRepository.getItemsInCart(userId)
        val orderId = "ORDER-${System.currentTimeMillis()}"
        val order = Order(orderId, items)
        orderRepository.saveOrder(order)
        cartRepository.clearCart(userId)
        return order
    }
}