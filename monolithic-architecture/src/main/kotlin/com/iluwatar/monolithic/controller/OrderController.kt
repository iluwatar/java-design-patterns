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

// ABOUTME: Controller class for managing Order operations in the e-commerce application.
// ABOUTME: Handles order placement with user/product validation and stock management.
package com.iluwatar.monolithic.controller

import com.iluwatar.monolithic.exceptions.InsufficientStockException
import com.iluwatar.monolithic.exceptions.NonExistentProductException
import com.iluwatar.monolithic.exceptions.NonExistentUserException
import com.iluwatar.monolithic.model.Order
import com.iluwatar.monolithic.repository.OrderRepository
import com.iluwatar.monolithic.repository.ProductRepository
import com.iluwatar.monolithic.repository.UserRepository
import org.springframework.stereotype.Service

/** OrderController is a controller class for managing Order operations. */
@Service
open class OrderController(
    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository,
) {
    /** This function handles placing orders with all of its cases. */
    fun placeOrder(
        userId: Long,
        productId: Long,
        quantity: Int,
    ): Order {
        val user =
            userRepository
                .findById(userId)
                .orElseThrow { NonExistentUserException("User with ID $userId not found") }

        val product =
            productRepository
                .findById(productId)
                .orElseThrow { NonExistentProductException("Product with ID $productId not found") }

        if (product.stock < quantity) {
            throw InsufficientStockException("Not enough stock for product $productId")
        }

        product.stock = product.stock - quantity
        productRepository.save(product)

        val order =
            Order(
                id = null,
                user = user,
                product = product,
                quantity = quantity,
                totalPrice = product.price * quantity,
            )
        return orderRepository.save(order)
    }
}