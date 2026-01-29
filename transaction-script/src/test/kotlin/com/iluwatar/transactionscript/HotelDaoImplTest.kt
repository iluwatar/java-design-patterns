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

// ABOUTME: Tests the HotelDaoImpl for CRUD operations on Room entities.
// ABOUTME: Covers connection success, non-existing/existing room scenarios, and connectivity issues.

import io.mockk.every
import io.mockk.mockk
import org.h2.jdbcx.JdbcDataSource
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import javax.sql.DataSource

/**
 * Tests [HotelDaoImpl].
 */
class HotelDaoImplTest {

    private lateinit var dao: HotelDaoImpl
    private var existingRoom = Room(1, "Single", 50, false)

    companion object {
        private const val DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"
    }

    /**
     * Creates rooms schema.
     *
     * @throws SQLException if there is any error while creating schema.
     */
    @BeforeEach
    @Throws(SQLException::class)
    fun createSchema() {
        DriverManager.getConnection(DB_URL).use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(RoomSchemaSql.DELETE_SCHEMA_SQL)
                statement.execute(RoomSchemaSql.CREATE_SCHEMA_SQL)
            }
        }
    }

    /**
     * Represents the scenario where DB connectivity is present.
     */
    @Nested
    inner class ConnectionSuccess {

        /**
         * Setup for connection success scenario.
         *
         * @throws Exception if any error occurs.
         */
        @BeforeEach
        @Throws(Exception::class)
        fun setUp() {
            val dataSource = JdbcDataSource()
            dataSource.setURL(DB_URL)
            dao = HotelDaoImpl(dataSource)
            val result = dao.add(existingRoom)
            assertTrue(result)
        }

        /**
         * Represents the scenario when DAO operations are being performed on a non-existing room.
         */
        @Nested
        inner class NonExistingRoom {

            @Test
            @Throws(Exception::class)
            fun addingShouldResultInSuccess() {
                dao.getAll().use { allRooms ->
                    assumeTrue(allRooms.count() == 1L)
                }

                val nonExistingRoom = Room(2, "Double", 80, false)
                val result = dao.add(nonExistingRoom)
                assertTrue(result)

                assertRoomCountIs(2)
                assertEquals(nonExistingRoom, dao.getById(nonExistingRoom.id).get())
            }

            @Test
            @Throws(Exception::class)
            fun deletionShouldBeFailureAndNotAffectExistingRooms() {
                val nonExistingRoom = Room(2, "Double", 80, false)
                val result = dao.delete(nonExistingRoom)

                assertFalse(result)
                assertRoomCountIs(1)
            }

            @Test
            @Throws(Exception::class)
            fun updationShouldBeFailureAndNotAffectExistingRooms() {
                val nonExistingId = getNonExistingRoomId()
                val newRoomType = "Double"
                val newPrice = 80
                val room = Room(nonExistingId, newRoomType, newPrice, false)
                val result = dao.update(room)

                assertFalse(result)
                assertFalse(dao.getById(nonExistingId).isPresent)
            }

            @Test
            @Throws(Exception::class)
            fun retrieveShouldReturnNoRoom() {
                assertFalse(dao.getById(getNonExistingRoomId()).isPresent)
            }
        }

        /**
         * Represents a scenario where DAO operations are being performed on an already existing room.
         */
        @Nested
        inner class ExistingRoom {

            @Test
            @Throws(Exception::class)
            fun addingShouldResultInFailureAndNotAffectExistingRooms() {
                val existingRoom = Room(1, "Single", 50, false)
                val result = dao.add(existingRoom)

                assertFalse(result)
                assertRoomCountIs(1)
                assertEquals(existingRoom, dao.getById(existingRoom.id).get())
            }

            @Test
            @Throws(Exception::class)
            fun deletionShouldBeSuccessAndRoomShouldBeNonAccessible() {
                val result = dao.delete(existingRoom)

                assertTrue(result)
                assertRoomCountIs(0)
                assertFalse(dao.getById(existingRoom.id).isPresent)
            }

            @Test
            @Throws(Exception::class)
            fun updationShouldBeSuccessAndAccessingTheSameRoomShouldReturnUpdatedInformation() {
                val newRoomType = "Double"
                val newPrice = 80
                val newBookingStatus = false
                val room = Room(existingRoom.id, newRoomType, newPrice, newBookingStatus)
                val result = dao.update(room)

                assertTrue(result)

                val retrievedRoom = dao.getById(existingRoom.id).get()
                assertEquals(newRoomType, retrievedRoom.roomType)
                assertEquals(newPrice, retrievedRoom.price)
                assertEquals(newBookingStatus, retrievedRoom.isBooked)
            }
        }
    }

    /**
     * Represents a scenario where DB connectivity is not present due to network issue, or DB service
     * unavailable.
     */
    @Nested
    inner class ConnectivityIssue {

        private val exceptionCause = "Connection not available"

        /**
         * setup a connection failure scenario.
         *
         * @throws SQLException if any error occurs.
         */
        @BeforeEach
        @Throws(SQLException::class)
        fun setUp() {
            dao = HotelDaoImpl(mockedDatasource())
        }

        @Throws(SQLException::class)
        private fun mockedDatasource(): DataSource {
            val mockedDataSource = mockk<DataSource>()
            val mockedConnection = mockk<Connection>()
            val exception = SQLException(exceptionCause)
            every { mockedConnection.prepareStatement(any()) } throws exception
            every { mockedDataSource.connection } returns mockedConnection
            return mockedDataSource
        }

        @Test
        fun addingARoomFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception::class.java) { dao.add(Room(2, "Double", 80, false)) }
        }

        @Test
        fun deletingARoomFailsWithExceptionAsFeedbackToTheClient() {
            assertThrows(Exception::class.java) { dao.delete(existingRoom) }
        }

        @Test
        fun updatingARoomFailsWithFeedbackToTheClient() {
            val newRoomType = "Double"
            val newPrice = 80
            val newBookingStatus = false
            assertThrows(Exception::class.java) {
                dao.update(Room(existingRoom.id, newRoomType, newPrice, newBookingStatus))
            }
        }

        @Test
        fun retrievingARoomByIdFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception::class.java) { dao.getById(existingRoom.id) }
        }

        @Test
        fun retrievingAllRoomsFailsWithExceptionAsFeedbackToClient() {
            assertThrows(Exception::class.java) { dao.getAll() }
        }
    }

    /**
     * Delete room schema for fresh setup per test.
     *
     * @throws SQLException if any error occurs.
     */
    @AfterEach
    @Throws(SQLException::class)
    fun deleteSchema() {
        DriverManager.getConnection(DB_URL).use { connection ->
            connection.createStatement().use { statement ->
                statement.execute(RoomSchemaSql.DELETE_SCHEMA_SQL)
            }
        }
    }

    @Throws(Exception::class)
    private fun assertRoomCountIs(count: Int) {
        dao.getAll().use { allRooms ->
            assertEquals(count.toLong(), allRooms.count())
        }
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
