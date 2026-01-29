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
package com.iluwatar.datatransfer.customer

// ABOUTME: Resource class that serves customer information, acting as the server in the demo.
// ABOUTME: Provides operations to list, save, and delete customers via CustomerDto objects.

/**
 * The resource class which serves customer information. This class acts as the server in
 * the demo, holding all customer details.
 */
class CustomerResource(val customers: MutableList<CustomerDto>) {
    /**
     * Save a new customer.
     *
     * @param customer the new customer to add to the list.
     */
    fun save(customer: CustomerDto) {
        customers.add(customer)
    }

    /**
     * Delete the customer with the given id.
     *
     * @param customerId the id of the customer to delete.
     */
    fun delete(customerId: String) {
        customers.removeIf { it.id == customerId }
    }
}
