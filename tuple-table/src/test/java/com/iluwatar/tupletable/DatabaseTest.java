package com.iluwatar.tupletable;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseTest {

  /**
   * Test for public methods for MemberTupleDAO
   * @throws SQLException for database connection and closing
   */

  @Test
  void testDBConnectionAndClose() throws SQLException, ClassNotFoundException {
    Database db = new Database();
    try (Connection con = db.getConnection()) {
      assertNotNull(con);
    }
  }
}
