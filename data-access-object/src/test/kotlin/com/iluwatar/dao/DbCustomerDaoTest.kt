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

// ABOUTME: Unit tests for the DbCustomerDao implementation.
// ABOUTME: Tests CRUD operations with both successful DB connections and connectivity issues.

import io.mockk.every
import io.mockk.mockk
import org.h2.jdbcx.JdbcDataSource
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import javax.sql.DataSource

/**
 * Tests [DbCustomerDao].
 */
class DbCustomerDaoTest {

    private lateinit var dao: DbCustomerDao
    private val existingCustomer = Customer(1, "Freddy", "Krueger")

    companion object {
        private const val DB_URL = "jdbc:h2:mem:dao;DB_CLOSE_DELAY=-1"
    }

    /**
     * Creates customers schema.
     */
    @BeforeEach
    fun createSchema() {
        DriverManager.getConnection(DB_URL).use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(CustomerSchemaSql.CREATE_SCHEMA_SQL)
            }
        }
    }

    /**
     * Represents the scenario where DB connectivity is present.
     */
    @Nested
    inner class ConnectionSuccess {

        /**
         * Setup for connection success scenario.
         */
        @BeforeEach
        fun setUp() {
            val dataSource = JdbcDataSource()
            dataSource.setURL(DB_URL)
            dao = DbCustomerDao(dataSource)
            val result = dao.add(existingCustomer)
            assertTrue(result)
        }

        /**
         * Represents the scenario when DAO operations are being performed on a non-existing customer.
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
            fun updationShouldBeFailureAndNotAffectExistingCustomers() {
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
         * Represents a scenario where DAO operations are being performed on an already existing
         * customer.
         */
        @Nested
        inner class ExistingCustomer {

            @Test
            fun addingShouldResultInFailureAndNotAffectExistingCustomers() {
                val existingCustomer = Customer(1, "Freddy", "Krueger")
                val result = dao.add(existingCustomer)

                assertFalse(result)
                assertCustomerCountIs(1)
                assertEquals(existingCustomer, dao.getById(existingCustomer.id))
            }

            @Test
            fun deletionShouldBeSuccessAndCustomerShouldBeNonAccessible() {
                val result = dao.delete(existingCustomer)

                assertTrue(result)
                assertCustomerCountIs(0)
                assertNull(dao.getById(existingCustomer.id))
            }

            @Test
            fun updationShouldBeSuccessAndAccessingTheSameCustomerShouldReturnUpdatedInformation() {
                val newFirstname = "Bernard"
                val newLastname = "Montgomery"
                val customer = Customer(existingCustomer.id, newFirstname, newLastname)
                val result = dao.update(customer)

                assertTrue(result)

                val cust = dao.getById(existingCustomer.id)
                assertNotNull(cust)
                assertEquals(newFirstname, cust!!.firstName)
                assertEquals(newLastname, cust.lastName)
            }
        }
    }

    /**
     * Represents a scenario where DB connectivity is not present due to network issue, or DB service
     * unavailable.
     */
    @Nested
    inner class ConnectivityIssue {

        private val exceptionCause = "Connection not available"

        /**
         * Setup a connection failure scenario.
         */
        @BeforeEach
        fun setUp() {
            dao = DbCustomerDao(mockedDatasource())
        }

        private fun mockedDatasource(): DataSource {
            val mockedDataSource = mockk<DataSource>()
            val mockedConnection = mockk<Connection>()
            val exception = SQLException(exceptionCause)
            every { mockedConnection.prepareStatement(any<String>()) } throws exception
            every { mockedDataSource.connection } returns mockedConnection
            return mockedDataSource
        }

        @Test
        fun addingACustomerFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception::class.java) { dao.add(Customer(2, "Bernard", "Montgomery")) }
        }

        @Test
        fun deletingACustomerFailsWithExceptionAsFeedbackToTheClient() {
            assertThrows(Exception::class.java) { dao.delete(existingCustomer) }
        }

        @Test
        fun updatingACustomerFailsWithFeedbackToTheClient() {
            val newFirstname = "Bernard"
            val newLastname = "Montgomery"
            assertThrows(Exception::class.java) {
                dao.update(Customer(existingCustomer.id, newFirstname, newLastname))
            }
        }

        @Test
        fun retrievingACustomerByIdFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception::class.java) { dao.getById(existingCustomer.id) }
        }

        @Test
        fun retrievingAllCustomersFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception::class.java) { dao.getAll() }
        }
    }

    /**
     * Delete customer schema for fresh setup per test.
     */
    @AfterEach
    fun deleteSchema() {
        DriverManager.getConnection(DB_URL).use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(CustomerSchemaSql.DELETE_SCHEMA_SQL)
            }
        }
    }

    private fun assertCustomerCountIs(count: Int) {
        dao.getAll().use { allCustomers ->
            assertEquals(count.toLong(), allCustomers.count())
        }
    }

    /**
     * An arbitrary number which does not correspond to an active Customer id.
     *
     * @return an int of a customer id which doesn't exist
     */
    private fun getNonExistingCustomerId(): Int = 999
}
