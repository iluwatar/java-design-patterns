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

// ABOUTME: Service class responsible for finalizing a customer's order and calculating totals.
// ABOUTME: Follows the information expert principle from GRASP for total calculation responsibility.

package com.iluwatar.sessionfacade

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The OrderService class is responsible for finalizing a customer's order. It includes a method to
 * calculate the total cost of the order, which follows the information expert principle from GRASP
 * by assigning the responsibility of total calculation to this service. Additionally, it provides a
 * method to complete the order, which empties the client's shopping cart once the order is
 * finalized.
 */
class OrderService(private val cart: MutableMap<Int, Product>) {

    /**
     * Order.
     */
    fun order() {
        val total = getTotal()
        if (cart.isNotEmpty()) {
            logger.info { "Client has chosen to order $cart with total ${"%.2f".format(total)}" }
            completeOrder()
        } else {
            logger.info { "Client's shopping cart is empty" }
        }
    }

    /**
     * Gets total.
     *
     * @return the total
     */
    fun getTotal(): Double = cart.values.sumOf { it.price }

    /**
     * Complete order.
     */
    fun completeOrder() {
        cart.clear()
    }
}
