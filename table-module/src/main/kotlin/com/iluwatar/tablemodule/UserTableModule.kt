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
package com.iluwatar.tablemodule

// ABOUTME: Table Module encapsulating domain logic for the USERS table.
// ABOUTME: Provides login and registerUser operations against a DataSource.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.sql.SQLException
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

/**
 * This class organizes domain logic with the user table in the database. A single instance of this
 * class contains the various procedures that will act on the data.
 */
class UserTableModule(private val dataSource: DataSource) {

    companion object {
        /** Public element for creating schema. */
        const val CREATE_SCHEMA_SQL: String =
            "CREATE TABLE IF NOT EXISTS USERS (ID NUMBER, USERNAME VARCHAR(30) UNIQUE,PASSWORD VARCHAR(30))"

        /** Public element for deleting schema. */
        const val DELETE_SCHEMA_SQL: String = "DROP TABLE USERS IF EXISTS"
    }

    /**
     * Login using username and password.
     *
     * @param username the username of a user
     * @param password the password of a user
     * @return the execution result of the method
     * @throws SQLException if any error
     */
    @Throws(SQLException::class)
    fun login(username: String, password: String): Int {
        val sql = "select count(*) from USERS where username=? and password=?"
        var resultSet: java.sql.ResultSet? = null
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                var result = 0
                preparedStatement.setString(1, username)
                preparedStatement.setString(2, password)
                try {
                    resultSet = preparedStatement.executeQuery()
                    while (resultSet!!.next()) {
                        result = resultSet!!.getInt(1)
                    }
                } finally {
                    resultSet?.close()
                }
                if (result == 1) {
                    logger.info { "Login successfully!" }
                } else {
                    logger.info { "Fail to login!" }
                }
                return result
            }
        }
    }

    /**
     * Register a new user.
     *
     * @param user a user instance
     * @return the execution result of the method
     * @throws SQLException if any error
     */
    @Throws(SQLException::class)
    fun registerUser(user: User): Int {
        val sql = "insert into USERS (username, password) values (?,?)"
        dataSource.connection.use { connection ->
            connection.prepareStatement(sql).use { preparedStatement ->
                preparedStatement.setString(1, user.username)
                preparedStatement.setString(2, user.password)
                val result = preparedStatement.executeUpdate()
                logger.info { "Register successfully!" }
                return result
            }
        }
    }
}
