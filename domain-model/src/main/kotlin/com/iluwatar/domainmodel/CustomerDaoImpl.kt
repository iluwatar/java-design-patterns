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

// ABOUTME: Implements CustomerDao interface with H2 database operations.
// ABOUTME: Provides JDBC-based persistence for customer data and purchases.
package com.iluwatar.domainmodel

import org.joda.money.CurrencyUnit
import org.joda.money.Money
import java.sql.SQLException
import javax.sql.DataSource

/** Implementations for database operations of Customer. */
class CustomerDaoImpl(private val dataSource: DataSource) : CustomerDao {

    @Throws(SQLException::class)
    override fun findByName(name: String): Customer? {
        val sql = "select * from CUSTOMERS where name = ?;"

        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, name)
                val rs = preparedStatement.executeQuery()

                return if (rs.next()) {
                    Customer(
                        customerDao = this,
                        name = rs.getString("name"),
                        money = Money.of(CurrencyUnit.USD, rs.getBigDecimal("money"))
                    )
                } else {
                    null
                }
            }
        }
    }

    @Throws(SQLException::class)
    override fun update(customer: Customer) {
        val sql = "update CUSTOMERS set money = ? where name = ?;"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setBigDecimal(1, customer.money.amount)
                preparedStatement.setString(2, customer.name)
                preparedStatement.executeUpdate()
            }
        }
    }

    @Throws(SQLException::class)
    override fun save(customer: Customer) {
        val sql = "insert into CUSTOMERS (name, money) values (?, ?)"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, customer.name)
                preparedStatement.setBigDecimal(2, customer.money.amount)
                preparedStatement.executeUpdate()
            }
        }
    }

    @Throws(SQLException::class)
    override fun addProduct(product: Product, customer: Customer) {
        val sql = "insert into PURCHASES (product_name, customer_name) values (?,?)"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, product.name)
                preparedStatement.setString(2, customer.name)
                preparedStatement.executeUpdate()
            }
        }
    }

    @Throws(SQLException::class)
    override fun deleteProduct(product: Product, customer: Customer) {
        val sql = "delete from PURCHASES where product_name = ? and customer_name = ?"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, product.name)
                preparedStatement.setString(2, customer.name)
                preparedStatement.executeUpdate()
            }
        }
    }
}
