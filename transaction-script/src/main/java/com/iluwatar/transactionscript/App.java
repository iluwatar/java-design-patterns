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

import java.util.List;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Transaction Script (TS) is one of the simplest domain logic pattern.
 * It needs less work to implement than other domain logic patterns and therefore
 * it’s perfect fit for smaller applications that don't need big architecture behind them.
 *
 * <p>In this example we will use the TS pattern to implement booking and cancellation
 * methods for a Hotel management App. The main method will initialise an instance of
 * {@link Hotel} and add rooms to it. After that it will book and cancel a couple of rooms
 * and that will be printed by the logger.</p>
 *
 * <p>The thing we have to note here is that all the operations related to booking or cancelling
 * a room like checking the database if the room exists, checking the booking status or the
 * room, calculating refund price are all clubbed inside a single transaction script method.</p>
 */
public class App {

  private static final String H2_DB_URL = "jdbc:h2:~/test";
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   * Initialises an instance of Hotel and adds rooms to it.
   * Carries out booking and cancel booking transactions.
   * @param args command line arguments
   * @throws Exception if any error occurs
   */
  public static void main(String[] args) throws Exception {

    final var dataSource = createDataSource();
    deleteSchema(dataSource);
    createSchema(dataSource);
    final var dao = new HotelDaoImpl(dataSource);

    // Add rooms
    addRooms(dao);

    // Print room booking status
    getRoomStatus(dao);

    var hotel = new Hotel(dao);

    // Book rooms
    hotel.bookRoom(1);
    hotel.bookRoom(2);
    hotel.bookRoom(3);
    hotel.bookRoom(4);
    hotel.bookRoom(5);
    hotel.bookRoom(6);

    // Cancel booking for a few rooms
    hotel.cancelRoomBooking(1);
    hotel.cancelRoomBooking(3);
    hotel.cancelRoomBooking(5);

    getRoomStatus(dao);

    deleteSchema(dataSource);

  }

  private static void getRoomStatus(HotelDaoImpl dao) throws Exception {
    try (var customerStream = dao.getAll()) {
      customerStream.forEach((customer) -> LOGGER.info(customer.toString()));
    }
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

  /**
   * Get database.
   *
   * @return h2 datasource
   */
  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setUrl(H2_DB_URL);
    return dataSource;
  }

  private static void addRooms(HotelDaoImpl hotelDao) throws Exception {
    for (var room : generateSampleRooms()) {
      hotelDao.add(room);
    }
  }

  /**
   * Generate rooms.
   *
   * @return list of rooms
   */
  private static List<Room> generateSampleRooms() {
    final var room1 = new Room(1, "Single", 50, false);
    final var room2 = new Room(2, "Double", 80, false);
    final var room3 = new Room(3, "Queen", 120, false);
    final var room4 = new Room(4, "King", 150, false);
    final var room5 = new Room(5, "Single", 50, false);
    final var room6 = new Room(6, "Double", 80, false);
    return List.of(room1, room2, room3, room4, room5, room6);
  }
}
