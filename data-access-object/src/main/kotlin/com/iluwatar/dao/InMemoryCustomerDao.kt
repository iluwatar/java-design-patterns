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
package com.iluwatar.dao

// ABOUTME: In-memory implementation of CustomerDao storing customers in a HashMap.
// ABOUTME: Useful as temporary database or for testing purposes.

import java.util.stream.Stream

/**
 * An in memory implementation of [CustomerDao], which stores the customers in JVM memory and
 * data is lost when the application exits.
 *
 * This implementation is useful as temporary database or for testing.
 */
class InMemoryCustomerDao : CustomerDao {

    private val idToCustomer = mutableMapOf<Int, Customer>()

    /**
     * An eagerly evaluated stream of customers stored in memory.
     */
    override fun getAll(): Stream<Customer> = idToCustomer.values.stream()

    override fun getById(id: Int): Customer? = idToCustomer[id]

    override fun add(customer: Customer): Boolean {
        if (getById(customer.id) != null) {
            return false
        }
        idToCustomer[customer.id] = customer
        return true
    }

    override fun update(customer: Customer): Boolean {
        return idToCustomer.replace(customer.id, customer) != null
    }

    override fun delete(customer: Customer): Boolean {
        return idToCustomer.remove(customer.id) != null
    }
}
