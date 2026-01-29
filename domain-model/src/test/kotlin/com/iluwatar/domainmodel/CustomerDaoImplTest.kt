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

// ABOUTME: Tests for CustomerDaoImpl database operations.
// ABOUTME: Validates CRUD operations and purchase management for customers.
package com.iluwatar.domainmodel

import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.SQLException
import java.time.LocalDate
import javax.sql.DataSource

class CustomerDaoImplTest {

    private lateinit var dataSource: DataSource
    private lateinit var product: Product
    private lateinit var customer: Customer
    private lateinit var customerDao: CustomerDao

    @BeforeEach
    fun setUp() {
        // create db schema
        dataSource = TestUtils.createDataSource()

        TestUtils.deleteSchema(dataSource)
        TestUtils.createSchema(dataSource)

        // setup objects
        customerDao = CustomerDaoImpl(dataSource)

        customer = Customer(
            customerDao = customerDao,
            name = "customer",
            money = Money.of(CurrencyUnit.USD, 100.0)
        )

        product = Product(
            productDao = ProductDaoImpl(dataSource),
            name = "product",
            price = Money.of(CurrencyUnit.USD, 100.0),
            expirationDate = LocalDate.parse("2021-06-27")
        )
    }

    @AfterEach
    fun tearDown() {
        TestUtils.deleteSchema(dataSource)
    }

    @Test
    fun shouldFindCustomerByName() {
        var foundCustomer = customerDao.findByName("customer")

        assertNull(foundCustomer)

        TestUtils.executeSQL(INSERT_CUSTOMER_SQL, dataSource)

        foundCustomer = customerDao.findByName("customer")

        assertNotNull(foundCustomer)
        assertEquals("customer", foundCustomer!!.name)
        assertEquals(Money.of(CurrencyUnit.USD, 100.0), foundCustomer.money)
    }

    @Test
    fun shouldSaveCustomer() {
        customerDao.save(customer)

        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery(SELECT_CUSTOMERS_SQL).use { rs ->
                    assertTrue(rs.next())
                    assertEquals(customer.name, rs.getString("name"))
                    assertEquals(customer.money, Money.of(CurrencyUnit.USD, rs.getBigDecimal("money")))
                }
            }
        }

        assertThrows(SQLException::class.java) { customerDao.save(customer) }
    }

    @Test
    fun shouldUpdateCustomer() {
        TestUtils.executeSQL(INSERT_CUSTOMER_SQL, dataSource)

        customer.money = Money.of(CurrencyUnit.USD, 99.0)

        customerDao.update(customer)

        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery(SELECT_CUSTOMERS_SQL).use { rs ->
                    assertTrue(rs.next())
                    assertEquals(customer.name, rs.getString("name"))
                    assertEquals(customer.money, Money.of(CurrencyUnit.USD, rs.getBigDecimal("money")))
                    assertFalse(rs.next())
                }
            }
        }
    }

    @Test
    fun shouldAddProductToPurchases() {
        TestUtils.executeSQL(INSERT_CUSTOMER_SQL, dataSource)
        TestUtils.executeSQL(ProductDaoImplTest.INSERT_PRODUCT_SQL, dataSource)

        customerDao.addProduct(product, customer)

        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery(SELECT_PURCHASES_SQL).use { rs ->
                    assertTrue(rs.next())
                    assertEquals(product.name, rs.getString("product_name"))
                    assertEquals(customer.name, rs.getString("customer_name"))
                    assertFalse(rs.next())
                }
            }
        }
    }

    @Test
    fun shouldDeleteProductFromPurchases() {
        TestUtils.executeSQL(INSERT_CUSTOMER_SQL, dataSource)
        TestUtils.executeSQL(ProductDaoImplTest.INSERT_PRODUCT_SQL, dataSource)
        TestUtils.executeSQL(INSERT_PURCHASES_SQL, dataSource)

        customerDao.deleteProduct(product, customer)

        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery(SELECT_PURCHASES_SQL).use { rs ->
                    assertFalse(rs.next())
                }
            }
        }
    }

    companion object {
        const val INSERT_CUSTOMER_SQL = "insert into CUSTOMERS values('customer', 100)"
        const val SELECT_CUSTOMERS_SQL = "select name, money from CUSTOMERS"
        const val INSERT_PURCHASES_SQL = "insert into PURCHASES values('product', 'customer')"
        const val SELECT_PURCHASES_SQL = "select product_name, customer_name from PURCHASES"
    }
}
