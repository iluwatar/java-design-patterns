package com.iluwatar.activerecord;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  @BeforeAll
  static void setupDatabase() throws SQLException {
    User.initializeTable();
  }

  @BeforeEach
  void clearDatabase() throws SQLException {
    // Clean up table before each test
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:database.db");
         Statement stmt = conn.createStatement()) {
      stmt.execute("DELETE FROM users");
    }
  }

  @Test
  void testSaveNewUser() throws SQLException {
    User user = new User(null, "Alice", "alice@example.com");
    user.save();
    assertNotNull(user.getId(), "User ID should be generated upon saving");
  }

  @Test
  void testFindById() throws SQLException {
    User user = new User(null, "Bob", "bob@example.com");
    user.save();

    User foundUser = User.findById(user.getId());
    assertNotNull(foundUser, "User should be found by ID");
    assertEquals("Bob", foundUser.getName());
    assertEquals("bob@example.com", foundUser.getEmail());
  }

  @Test
  void testFindAll() throws SQLException {
    User user1 = new User(null, "Charlie", "charlie@example.com");
    User user2 = new User(null, "Diana", "diana@example.com");
    user1.save();
    user2.save();

    List<User> users = User.findAll();
    assertEquals(2, users.size(), "There should be two users in the database");
  }

  @Test
  void testUpdateUser() throws SQLException {
    User user = new User(null, "Eve", "eve@example.com");
    user.save();

    user.setName("Eve Updated");
    user.setEmail("eve.updated@example.com");
    user.save();

    User updatedUser = User.findById(user.getId());
    assert updatedUser != null;
    assertEquals("Eve Updated", updatedUser.getName());
    assertEquals("eve.updated@example.com", updatedUser.getEmail());
  }

  @Test
  void testDeleteUser() throws SQLException {
    User user = new User(null, "Frank", "frank@example.com");
    user.save();
    Integer userId = user.getId();

    user.delete();
    assertNull(User.findById(userId), "User should be deleted from the database");
  }
}
