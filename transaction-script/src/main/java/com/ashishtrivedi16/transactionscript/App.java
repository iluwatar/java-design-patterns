/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.ashishtrivedi16.transactionscript;

import java.util.List;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

  private static final String H2_DB_URL = "jdbc:h2:~/test";
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionScriptApp.class);

  /**
   * Program entry point.
   *
   * @param args command line arguments
   * @throws Exception if any error occurs
   */
  public static void main(String[] args) throws Exception {

    final var dataSource = createDataSource();
    deleteSchema(dataSource);
    createSchema(dataSource);
    final var dao = new HotelDaoImpl(dataSource);

    addRooms(dao);

    getRoomStatus(dao);
  
    var hotel = new Hotel(dao);

    hotel.bookRoom(1);
    hotel.bookRoom(2);
    hotel.bookRoom(3);
    hotel.bookRoom(4);
    hotel.bookRoom(5);
    hotel.bookRoom(6);

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
      throw new SqlException(e.getMessage(), e);
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
