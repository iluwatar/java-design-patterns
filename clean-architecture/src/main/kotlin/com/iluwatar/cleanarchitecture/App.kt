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

// ABOUTME: Entry point demonstrating the Clean Architecture pattern.
// ABOUTME: Shows separation of concerns with entities, use cases, and interface adapters.
package com.iluwatar.cleanarchitecture

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Clean Architecture ensures separation of concerns by organizing code into layers and making it
 * scalable and maintainable.
 *
 * In the example there are Entities (Core Models) - Product, Cart, Order handle business logic.
 * Use Cases (Application Logic) - ShoppingCartService manages operations like adding items and
 * checkout. Interfaces & Adapters - Repositories (CartRepository, OrderRepository) abstract data
 * handling, while controllers (CartController, OrderController) manage interactions.
 */
fun main() {
    val productRepository: ProductRepository = InMemoryProductRepository()
    val cartRepository: CartRepository = InMemoryCartRepository()
    val orderRepository: OrderRepository = InMemoryOrderRepository()

    val shoppingCartUseCase = ShoppingCartService(productRepository, cartRepository, orderRepository)

    val cartController = CartController(shoppingCartUseCase)
    val orderController = OrderController(shoppingCartUseCase)

    val userId = "user123"
    cartController.addItemToCart(userId, "1", 1)
    cartController.addItemToCart(userId, "2", 2)

    val order = orderController.checkout(userId)
    logger.info { "Total: \$${cartController.calculateTotal(userId)}" }

    logger.info { "Order placed! Order ID: ${order.orderId}, Total: \$${order.totalPrice}" }
}