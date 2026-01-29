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

// ABOUTME: Tests the Room data class for proper getter/setter behavior and equality.
// ABOUTME: Verifies property access, equals, hashCode, and toString implementations.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Tests [Room].
 */
class RoomTest {

    private lateinit var room: Room

    companion object {
        private const val ID = 1
        private const val ROOMTYPE = "Single"
        private const val PRICE = 50
        private const val BOOKED = false
    }

    @BeforeEach
    fun setUp() {
        room = Room(ID, ROOMTYPE, PRICE, BOOKED)
    }

    @Test
    fun getAndSetId() {
        val newId = 2
        room.id = newId
        assertEquals(newId, room.id)
    }

    @Test
    fun getAndSetRoomType() {
        val newRoomType = "Double"
        room.roomType = newRoomType
        assertEquals(newRoomType, room.roomType)
    }

    @Test
    fun getAndSetLastName() {
        val newPrice = 60
        room.price = newPrice
        assertEquals(newPrice, room.price)
    }

    @Test
    fun notEqualWithDifferentId() {
        val newId = 2
        val otherRoom = Room(newId, ROOMTYPE, PRICE, BOOKED)
        assertNotEquals(room, otherRoom)
        assertNotEquals(room.hashCode(), otherRoom.hashCode())
    }

    @Test
    fun equalsWithSameObjectValues() {
        val otherRoom = Room(ID, ROOMTYPE, PRICE, BOOKED)
        assertEquals(room, otherRoom)
        assertEquals(room.hashCode(), otherRoom.hashCode())
    }

    @Test
    fun equalsWithSameObjects() {
        assertEquals(room, room)
        assertEquals(room.hashCode(), room.hashCode())
    }

    @Test
    fun testToString() {
        assertEquals(
            String.format(
                "Room(id=%s, roomType=%s, price=%s, booked=%s)",
                room.id, room.roomType, room.price, room.isBooked
            ),
            room.toString()
        )
    }
}
