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
package com.iluwatar.transactionscript;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hotel}
 */
class HotelTest {

  private static final String H2_DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

  private Hotel hotel;
  private HotelDaoImpl dao;

  @BeforeEach
  void setUp() throws Exception {
    final var dataSource = createDataSource();
    deleteSchema(dataSource);
    createSchema(dataSource);
    dao = new HotelDaoImpl(dataSource);
    addRooms(dao);
    hotel = new Hotel(dao);

  }

  @Test
  void bookingRoomShouldChangeBookedStatusToTrue() throws Exception {
    hotel.bookRoom(1);
    assertTrue(dao.getById(1).isPresent());
    assertTrue(dao.getById(1).get().isBooked());
  }

  @Test
  void bookingRoomWithInvalidIdShouldRaiseException() {
    assertThrows(Exception.class, () -> hotel.bookRoom(getNonExistingRoomId()));
  }


  @Test
  @SneakyThrows
  void bookingRoomAgainShouldRaiseException() {
    hotel.bookRoom(1);
    assertThrows(Exception.class, () -> hotel.bookRoom(1), "Room already booked!");
  }

  @Test
  void NotBookingRoomShouldNotChangeBookedStatus() throws Exception {
    assertTrue(dao.getById(1).isPresent());
    assertFalse(dao.getById(1).get().isBooked());
  }

  @Test
  void cancelRoomBookingShouldChangeBookedStatus() throws Exception {
    hotel.bookRoom(1);
    assertTrue(dao.getById(1).isPresent());
    assertTrue(dao.getById(1).get().isBooked());

    hotel.cancelRoomBooking(1);
    assertTrue(dao.getById(1).isPresent());
    assertFalse(dao.getById(1).get().isBooked());
  }

  @Test
  void cancelRoomBookingWithInvalidIdShouldRaiseException() {
    assertThrows(Exception.class, () -> hotel.cancelRoomBooking(getNonExistingRoomId()));
  }

  @Test
  void cancelRoomBookingForUnbookedRoomShouldRaiseException() {
    assertThrows(Exception.class, () -> hotel.cancelRoomBooking(1));
  }


  private static void deleteSchema(DataSource dataSource) throws java.sql.SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(RoomSchemaSql.DELETE_SCHEMA_SQL);
    }
  }

  private static void createSchema(DataSource dataSource) throws Exception {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(RoomSchemaSql.CREATE_SCHEMA_SQL);
    } catch (Exception e) {
      throw new Exception(e.getMessage(), e);
    }
  }

  public static DataSource createDataSource() {
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setUrl(H2_DB_URL);
    return dataSource;
  }

  private static void addRooms(HotelDaoImpl hotelDao) throws Exception {
    for (var room : generateSampleRooms()) {
      hotelDao.add(room);
    }
  }

  public static List<Room> generateSampleRooms() {
    final var room1 = new Room(1, "Single", 50, false);
    final var room2 = new Room(2, "Double", 80, false);
    final var room3 = new Room(3, "Queen", 120, false);
    final var room4 = new Room(4, "King", 150, false);
    final var room5 = new Room(5, "Single", 50, false);
    final var room6 = new Room(6, "Double", 80, false);
    return List.of(room1, room2, room3, room4, room5, room6);
  }

  /**
   * An arbitrary number which does not correspond to an active Room id.
   *
   * @return an int of a room id which doesn't exist
   */
  private int getNonExistingRoomId() {
    return 999;
  }
}