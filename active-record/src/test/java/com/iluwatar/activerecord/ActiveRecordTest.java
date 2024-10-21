package com.iluwatar.activerecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ActiveRecordTest {

  User testUser1 = new User(
      null,
      "Test1 Name",
      "test1@test.com",
      "usersTEST"
  );
  User testUser2 = new User(
      1,
      "Test2 Name",
      "test2@test.com",
      "usersTEST"

  );
  User testUser3 = new User(
      null,
      "Test3 Name",
      "test3@test.com",
      "usersTEST"
  );

  private final String sqlStatement = "" +
      "CREATE TABLE IF NOT EXISTS usersTEST (" +
      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
      "name TEXT NOT NULL, email TEXT NOT NULL)";


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
  public void testSaveUser() {
    assertEquals(testUser1.save(), ("Inserted new entry successfully"));
    assertEquals(testUser3.save(), ("Inserted new entry successfully"));
    assertEquals(testUser2.save(), ("Updated existing entry successfully"));
  }


//  TODO: Fix
  @Test
  public void testFindUserByID(){
    System.out.println(testUser2.getTableName());
    testUser2.save();
      User foundUser = ActiveRecord.find(User.class, 1);
    System.out.println(foundUser.toString());
      assertNotNull(foundUser, "User should not be null when found by ID 1");
  }

  @Test
  public void testDeleteUser() {
    assertDoesNotThrow(testUser2::delete);
  }
}
