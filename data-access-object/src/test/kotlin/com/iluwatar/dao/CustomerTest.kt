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

// ABOUTME: Unit tests for the Customer class.
// ABOUTME: Tests getters, setters, equals, hashCode, and toString methods.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Tests [Customer].
 */
class CustomerTest {

    private lateinit var customer: Customer

    companion object {
        private const val ID = 1
        private const val FIRSTNAME = "Winston"
        private const val LASTNAME = "Churchill"
    }

    @BeforeEach
    fun setUp() {
        customer = Customer(ID, FIRSTNAME, LASTNAME)
    }

    @Test
    fun getAndSetId() {
        val newId = 2
        customer.id = newId
        assertEquals(newId, customer.id)
    }

    @Test
    fun getAndSetFirstname() {
        val newFirstname = "Bill"
        customer.firstName = newFirstname
        assertEquals(newFirstname, customer.firstName)
    }

    @Test
    fun getAndSetLastname() {
        val newLastname = "Clinton"
        customer.lastName = newLastname
        assertEquals(newLastname, customer.lastName)
    }

    @Test
    fun notEqualWithDifferentId() {
        val newId = 2
        val otherCustomer = Customer(newId, FIRSTNAME, LASTNAME)
        assertNotEquals(customer, otherCustomer)
        assertNotEquals(customer.hashCode(), otherCustomer.hashCode())
    }

    @Test
    fun equalsWithSameObjectValues() {
        val otherCustomer = Customer(ID, FIRSTNAME, LASTNAME)
        assertEquals(customer, otherCustomer)
        assertEquals(customer.hashCode(), otherCustomer.hashCode())
    }

    @Test
    fun equalsWithSameObjects() {
        assertEquals(customer, customer)
        assertEquals(customer.hashCode(), customer.hashCode())
    }

    @Test
    fun testToString() {
        assertEquals(
            "Customer(id=${customer.id}, firstName=${customer.firstName}, lastName=${customer.lastName})",
            customer.toString()
        )
    }
}
