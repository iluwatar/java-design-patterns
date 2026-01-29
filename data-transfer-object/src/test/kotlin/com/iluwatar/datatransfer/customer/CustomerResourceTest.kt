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

// ABOUTME: Tests for CustomerResource covering list, save, and delete operations.
// ABOUTME: Verifies that the resource correctly manages customer DTOs.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** Tests [CustomerResource]. */
class CustomerResourceTest {
    @Test
    fun shouldGetAllCustomers() {
        val customers = mutableListOf(CustomerDto("1", "Melody", "Yates"))
        val customerResource = CustomerResource(customers)
        val allCustomers = customerResource.customers

        assertEquals(1, allCustomers.size)
        assertEquals("1", allCustomers[0].id)
        assertEquals("Melody", allCustomers[0].firstName)
        assertEquals("Yates", allCustomers[0].lastName)
    }

    @Test
    fun shouldSaveCustomer() {
        val customer = CustomerDto("1", "Rita", "Reynolds")
        val customerResource = CustomerResource(mutableListOf())

        customerResource.save(customer)

        val allCustomers = customerResource.customers
        assertEquals("1", allCustomers[0].id)
        assertEquals("Rita", allCustomers[0].firstName)
        assertEquals("Reynolds", allCustomers[0].lastName)
    }

    @Test
    fun shouldDeleteCustomer() {
        val customer = CustomerDto("1", "Terry", "Nguyen")
        val customers = mutableListOf(customer)
        val customerResource = CustomerResource(customers)

        customerResource.delete(customer.id)

        val allCustomers = customerResource.customers
        assertTrue(allCustomers.isEmpty())
    }
}
