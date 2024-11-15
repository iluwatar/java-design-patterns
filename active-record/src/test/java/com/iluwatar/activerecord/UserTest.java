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
package com.iluwatar.activerecord;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

  private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
  private static final String USERNAME = "sa";
  private static final String PASSWORD = "";

  @BeforeAll
  static void setupDatabase() throws SQLException {
    User.initializeTable();
  }

  @BeforeEach
  void clearDatabase() throws SQLException {
    // Clean up table before each test
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
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
    // Create and save a new user
    User user = new User(null, "Bob", "bob@example.com");
    user.save();

    Optional<User> foundUserOpt = User.findById(user.getId());

    assertTrue(foundUserOpt.isPresent(), "User should be found by ID");

    foundUserOpt.ifPresent(foundUser -> {
      assertEquals("Bob", foundUser.getName());
      assertEquals("bob@example.com", foundUser.getEmail());
    });
  }

  @Test
  void testFindAll() throws SQLException {
    User user1 = new User(null, "Charlie", "charlie@example.com");
    User user2 = new User(null, "Diana", "diana@example.com");
    user1.save();
    user2.save();

    List<User> users = User.findAll();
    assertEquals(2, users.size(), "There should be two users in the database");
    assertTrue(users.stream().anyMatch(u -> "Charlie".equals(u.getName())), "User Charlie should exist");
    assertTrue(users.stream().anyMatch(u -> "Diana".equals(u.getName())), "User Diana should exist");
  }

  @Test
  void testUpdateUser() throws SQLException {
    User user = new User(null, "Eve", "eve@example.com");
    user.save();

    user.setName("Eve Updated");
    user.setEmail("eve.updated@example.com");
    user.save();

    Optional<User> updatedUserOpt = User.findById(user.getId());

    assertTrue(updatedUserOpt.isPresent(), "Updated user should be found by ID");

    updatedUserOpt.ifPresent(updatedUser -> {
      assertEquals("Eve Updated", updatedUser.getName());
      assertEquals("eve.updated@example.com", updatedUser.getEmail());
    });
  }

  @Test
  void testDeleteUser() throws SQLException {
    User user = new User(null, "Frank", "frank@example.com");
    user.save();
    Integer userId = user.getId();

    user.delete();

    Optional<User> deletedUserOpt = User.findById(userId);
    assertTrue(deletedUserOpt.isEmpty(), "User should be deleted from the database");
  }
}