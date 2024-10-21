package com.iluwatar.activerecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {
  User testUser1 = new User(
      null,
      "Test1 Name",
      "test1@test.com"
  );
  User testUser2 = new User(
      1,
      "Test2 Name",
      "test2@test.com"

  );
  User testUser3 = new User(
      null,
      "Test3 Name",
      "test3@test.com"
  );

  private final String sqlStatement = "" +
      "CREATE TABLE IF NOT EXISTS usersTEST (" +
      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
      "name TEXT NOT NULL, email TEXT NOT NULL)";

  /**
   * Setup of application test includes initializing DB connection.
   */
  @BeforeEach
  public void setup() {
    assertDoesNotThrow(() -> {
      Connection conn = ActiveRecord.getConnection();
      assertNotNull(conn);
      try (Statement stmt = conn.createStatement()) {
        stmt.execute(sqlStatement);
      }
    });
  }

  @Test
  void testInsertUser(){

  }

  @Test
  void testUpdateUser() {

  }

  @Test
  void testDeleteUser() {

  }

  @Test
  void testFindUserById() {

  }
}
