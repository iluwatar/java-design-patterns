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

// ABOUTME: Unit tests for the InMemoryCustomerDao implementation.
// ABOUTME: Tests CRUD operations for both existing and non-existing customers.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

/**
 * Tests [InMemoryCustomerDao].
 */
class InMemoryCustomerDaoTest {

    private lateinit var dao: InMemoryCustomerDao

    companion object {
        private val CUSTOMER = Customer(1, "Freddy", "Krueger")
    }

    @BeforeEach
    fun setUp() {
        dao = InMemoryCustomerDao()
        assertTrue(dao.add(CUSTOMER))
    }

    /**
     * Represents the scenario when the DAO operations are being performed on a non-existent customer.
     */
    @Nested
    inner class NonExistingCustomer {

        @Test
        fun addingShouldResultInSuccess() {
            dao.getAll().use { allCustomers ->
                assumeTrue(allCustomers.count() == 1L)
            }

            val nonExistingCustomer = Customer(2, "Robert", "Englund")
            val result = dao.add(nonExistingCustomer)
            assertTrue(result)

            assertCustomerCountIs(2)
            assertEquals(nonExistingCustomer, dao.getById(nonExistingCustomer.id))
        }

        @Test
        fun deletionShouldBeFailureAndNotAffectExistingCustomers() {
            val nonExistingCustomer = Customer(2, "Robert", "Englund")
            val result = dao.delete(nonExistingCustomer)

            assertFalse(result)
            assertCustomerCountIs(1)
        }

        @Test
        fun updateShouldBeFailureAndNotAffectExistingCustomers() {
            val nonExistingId = getNonExistingCustomerId()
            val newFirstname = "Douglas"
            val newLastname = "MacArthur"
            val customer = Customer(nonExistingId, newFirstname, newLastname)
            val result = dao.update(customer)

            assertFalse(result)
            assertNull(dao.getById(nonExistingId))
        }

        @Test
        fun retrieveShouldReturnNoCustomer() {
            assertNull(dao.getById(getNonExistingCustomerId()))
        }
    }

    /**
     * Represents the scenario when the DAO operations are being performed on an already existing
     * customer.
     */
    @Nested
    inner class ExistingCustomer {

        @Test
        fun addingShouldResultInFailureAndNotAffectExistingCustomers() {
            val result = dao.add(CUSTOMER)

            assertFalse(result)
            assertCustomerCountIs(1)
            assertEquals(CUSTOMER, dao.getById(CUSTOMER.id))
        }

        @Test
        fun deletionShouldBeSuccessAndCustomerShouldBeNonAccessible() {
            val result = dao.delete(CUSTOMER)

            assertTrue(result)
            assertCustomerCountIs(0)
            assertNull(dao.getById(CUSTOMER.id))
        }

        @Test
        fun updationShouldBeSuccessAndAccessingTheSameCustomerShouldReturnUpdatedInformation() {
            val newFirstname = "Bernard"
            val newLastname = "Montgomery"
            val customer = Customer(CUSTOMER.id, newFirstname, newLastname)
            val result = dao.update(customer)

            assertTrue(result)

            val cust = dao.getById(CUSTOMER.id)
            assertNotNull(cust)
            assertEquals(newFirstname, cust!!.firstName)
            assertEquals(newLastname, cust.lastName)
        }

        @Test
        fun retriveShouldReturnTheCustomer() {
            val customer = dao.getById(CUSTOMER.id)

            assertNotNull(customer)
            assertEquals(CUSTOMER, customer)
        }
    }

    /**
     * An arbitrary number which does not correspond to an active Customer id.
     *
     * @return an int of a customer id which doesn't exist
     */
    private fun getNonExistingCustomerId(): Int = 999

    private fun assertCustomerCountIs(count: Int) {
        dao.getAll().use { allCustomers ->
            assertEquals(count.toLong(), allCustomers.count())
        }
    }
}
