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

// ABOUTME: Main application demonstrating the Data Access Object (DAO) pattern.
// ABOUTME: Shows CRUD operations using both in-memory and database-backed DAO implementations.

import io.github.oshai.kotlinlogging.KotlinLogging
import org.h2.jdbcx.JdbcDataSource
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

private const val DB_URL = "jdbc:h2:mem:dao;DB_CLOSE_DELAY=-1"
private const val ALL_CUSTOMERS = "customerDao.getAllCustomers(): "

/**
 * Data Access Object (DAO) is an object that provides an abstract interface to some type of
 * database or other persistence mechanism. By mapping application calls to the persistence layer,
 * DAO provide some specific data operations without exposing details of the database. This
 * isolation supports the Single responsibility principle. It separates what data accesses the
 * application needs, in terms of domain-specific objects and data types (the public interface of
 * the DAO), from how these needs can be satisfied with a specific DBMS.
 *
 * With the DAO pattern, we can use various method calls to retrieve/add/delete/update data
 * without directly interacting with the data source. The below example demonstrates basic CRUD
 * operations: select, add, update, and delete.
 */
fun main() {
    val inMemoryDao = InMemoryCustomerDao()
    performOperationsUsing(inMemoryDao)

    val dataSource = createDataSource()
    createSchema(dataSource)
    val dbDao = DbCustomerDao(dataSource)
    performOperationsUsing(dbDao)
    deleteSchema(dataSource)
}

private fun deleteSchema(dataSource: DataSource) {
    dataSource.connection.use { connection ->
        connection.createStatement().use { statement ->
            statement.execute(CustomerSchemaSql.DELETE_SCHEMA_SQL)
        }
    }
}

private fun createSchema(dataSource: DataSource) {
    dataSource.connection.use { connection ->
        connection.createStatement().use { statement ->
            statement.execute(CustomerSchemaSql.CREATE_SCHEMA_SQL)
        }
    }
}

private fun createDataSource(): DataSource {
    val dataSource = JdbcDataSource()
    dataSource.setURL(DB_URL)
    return dataSource
}

private fun performOperationsUsing(customerDao: CustomerDao) {
    addCustomers(customerDao)
    logger.info { ALL_CUSTOMERS }
    customerDao.getAll().use { customerStream ->
        customerStream.forEach { customer -> logger.info { customer.toString() } }
    }
    logger.info { "customerDao.getCustomerById(2): ${customerDao.getById(2)}" }
    val customer = Customer(4, "Dan", "Danson")
    customerDao.add(customer)
    logger.info { "$ALL_CUSTOMERS${customerDao.getAll()}" }
    customer.firstName = "Daniel"
    customer.lastName = "Danielson"
    customerDao.update(customer)
    logger.info { ALL_CUSTOMERS }
    customerDao.getAll().use { customerStream ->
        customerStream.forEach { cust -> logger.info { cust.toString() } }
    }
    customerDao.delete(customer)
    logger.info { "$ALL_CUSTOMERS${customerDao.getAll()}" }
}

private fun addCustomers(customerDao: CustomerDao) {
    for (customer in generateSampleCustomers()) {
        customerDao.add(customer)
    }
}

/**
 * Generate customers.
 *
 * @return list of customers.
 */
fun generateSampleCustomers(): List<Customer> {
    val customer1 = Customer(1, "Adam", "Adamson")
    val customer2 = Customer(2, "Bob", "Bobson")
    val customer3 = Customer(3, "Carl", "Carlson")
    return listOf(customer1, customer2, customer3)
}
