/*
 * This project is licensed under the MIT license. Module model-view-viewmodel
 * is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of active record pattern.
 */

@Slf4j
public class User {

  /**
   * Credentials for in-memory H2 database.
   */
  private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

  /**
   * Database username.
   */
  private static final String USERNAME = "sa";

  /**
   * Database password.
   */
  private static final String PASSWORD = "";

  /**
   * User ID.
   */
  private Integer id;

  /**
   * User name.
   */
  private String name;

  /**
   * User email.
   */
  private String email;

  /**
   * User constructor.
   *
   * @param userId    the unique identifier of the user
   * @param userName  the name of the user
   * @param userEmail the email address of the user
   */
  public User(
      final Integer userId,
      final String userName,
      final String userEmail) {
    this.id = userId;
    this.name = userName;
    this.email = userEmail;
  }

  /**
   * Establish a database connection.
   *
   * @return a {@link Connection} object to interact with the database
   * @throws SQLException if a database access error occurs
   */

  private static Connection connect() throws SQLException {
    return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
  }


  /**
   * Initialize the table (required each time program runs
   * as we are using an in-memory DB solution).
   */

  public static void initializeTable() throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS users (\n"
        + "    id INTEGER PRIMARY KEY AUTO_INCREMENT,\n"
        + "    name VARCHAR(255),\n"
        + "    email VARCHAR(255)\n"
        + ");";
    try (Connection conn = connect();
         Statement stmt = conn.createStatement()) {
      stmt.execute(sql);
    }
  }

  /**
   * Insert a new record into the database.
   */

  public void save() throws SQLException {
    String sql;
    if (this.id == null) { // New record
      sql = "INSERT INTO users(name, email) VALUES(?, ?)";
    } else { // Update existing record
      sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
    }
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(
             sql, Statement.RETURN_GENERATED_KEYS)) {
      pstmt.setString(1, this.name);
      pstmt.setString(2, this.email);
      if (this.id != null) {
        pstmt.setInt(3, this.id);
      }
      pstmt.executeUpdate();
      if (this.id == null) {
        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            this.id = generatedKeys.getInt(1);
          }
        }
      }
    }
  }

  /**
   * Find a user by ID.
   *
   * @param id userID
   * @return the found user if a user is found, or an empty {@link Optional}
   *     if no user is found or an exception occurs
   */

  public static Optional<User> findById(final int id) {
    String sql = "SELECT * FROM users WHERE id = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        return Optional.of(new User(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email")));
      }
    } catch (SQLException e) {
      LOGGER.error("SQL error: {}", e.getMessage(), e);
    }
    return Optional.empty();
  }

  /**
   * Get all users.
   *
   * @return all users from the database;
   */

  public static List<User> findAll() throws SQLException {
    String sql = "SELECT * FROM users";
    List<User> users = new ArrayList<>();
    try (Connection conn = connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        users.add(new User(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email")));
      }
    }
    return users;
  }

  /**
   * Delete the user from the database.
   */

  public void delete() throws SQLException {
    if (this.id == null) {
      return;
    }

    String sql = "DELETE FROM users WHERE id = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, this.id);
      pstmt.executeUpdate();
      this.id = null;
    }
  }

  /**
   * Gets the ID of the user.
   *
   * @return the unique identifier of the user,
   *     or null if the user is not yet saved to the database
   */
  public Integer getId() {
    return id;
  }

  /**
   * Gets the name of the user.
   *
   * @return the name of the user
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the user.
   *
   * @param userName the name to set for the user
   */
  public void setName(final String userName) {
    this.name = name;
  }

  /**
   * Gets the email address of the user.
   *
   * @return the email address of the user
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email address of the user.
   *
   * @param userEmail the email address to set for the user
   */
  public void setEmail(final String userEmail) {
    this.email = email;
  }
}
