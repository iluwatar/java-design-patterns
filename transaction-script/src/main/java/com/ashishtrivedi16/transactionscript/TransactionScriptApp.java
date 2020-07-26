package com.ashishtrivedi16.transactionscript;

import com.ashishtrivedi16.transactionscript.db.CustomException;
import com.ashishtrivedi16.transactionscript.db.HotelDaoImpl;
import com.ashishtrivedi16.transactionscript.db.RoomSchemaSql;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.h2.jdbc.JdbcSQLException;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionScriptApp {

  private static final String H2_DB_URL = "jdbc:h2:~/test";
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionScriptApp.class);
  private static final String ALL_ROOMS = "customerDao.getAllRooms(): ";

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

    Hotel hotel = new Hotel(dao);

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

  private static void deleteSchema(DataSource dataSource) throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(RoomSchemaSql.DELETE_SCHEMA_SQL);
    }
  }

  private static void createSchema(DataSource dataSource) throws Exception {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(RoomSchemaSql.CREATE_SCHEMA_SQL);
    } catch (JdbcSQLException e) {
      throw new CustomException(e.getMessage(), e);
    }
  }

  /**
   * Get database.
   *
   * @return h2 datasource
   */
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

  /**
   * Generate rooms.
   *
   * @return list of rooms
   */
  public static List<Room> generateSampleRooms() {
    final var room1 = new Room(1, "Single", 50, false);
    final var room2 = new Room(2, "Double", 80, false);
    final var room3 = new Room(3, "Queen", 120, false);
    final var room4 = new Room(4, "King", 150, false);
    final var room5 = new Room(5, "Single", 50, false);
    final var room6 = new Room(6, "Double", 80, false);
    return List.of(room1, room2, room3, room4, room5, room6);
  }
}
