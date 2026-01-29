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

// ABOUTME: Utility object for creating and initializing the H2 in-memory database.
// ABOUTME: Creates the user_account table schema on class loading via a static initializer.
package com.iluwatar.metamapping.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import org.h2.jdbcx.JdbcDataSource
import java.sql.SQLException

private val logger = KotlinLogging.logger {}

private const val DB_URL = "jdbc:h2:mem:metamapping"
private const val CREATE_SCHEMA_SQL = """
    DROP TABLE IF EXISTS `user_account`;CREATE TABLE `user_account` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `username` varchar(255) NOT NULL,
      `password` varchar(255) NOT NULL,
      PRIMARY KEY (`id`)
    );"""

/**
 * Create h2 database.
 */
object DatabaseUtil {
    init {
        logger.info { "create h2 database" }
        val source = JdbcDataSource().apply {
            setURL(DB_URL)
        }
        try {
            source.connection.createStatement().use { statement ->
                statement.execute(CREATE_SCHEMA_SQL)
            }
        } catch (e: SQLException) {
            logger.error(e) { "unable to create h2 data source" }
        }
    }
}
