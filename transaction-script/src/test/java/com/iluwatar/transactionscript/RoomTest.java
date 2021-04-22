/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.transactionscript;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Room}.
 */
class RoomTest {

  private Room room;
  private static final int ID = 1;
  private static final String ROOMTYPE = "Single";
  private static final int PRICE = 50;
  private static final boolean BOOKED = false;

  @BeforeEach
  void setUp() {
    room = new Room(ID, ROOMTYPE, PRICE, BOOKED);
  }

  @Test
  void getAndSetId() {
    final var newId = 2;
    room.setId(newId);
    assertEquals(newId, room.getId());
  }

  @Test
  void getAndSetRoomType() {
    final var newRoomType = "Double";
    room.setRoomType(newRoomType);
    assertEquals(newRoomType, room.getRoomType());
  }

  @Test
  void getAndSetLastName() {
    final var newPrice = 60;
    room.setPrice(newPrice);
    assertEquals(newPrice, room.getPrice());
  }

  @Test
  void notEqualWithDifferentId() {
    final var newId = 2;
    final var otherRoom = new Room(newId, ROOMTYPE, PRICE, BOOKED);
    assertNotEquals(room, otherRoom);
    assertNotEquals(room.hashCode(), otherRoom.hashCode());
  }

  @Test
  void equalsWithSameObjectValues() {
    final var otherRoom = new Room(ID, ROOMTYPE, PRICE, BOOKED);
    assertEquals(room, otherRoom);
    assertEquals(room.hashCode(), otherRoom.hashCode());
  }

  @Test
  void equalsWithSameObjects() {
    assertEquals(room, room);
    assertEquals(room.hashCode(), room.hashCode());
  }

  @Test
  void testToString() {
    assertEquals(String.format("Room(id=%s, roomType=%s, price=%s, booked=%s)",
        room.getId(), room.getRoomType(), room.getPrice(), room.isBooked()), room.toString());
  }
}
