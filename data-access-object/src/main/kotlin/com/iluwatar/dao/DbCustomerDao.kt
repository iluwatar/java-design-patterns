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

// ABOUTME: Database implementation of CustomerDao that persists customers in RDBMS.
// ABOUTME: Uses JDBC with DataSource for database connectivity and SQL operations.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.Spliterator
import java.util.Spliterators
import java.util.stream.Stream
import java.util.stream.StreamSupport
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

/**
 * An implementation of [CustomerDao] that persists customers in RDBMS.
 */
class DbCustomerDao(private val dataSource: DataSource) : CustomerDao {

    /**
     * Get all customers as Java Stream.
     *
     * @return a lazily populated stream of customers. Note the stream returned must be closed to free
     *     all the acquired resources. The stream keeps an open connection to the database till it is
     *     complete or is closed manually.
     */
    override fun getAll(): Stream<Customer> {
        try {
            val connection = dataSource.connection
            val statement = connection.prepareStatement("SELECT * FROM CUSTOMERS")
            val resultSet = statement.executeQuery()

            return StreamSupport.stream(
                object : Spliterators.AbstractSpliterator<Customer>(Long.MAX_VALUE, Spliterator.ORDERED) {
                    override fun tryAdvance(action: java.util.function.Consumer<in Customer>): Boolean {
                        try {
                            if (!resultSet.next()) {
                                return false
                            }
                            action.accept(createCustomer(resultSet))
                            return true
                        } catch (e: SQLException) {
                            throw RuntimeException(e)
                        }
                    }
                },
                false
            ).onClose { mutedClose(connection, statement, resultSet) }
        } catch (e: SQLException) {
            throw CustomException(e.message ?: "", e)
        }
    }

    private fun mutedClose(connection: Connection, statement: PreparedStatement, resultSet: ResultSet) {
        try {
            resultSet.close()
            statement.close()
            connection.close()
        } catch (e: SQLException) {
            logger.info { "Exception thrown ${e.message}" }
        }
    }

    private fun createCustomer(resultSet: ResultSet): Customer {
        return Customer(
            resultSet.getInt("ID"),
            resultSet.getString("FNAME"),
            resultSet.getString("LNAME")
        )
    }

    override fun getById(id: Int): Customer? {
        var resultSet: ResultSet? = null

        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE ID = ?").use { statement ->
                    statement.setInt(1, id)
                    resultSet = statement.executeQuery()
                    return if (resultSet!!.next()) {
                        createCustomer(resultSet!!)
                    } else {
                        null
                    }
                }
            }
        } catch (ex: SQLException) {
            throw CustomException(ex.message ?: "", ex)
        } finally {
            resultSet?.close()
        }
    }

    override fun add(customer: Customer): Boolean {
        if (getById(customer.id) != null) {
            return false
        }

        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement("INSERT INTO CUSTOMERS VALUES (?,?,?)").use { statement ->
                    statement.setInt(1, customer.id)
                    statement.setString(2, customer.firstName)
                    statement.setString(3, customer.lastName)
                    statement.execute()
                    return true
                }
            }
        } catch (ex: SQLException) {
            throw CustomException(ex.message ?: "", ex)
        }
    }

    override fun update(customer: Customer): Boolean {
        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement("UPDATE CUSTOMERS SET FNAME = ?, LNAME = ? WHERE ID = ?").use { statement ->
                    statement.setString(1, customer.firstName)
                    statement.setString(2, customer.lastName)
                    statement.setInt(3, customer.id)
                    return statement.executeUpdate() > 0
                }
            }
        } catch (ex: SQLException) {
            throw CustomException(ex.message ?: "", ex)
        }
    }

    override fun delete(customer: Customer): Boolean {
        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement("DELETE FROM CUSTOMERS WHERE ID = ?").use { statement ->
                    statement.setInt(1, customer.id)
                    return statement.executeUpdate() > 0
                }
            }
        } catch (ex: SQLException) {
            throw CustomException(ex.message ?: "", ex)
        }
    }
}
