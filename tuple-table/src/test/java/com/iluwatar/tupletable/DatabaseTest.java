package com.iluwatar.tupletable;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    Connection con = null;
    PreparedStatement ps = null;
    Database db = new Database();
    con = db.getConnection();
    assertNotNull(con);
    db.closeConnection(con);
    db.closeStatement(ps);
  }
}
