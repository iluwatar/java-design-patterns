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
package com.iluwatar.transactionscript

// ABOUTME: Implementation of HotelDao providing JDBC-based database operations for Room entities.
// ABOUTME: Handles connection management, SQL queries, and result set processing with streams.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.Optional
import java.util.Spliterator
import java.util.Spliterators
import java.util.function.Consumer
import java.util.stream.Stream
import java.util.stream.StreamSupport
import javax.sql.DataSource

private val logger = KotlinLogging.logger {}

/**
 * Implementation of database operations for Hotel class.
 */
class HotelDaoImpl(private val dataSource: DataSource) : HotelDao {

    @Throws(Exception::class)
    override fun getAll(): Stream<Room> {
        try {
            val connection = getConnection()
            val statement = connection.prepareStatement("SELECT * FROM ROOMS") // NOSONAR
            val resultSet = statement.executeQuery() // NOSONAR
            return StreamSupport.stream(
                object : Spliterators.AbstractSpliterator<Room>(Long.MAX_VALUE, Spliterator.ORDERED) {
                    override fun tryAdvance(action: Consumer<in Room>): Boolean {
                        try {
                            if (!resultSet.next()) {
                                return false
                            }
                            action.accept(createRoom(resultSet))
                            return true
                        } catch (e: Exception) {
                            throw RuntimeException(e) // NOSONAR
                        }
                    }
                },
                false
            ).onClose {
                try {
                    mutedClose(connection, statement, resultSet)
                } catch (e: Exception) {
                    logger.error { e.message }
                }
            }
        } catch (e: Exception) {
            throw Exception(e.message, e)
        }
    }

    @Throws(Exception::class)
    override fun getById(id: Int): Optional<Room> {
        var resultSet: ResultSet? = null

        try {
            getConnection().use { connection ->
                connection.prepareStatement("SELECT * FROM ROOMS WHERE ID = ?").use { statement ->
                    statement.setInt(1, id)
                    resultSet = statement.executeQuery()
                    return if (resultSet!!.next()) {
                        Optional.of(createRoom(resultSet!!))
                    } else {
                        Optional.empty()
                    }
                }
            }
        } catch (e: Exception) {
            throw Exception(e.message, e)
        } finally {
            resultSet?.close()
        }
    }

    @Throws(Exception::class)
    override fun add(room: Room): Boolean {
        if (getById(room.id).isPresent) {
            return false
        }

        try {
            getConnection().use { connection ->
                connection.prepareStatement("INSERT INTO ROOMS VALUES (?,?,?,?)").use { statement ->
                    statement.setInt(1, room.id)
                    statement.setString(2, room.roomType)
                    statement.setInt(3, room.price)
                    statement.setBoolean(4, room.isBooked)
                    statement.execute()
                    return true
                }
            }
        } catch (e: Exception) {
            throw Exception(e.message, e)
        }
    }

    @Throws(Exception::class)
    override fun update(room: Room): Boolean {
        try {
            getConnection().use { connection ->
                connection.prepareStatement(
                    "UPDATE ROOMS SET ROOM_TYPE = ?, PRICE = ?, BOOKED = ? WHERE ID = ?"
                ).use { statement ->
                    statement.setString(1, room.roomType)
                    statement.setInt(2, room.price)
                    statement.setBoolean(3, room.isBooked)
                    statement.setInt(4, room.id)
                    return statement.executeUpdate() > 0
                }
            }
        } catch (e: Exception) {
            throw Exception(e.message, e)
        }
    }

    @Throws(Exception::class)
    override fun delete(room: Room): Boolean {
        try {
            getConnection().use { connection ->
                connection.prepareStatement("DELETE FROM ROOMS WHERE ID = ?").use { statement ->
                    statement.setInt(1, room.id)
                    return statement.executeUpdate() > 0
                }
            }
        } catch (e: Exception) {
            throw Exception(e.message, e)
        }
    }

    @Throws(Exception::class)
    private fun getConnection(): Connection {
        return dataSource.connection
    }

    @Throws(Exception::class)
    private fun mutedClose(connection: Connection, statement: PreparedStatement, resultSet: ResultSet) {
        try {
            resultSet.close()
            statement.close()
            connection.close()
        } catch (e: Exception) {
            throw Exception(e.message, e)
        }
    }

    @Throws(Exception::class)
    private fun createRoom(resultSet: ResultSet): Room {
        return Room(
            resultSet.getInt("ID"),
            resultSet.getString("ROOM_TYPE"),
            resultSet.getInt("PRICE"),
            resultSet.getBoolean("BOOKED")
        )
    }
}
