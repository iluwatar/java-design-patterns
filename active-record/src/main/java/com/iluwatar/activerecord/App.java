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
import java.sql.SQLException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * The Active Record pattern is an architectural pattern that simplifies
 * database interactions by encapsulating database access logic within
 * the domain model. This pattern allows objects to be responsible for
 * their own persistence, providing methods for CRUD operations directly
 * within the model class.
 *
 * <p>In this example, we demonstrate the Active Record pattern
 * by creating, updating, retrieving, and deleting user records in
 * a SQLite database. The User class contains methods to perform
 * these operations, ensuring that the database interactions are
 * straightforward and intuitive.
 */

@Slf4j
public final class App {

  private App() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Entry Point.
   *
   * @param args the command line arguments - not used
   */
  public static void main(final String[] args) {
    try {
      // Initialize the database and create the users table if it doesn't exist
      User.initializeTable();
      LOGGER.info("Database and table initialized.");

      // Create a new user and save it to the database
      User user1 = new User(null, "John Doe", "john.doe@example.com");
      user1.save();
      LOGGER.info("New user saved: {} with ID {}", user1.getName(), user1.getId());

      // Retrieve and display the user by ID
      User foundUser = User.findById(user1.getId());
      if (foundUser != null) {
        LOGGER.info("User found: {} with email {}", foundUser.getName(), foundUser.getEmail());
      } else {
        LOGGER.info("User not found.");
      }

      // Update the user’s details
      assert foundUser != null;
      foundUser.setName("John Updated");
      foundUser.setEmail("john.updated@example.com");
      foundUser.save();
      LOGGER.info("User updated: {} with email {}", foundUser.getName(), foundUser.getEmail());

      // Retrieve all users
      List<User> users = User.findAll();
      LOGGER.info("All users in the database:");
      for (User user : users) {
        LOGGER.info("ID: {}, Name: {}, Email: {}", user.getId(), user.getName(), user.getEmail());
      }

      // Delete the user
      try {
        LOGGER.info("Deleting user with ID: {}", foundUser.getId());
        foundUser.delete();
        LOGGER.info("User successfully deleted!");
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }

    } catch (SQLException e) {
      LOGGER.error("SQL error: {}", e.getMessage(), e);
    }
  }
}