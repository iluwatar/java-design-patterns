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
package com.iluwatar.serializedentity

// ABOUTME: Entry point demonstrating the Serialized Entity pattern.
// ABOUTME: Serializes Country objects to an H2 database and deserializes them back.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.sql.SQLException
import javax.sql.DataSource
import org.h2.jdbcx.JdbcDataSource

private val logger = KotlinLogging.logger {}

private const val DB_URL = "jdbc:h2:~/testdb"

/**
 * Serialized Entity Pattern.
 *
 * Serialized Entity Pattern allows us to easily persist Java/Kotlin objects to the database. It
 * uses Serializable interface and DAO pattern. Serialized Entity Pattern will first use Serializable
 * to convert an object into a set of bytes, then it will use DAO pattern to store this set of bytes
 * as BLOB to database.
 *
 * In this example, we first initialize two Country objects "China" and "UnitedArabEmirates", then
 * we use CountrySchemaSql to serialize and persist them to the database. Last, we read them back
 * from the database as sets of bytes and deserialize them back to Country objects.
 */
fun main() {
    val dataSource = createDataSource()

    deleteSchema(dataSource)
    createSchema(dataSource)

    // Initializing Country Object China
    val china = Country(86, "China", "Asia", "Chinese")

    // Initializing Country Object UnitedArabEmirates
    val unitedArabEmirates = Country(971, "United Arab Emirates", "Asia", "Arabic")

    // Initializing CountrySchemaSql Object with parameter "China" and "dataSource"
    val serializedChina = CountrySchemaSql(china, dataSource)
    // Initializing CountrySchemaSql Object with parameter "UnitedArabEmirates" and "dataSource"
    val serializedUnitedArabEmirates = CountrySchemaSql(unitedArabEmirates, dataSource)

    /*
    By using CountrySchemaSql.insertCountry() method, the private (Country) type variable within Object
    CountrySchemaSql will be serialized to a set of bytes and persist to database.
    For more details of CountrySchemaSql.insertCountry() method please refer to CountrySchemaSql.kt file
    */
    serializedChina.insertCountry()
    serializedUnitedArabEmirates.insertCountry()

    /*
    By using CountrySchemaSql.selectCountry() method, CountrySchemaSql object will read the sets of bytes from database
    and deserialize it to Country object.
    For more details of CountrySchemaSql.selectCountry() method please refer to CountrySchemaSql.kt file
    */
    serializedChina.selectCountry()
    serializedUnitedArabEmirates.selectCountry()
}

private fun deleteSchema(dataSource: DataSource) {
    try {
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(CountrySchemaSql.DELETE_SCHEMA_SQL)
            }
        }
    } catch (e: SQLException) {
        logger.info { "Exception thrown ${e.message}" }
    }
}

private fun createSchema(dataSource: DataSource) {
    try {
        dataSource.connection.use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(CountrySchemaSql.CREATE_SCHEMA_SQL)
            }
        }
    } catch (e: SQLException) {
        logger.info { "Exception thrown ${e.message}" }
    }
}

private fun createDataSource(): DataSource {
    val dataSource = JdbcDataSource()
    dataSource.setURL(DB_URL)
    return dataSource
}
