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

// ABOUTME: Service class for database operations with H2.
// ABOUTME: Handles CRUD operations for storing serialized LOB data.
package com.iluwatar.slob.dbservice

import io.github.oshai.kotlinlogging.KotlinLogging
import org.h2.jdbcx.JdbcDataSource
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

/**
 * Service to handle database operations.
 *
 * @property dataTypeDb Type of data that is to be stored in DB can be 'TEXT' or 'BINARY'.
 */
class DatabaseService(var dataTypeDb: String) {

    companion object {
        const val CREATE_BINARY_SCHEMA_DDL =
            "CREATE TABLE IF NOT EXISTS FORESTS (ID NUMBER UNIQUE, NAME VARCHAR(30),FOREST VARBINARY)"
        const val CREATE_TEXT_SCHEMA_DDL =
            "CREATE TABLE IF NOT EXISTS FORESTS (ID NUMBER UNIQUE, NAME VARCHAR(30),FOREST VARCHAR)"
        const val DELETE_SCHEMA_SQL = "DROP TABLE FORESTS IF EXISTS"
        const val BINARY_DATA = "BINARY"
        private const val DB_URL = "jdbc:h2:~/test"
        private const val INSERT = "insert into FORESTS (id,name, forest) values (?,?,?)"
        private const val SELECT = "select FOREST from FORESTS where id = ?"
        private val dataSource: DataSource = createDataSource()

        /**
         * Initiates Data source.
         *
         * @return created data source
         */
        private fun createDataSource(): DataSource {
            val ds = JdbcDataSource()
            ds.setURL(DB_URL)
            return ds
        }
    }

    /**
     * Shutdown Sequence executes Query [DELETE_SCHEMA_SQL].
     *
     * @throws java.sql.SQLException if any issue occurs while executing DROP Query
     */
    fun shutDownService() {
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(DELETE_SCHEMA_SQL)
            }
        }
    }

    /**
     * Initiates startup sequence and executes the query [CREATE_BINARY_SCHEMA_DDL]
     * if [dataTypeDb] is binary else will execute the query [CREATE_TEXT_SCHEMA_DDL].
     *
     * @throws java.sql.SQLException if there are any issues during DDL execution
     */
    fun startupService() {
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                if (dataTypeDb == BINARY_DATA) {
                    statement.execute(CREATE_BINARY_SCHEMA_DDL)
                } else {
                    statement.execute(CREATE_TEXT_SCHEMA_DDL)
                }
            }
        }
    }

    /**
     * Executes the insert query [INSERT].
     *
     * @param id with which row is to be inserted
     * @param name name to be added in the row
     * @param data object data to be saved in the row
     * @throws java.sql.SQLException if there are any issues in executing insert query [INSERT]
     */
    fun insert(id: Int, name: String, data: Any?) {
        dataSource.connection.use { connection ->
            connection.prepareStatement(INSERT).use { insert ->
                insert.setInt(1, id)
                insert.setString(2, name)
                insert.setObject(3, data)
                insert.execute()
            }
        }
    }

    /**
     * Runs the select query [SELECT] form the result set returns an [java.io.InputStream]
     * if [dataTypeDb] is 'binary' else will return the object as a [String].
     *
     * @param id with which row is to be selected
     * @param columnsName column in which the object is stored
     * @return object found from DB
     * @throws java.sql.SQLException if there are any issues in executing insert query [SELECT]
     */
    fun select(id: Long, columnsName: String): Any? {
        dataSource.connection.use { connection ->
            connection.prepareStatement(SELECT).use { preparedStatement ->
                preparedStatement.setLong(1, id)
                preparedStatement.executeQuery().use { resultSet ->
                    var result: Any? = null
                    while (resultSet.next()) {
                        result = if (dataTypeDb == BINARY_DATA) {
                            resultSet.getBinaryStream(columnsName)
                        } else {
                            resultSet.getString(columnsName)
                        }
                    }
                    return result
                }
            }
        }
    }
}
