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

// ABOUTME: SQL-based DAO implementation that serializes Country objects to database BLOBs.
// ABOUTME: Uses Java ObjectOutputStream/ObjectInputStream for binary serialization.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.sql.SQLException
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

/** Country Schema SQL Class. */
class CountrySchemaSql(
    private var country: Country,
    private val dataSource: DataSource
) : CountryDao {

    init {
        country = country.copy()
    }

    companion object {
        const val CREATE_SCHEMA_SQL =
            "CREATE TABLE IF NOT EXISTS WORLD (ID INT PRIMARY KEY, COUNTRY BLOB)"
        const val DELETE_SCHEMA_SQL = "DROP TABLE WORLD IF EXISTS"
    }

    /**
     * This method will serialize a Country object and store it to database.
     *
     * @return int type, if successfully insert a serialized object to database then return country
     *     code, else return -1.
     * @throws IOException if any.
     */
    override fun insertCountry(): Int {
        val sql = "INSERT INTO WORLD (ID, COUNTRY) VALUES (?, ?)"
        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(sql).use { preparedStatement ->
                    ByteArrayOutputStream().use { baos ->
                        ObjectOutputStream(baos).use { oos ->
                            oos.writeObject(country)
                            oos.flush()

                            preparedStatement.setInt(1, country.code)
                            preparedStatement.setBlob(2, ByteArrayInputStream(baos.toByteArray()))
                            preparedStatement.execute()
                            return country.code
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            logger.info { "Exception thrown ${e.message}" }
        }
        return -1
    }

    /**
     * This method will select a data item from database and deserialize it.
     *
     * @return int type, if successfully select and deserialized object from database then return
     *     country code, else return -1.
     * @throws IOException if any.
     * @throws ClassNotFoundException if any.
     */
    override fun selectCountry(): Int {
        val sql = "SELECT ID, COUNTRY FROM WORLD WHERE ID = ?"
        try {
            dataSource.connection.use { connection ->
                connection.prepareStatement(sql).use { preparedStatement ->
                    preparedStatement.setInt(1, country.code)

                    preparedStatement.executeQuery().use { rs ->
                        if (rs.next()) {
                            val countryBlob = rs.getBlob("country")
                            val baos = ByteArrayInputStream(
                                countryBlob.getBytes(1, countryBlob.length().toInt())
                            )
                            val ois = ObjectInputStream(baos)
                            country = ois.readObject() as Country
                            logger.info { "Country: $country" }
                        }
                        return rs.getInt("id")
                    }
                }
            }
        } catch (e: SQLException) {
            logger.info { "Exception thrown ${e.message}" }
        }
        return -1
    }
}
