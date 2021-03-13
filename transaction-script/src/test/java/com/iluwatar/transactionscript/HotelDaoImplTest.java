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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests {@link HotelDaoImpl}.
 */
public class HotelDaoImplTest {

  private static final String DB_URL = "jdbc:h2:~/test";
  private HotelDaoImpl dao;
  private Room existingRoom = new Room(1, "Single", 50, false);

  /**
   * Creates rooms schema.
   *
   * @throws SQLException if there is any error while creating schema.
   */
  @BeforeEach
  public void createSchema() throws SQLException {
    try (var connection = DriverManager.getConnection(DB_URL);
         var statement = connection.createStatement()) {
      statement.execute(RoomSchemaSql.DELETE_SCHEMA_SQL);
      statement.execute(RoomSchemaSql.CREATE_SCHEMA_SQL);
    }
  }

  /**
   * Represents the scenario where DB connectivity is present.
   */
  @Nested
  public class ConnectionSuccess {

    /**
     * Setup for connection success scenario.
     *
     * @throws Exception if any error occurs.
     */
    @BeforeEach
    public void setUp() throws Exception {
      var dataSource = new JdbcDataSource();
      dataSource.setURL(DB_URL);
      dao = new HotelDaoImpl(dataSource);
      var result = dao.add(existingRoom);
      Assertions.assertTrue(result);
    }

    /**
     * Represents the scenario when DAO operations are being performed on a non existing room.
     */
    @Nested
    public class NonExistingRoom {

      @Test
      void addingShouldResultInSuccess() throws Exception {
        try (var allRooms = dao.getAll()) {
          assumeTrue(allRooms.count() == 1);
        }

        final var nonExistingRoom = new Room(2, "Double", 80, false);
        var result = dao.add(nonExistingRoom);
        Assertions.assertTrue(result);

        assertRoomCountIs(2);
        assertEquals(nonExistingRoom, dao.getById(nonExistingRoom.getId()).get());
      }

      @Test
      void deletionShouldBeFailureAndNotAffectExistingRooms() throws Exception {
        final var nonExistingRoom = new Room(2, "Double", 80, false);
        var result = dao.delete(nonExistingRoom);

        Assertions.assertFalse(result);
        assertRoomCountIs(1);
      }

      @Test
      void updationShouldBeFailureAndNotAffectExistingRooms() throws Exception {
        final var nonExistingId = getNonExistingRoomId();
        final var newRoomType = "Double";
        final var newPrice = 80;
        final var room = new Room(nonExistingId, newRoomType, newPrice, false);
        var result = dao.update(room);

        Assertions.assertFalse(result);
        assertFalse(dao.getById(nonExistingId).isPresent());
      }

      @Test
      void retrieveShouldReturnNoRoom() throws Exception {
        assertFalse(dao.getById(getNonExistingRoomId()).isPresent());
      }
    }

    /**
     * Represents a scenario where DAO operations are being performed on an already existing
     * room.
     */
    @Nested
    public class ExistingRoom {

      @Test
      void addingShouldResultInFailureAndNotAffectExistingRooms() throws Exception {
        var existingRoom = new Room(1, "Single", 50, false);
        var result = dao.add(existingRoom);

        Assertions.assertFalse(result);
        assertRoomCountIs(1);
        assertEquals(existingRoom, dao.getById(existingRoom.getId()).get());
      }

      @Test
      void deletionShouldBeSuccessAndRoomShouldBeNonAccessible() throws Exception {
        var result = dao.delete(existingRoom);

        Assertions.assertTrue(result);
        assertRoomCountIs(0);
        assertFalse(dao.getById(existingRoom.getId()).isPresent());
      }

      @Test
      void updationShouldBeSuccessAndAccessingTheSameRoomShouldReturnUpdatedInformation() throws
          Exception {
        final var newRoomType = "Double";
        final var newPrice = 80;
        final var newBookingStatus = false;
        final var Room = new Room(existingRoom.getId(), newRoomType, newPrice, newBookingStatus);
        var result = dao.update(Room);

        Assertions.assertTrue(result);

        final var room = dao.getById(existingRoom.getId()).get();
        assertEquals(newRoomType, room.getRoomType());
        assertEquals(newPrice, room.getPrice());
        assertEquals(newBookingStatus, room.isBooked());
      }
    }
  }

  /**
   * Represents a scenario where DB connectivity is not present due to network issue, or DB service
   * unavailable.
   */
  @Nested
  public class ConnectivityIssue {

    private static final String EXCEPTION_CAUSE = "Connection not available";

    /**
     * setup a connection failure scenario.
     *
     * @throws SQLException if any error occurs.
     */
    @BeforeEach
    public void setUp() throws SQLException {
      dao = new HotelDaoImpl(mockedDatasource());
    }

    private DataSource mockedDatasource() throws SQLException {
      var mockedDataSource = mock(DataSource.class);
      var mockedConnection = mock(Connection.class);
      var exception = new SQLException(EXCEPTION_CAUSE);
      doThrow(exception).when(mockedConnection).prepareStatement(Mockito.anyString());
      doReturn(mockedConnection).when(mockedDataSource).getConnection();
      return mockedDataSource;
    }

    @Test
    void addingARoomFailsWithExceptionAsFeedbackToClient() {
      assertThrows(Exception.class, () -> {
        dao.add(new Room(2, "Double", 80, false));
      });
    }

    @Test
    void deletingARoomFailsWithExceptionAsFeedbackToTheClient() {
      assertThrows(Exception.class, () -> {
        dao.delete(existingRoom);
      });
    }

    @Test
    void updatingARoomFailsWithFeedbackToTheClient() {
      final var newRoomType = "Double";
      final var newPrice = 80;
      final var newBookingStatus = false;
      assertThrows(Exception.class, () -> {
        dao.update(new Room(existingRoom.getId(), newRoomType, newPrice, newBookingStatus));
      });
    }

    @Test
    void retrievingARoomByIdFailsWithExceptionAsFeedbackToClient() {
      assertThrows(Exception.class, () -> {
        dao.getById(existingRoom.getId());
      });
    }

    @Test
    void retrievingAllRoomsFailsWithExceptionAsFeedbackToClient() {
      assertThrows(Exception.class, () -> {
        dao.getAll();
      });
    }

  }

  /**
   * Delete room schema for fresh setup per test.
   *
   * @throws SQLException if any error occurs.
   */
  @AfterEach
  public void deleteSchema() throws SQLException {
    try (var connection = DriverManager.getConnection(DB_URL);
         var statement = connection.createStatement()) {
      statement.execute(RoomSchemaSql.DELETE_SCHEMA_SQL);
    }
  }

  private void assertRoomCountIs(int count) throws Exception {
    try (var allRooms = dao.getAll()) {
      assertEquals(count, allRooms.count());
    }
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
