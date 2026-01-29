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

// ABOUTME: In-memory implementation of OrderRepository for storing orders.
// ABOUTME: Stores orders in a list, not persisted beyond the application's runtime.
package com.iluwatar.cleanarchitecture

/**
 * An in-memory implementation of the [OrderRepository].
 *
 * This class stores orders in a list, allowing orders to be saved but not persisted beyond the
 * application's runtime.
 */
class InMemoryOrderRepository : OrderRepository {
    /** A list to store orders in memory. */
    private val orders: MutableList<Order> = mutableListOf()

    /**
     * Saves an order to the in-memory repository.
     *
     * @param order The order to be saved.
     */
    override fun saveOrder(order: Order) {
        orders.add(order)
    }
}