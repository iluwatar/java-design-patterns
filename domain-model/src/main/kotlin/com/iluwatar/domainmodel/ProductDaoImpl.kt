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

// ABOUTME: Implements ProductDao interface with H2 database operations.
// ABOUTME: Provides JDBC-based persistence for product data.
package com.iluwatar.domainmodel

import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.sql.Date
import java.sql.SQLException
import javax.sql.DataSource

/** Implementations for database transactions of Product. */
class ProductDaoImpl(private val dataSource: DataSource) : ProductDao {

    @Throws(SQLException::class)
    override fun findByName(name: String): Product? {
        val sql = "select * from PRODUCTS where name = ?;"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, name)
                val rs = preparedStatement.executeQuery()

                return if (rs.next()) {
                    Product(
                        productDao = this,
                        name = rs.getString("name"),
                        price = Money.of(CurrencyUnit.USD, rs.getBigDecimal("price")),
                        expirationDate = rs.getDate("expiration_date").toLocalDate()
                    )
                } else {
                    null
                }
            }
        }
    }

    @Throws(SQLException::class)
    override fun save(product: Product) {
        val sql = "insert into PRODUCTS (name, price, expiration_date) values (?, ?, ?)"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, product.name)
                preparedStatement.setBigDecimal(2, product.price.amount)
                preparedStatement.setDate(3, Date.valueOf(product.expirationDate))
                preparedStatement.executeUpdate()
            }
        }
    }

    @Throws(SQLException::class)
    override fun update(product: Product) {
        val sql = "update PRODUCTS set price = ?, expiration_date = ? where name = ?;"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setBigDecimal(1, product.price.amount)
                preparedStatement.setDate(2, Date.valueOf(product.expirationDate))
                preparedStatement.setString(3, product.name)
                preparedStatement.executeUpdate()
            }
        }
    }
}
