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

// ABOUTME: Tests for ProductDaoImpl database operations.
// ABOUTME: Validates find, save, and update operations for products.
package com.iluwatar.domainmodel

import org.joda.money.CurrencyUnit
import org.joda.money.Money
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.SQLException
import java.time.LocalDate
import javax.sql.DataSource

class ProductDaoImplTest {

    private lateinit var dataSource: DataSource
    private lateinit var productDao: ProductDao
    private lateinit var product: Product

    @BeforeEach
    fun setUp() {
        // create schema
        dataSource = TestUtils.createDataSource()

        TestUtils.deleteSchema(dataSource)
        TestUtils.createSchema(dataSource)

        // setup objects
        productDao = ProductDaoImpl(dataSource)

        product = Product(
            productDao = productDao,
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
    fun shouldFindProductByName() {
        var foundProduct = productDao.findByName("product")

        assertNull(foundProduct)

        TestUtils.executeSQL(INSERT_PRODUCT_SQL, dataSource)

        foundProduct = productDao.findByName("product")

        assertNotNull(foundProduct)
        assertEquals("product", foundProduct!!.name)
        assertEquals(Money.of(CurrencyUnit.USD, 100.0), foundProduct.price)
        assertEquals(LocalDate.parse("2021-06-27"), foundProduct.expirationDate)
    }

    @Test
    fun shouldSaveProduct() {
        productDao.save(product)

        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery(SELECT_PRODUCTS_SQL).use { rs ->
                    assertTrue(rs.next())
                    assertEquals(product.name, rs.getString("name"))
                    assertEquals(product.price, Money.of(CurrencyUnit.USD, rs.getBigDecimal("price")))
                    assertEquals(product.expirationDate, rs.getDate("expiration_date").toLocalDate())
                }
            }
        }

        assertThrows(SQLException::class.java) { productDao.save(product) }
    }

    @Test
    fun shouldUpdateProduct() {
        TestUtils.executeSQL(INSERT_PRODUCT_SQL, dataSource)

        product.price = Money.of(CurrencyUnit.USD, 99.0)

        productDao.update(product)

        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery(SELECT_PRODUCTS_SQL).use { rs ->
                    assertTrue(rs.next())
                    assertEquals(product.name, rs.getString("name"))
                    assertEquals(product.price, Money.of(CurrencyUnit.USD, rs.getBigDecimal("price")))
                    assertEquals(product.expirationDate, rs.getDate("expiration_date").toLocalDate())
                }
            }
        }
    }

    companion object {
        const val INSERT_PRODUCT_SQL = "insert into PRODUCTS values('product', 100, DATE '2021-06-27')"
        const val SELECT_PRODUCTS_SQL = "select name, price, expiration_date from PRODUCTS"
    }
}
