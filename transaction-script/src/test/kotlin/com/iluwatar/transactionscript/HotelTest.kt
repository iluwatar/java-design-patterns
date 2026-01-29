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

// ABOUTME: Tests the Hotel class for booking and cancellation transaction scripts.
// ABOUTME: Verifies correct behavior for room booking, cancellation, and error conditions.

import org.h2.jdbcx.JdbcDataSource
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.sql.DataSource

/**
 * Tests [Hotel]
 */
class HotelTest {

    private lateinit var hotel: Hotel
    private lateinit var dao: HotelDaoImpl

    companion object {
        private const val H2_DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"

        fun createDataSource(): DataSource {
            val dataSource = JdbcDataSource()
            dataSource.setUrl(H2_DB_URL)
            return dataSource
        }

        fun generateSampleRooms(): List<Room> {
            val room1 = Room(1, "Single", 50, false)
            val room2 = Room(2, "Double", 80, false)
            val room3 = Room(3, "Queen", 120, false)
            val room4 = Room(4, "King", 150, false)
            val room5 = Room(5, "Single", 50, false)
            val room6 = Room(6, "Double", 80, false)
            return listOf(room1, room2, room3, room4, room5, room6)
        }

        @Throws(java.sql.SQLException::class)
        private fun deleteSchema(dataSource: DataSource) {
            dataSource.connection.use { connection ->
                connection.createStatement().use { statement ->
                    statement.execute(RoomSchemaSql.DELETE_SCHEMA_SQL)
                }
            }
        }

        @Throws(Exception::class)
        private fun createSchema(dataSource: DataSource) {
            try {
                dataSource.connection.use { connection ->
                    connection.createStatement().use { statement ->
                        statement.execute(RoomSchemaSql.CREATE_SCHEMA_SQL)
                    }
                }
            } catch (e: Exception) {
                throw Exception(e.message, e)
            }
        }

        @Throws(Exception::class)
        private fun addRooms(hotelDao: HotelDaoImpl) {
            for (room in generateSampleRooms()) {
                hotelDao.add(room)
            }
        }
    }

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        val dataSource = createDataSource()
        deleteSchema(dataSource)
        createSchema(dataSource)
        dao = HotelDaoImpl(dataSource)
        addRooms(dao)
        hotel = Hotel(dao)
    }

    @Test
    @Throws(Exception::class)
    fun bookingRoomShouldChangeBookedStatusToTrue() {
        hotel.bookRoom(1)
        assertTrue(dao.getById(1).isPresent)
        assertTrue(dao.getById(1).get().isBooked)
    }

    @Test
    fun bookingRoomWithInvalidIdShouldRaiseException() {
        assertThrows(Exception::class.java) { hotel.bookRoom(getNonExistingRoomId()) }
    }

    @Test
    @Throws(Exception::class)
    fun bookingRoomAgainShouldRaiseException() {
        hotel.bookRoom(1)
        assertThrows(Exception::class.java) { hotel.bookRoom(1) }
    }

    @Test
    @Throws(Exception::class)
    fun notBookingRoomShouldNotChangeBookedStatus() {
        assertTrue(dao.getById(1).isPresent)
        assertFalse(dao.getById(1).get().isBooked)
    }

    @Test
    @Throws(Exception::class)
    fun cancelRoomBookingShouldChangeBookedStatus() {
        hotel.bookRoom(1)
        assertTrue(dao.getById(1).isPresent)
        assertTrue(dao.getById(1).get().isBooked)

        hotel.cancelRoomBooking(1)
        assertTrue(dao.getById(1).isPresent)
        assertFalse(dao.getById(1).get().isBooked)
    }

    @Test
    fun cancelRoomBookingWithInvalidIdShouldRaiseException() {
        assertThrows(Exception::class.java) { hotel.cancelRoomBooking(getNonExistingRoomId()) }
    }

    @Test
    fun cancelRoomBookingForUnbookedRoomShouldRaiseException() {
        assertThrows(Exception::class.java) { hotel.cancelRoomBooking(1) }
    }

    /**
     * An arbitrary number which does not correspond to an active Room id.
     *
     * @return an int of a room id which doesn't exist
     */
    private fun getNonExistingRoomId(): Int {
        return 999
    }
}
