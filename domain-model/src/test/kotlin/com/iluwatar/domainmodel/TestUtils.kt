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

// ABOUTME: Provides utility functions for database testing operations.
// ABOUTME: Contains helpers for creating data sources and managing test schema.
package com.iluwatar.domainmodel

import org.h2.jdbcx.JdbcDataSource
import java.sql.SQLException
import javax.sql.DataSource

object TestUtils {

    @JvmStatic
    @Throws(SQLException::class)
    fun executeSQL(sql: String, dataSource: DataSource) {
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.executeUpdate(sql)
            }
        }
    }

    @JvmStatic
    @Throws(SQLException::class)
    fun createSchema(dataSource: DataSource) {
        executeSQL(CREATE_SCHEMA_SQL, dataSource)
    }

    @JvmStatic
    @Throws(SQLException::class)
    fun deleteSchema(dataSource: DataSource) {
        executeSQL(DELETE_SCHEMA_SQL, dataSource)
    }

    @JvmStatic
    fun createDataSource(): DataSource {
        val dataSource = JdbcDataSource()
        dataSource.setURL(H2_DB_URL)
        return dataSource
    }
}
