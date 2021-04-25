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

package com.iluwatar.tablemodule;

import lombok.extern.slf4j.Slf4j;

/**
 * This class organizes domain logic with the room table in the
 * database. A single instance of this class contains the various
 * procedures that will act on the data.
 */
@Slf4j
public class RoomTableModule {

  private final RoomDao roomDao;

  /**
   * Public constructor of the class RoomTableModule.
   *
   * @param roomDaoImpl deal with data source layer
   */
  public RoomTableModule(final RoomDaoImpl roomDaoImpl) {
    this.roomDao = roomDaoImpl;
  }

  /**
   * Book a room.
   *
   * @param roomNumber room to book
   * @throws Exception if any error
   */
  public void bookRoom(final int roomNumber) throws Exception {

    var room = roomDao.getById(roomNumber);

    if (room.isEmpty()) {
      throw new Exception("Room number: " + roomNumber + " does not exist");
    } else {
      if (room.get().isBooked()) {
        throw new Exception("Room already booked!");
      } else {
        var updateRoomBooking = room.get();
        updateRoomBooking.setBooked(true);
        roomDao.update(updateRoomBooking);
      }
    }
  }

  /**
   * Cancel a room booking.
   *
   * @param roomNumber room to cancel booking
   * @throws Exception if any error
   */
  public void cancelRoomBooking(final int roomNumber) throws Exception {

    var room = roomDao.getById(roomNumber);

    if (room.isEmpty()) {
      throw new Exception("Room number: " + roomNumber + " does not exist");
    } else {
      if (room.get().isBooked()) {
        var updateRoomBooking = room.get();
        updateRoomBooking.setBooked(false);
        int refundAmount = updateRoomBooking.getPrice();
        roomDao.update(updateRoomBooking);

        LOGGER.info("Booking cancelled for room number: " + roomNumber);
        LOGGER.info(refundAmount + " is refunded");
      } else {
        throw new Exception("No booking for the room exists");
      }
    }
  }
}
