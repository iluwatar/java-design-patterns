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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of active record pattern.
 */


public class User {
  /**
   * DB_URL.
   */

  private static final String DB_URL = "jdbc:sqlite:database.db";

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
   */
  public User(Integer id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  /**
   * Establish a database connection.
   */

  private static Connection connect() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }


  /**
   * Initialize the table (if not exists).
   */

  public static void initializeTable() throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT)";
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
         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
   */

  public static User findById(int id) throws SQLException {
    String sql = "SELECT * FROM users WHERE id = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
      }
    }
    return null;
  }
  /**
   * Get all users.
   */

  public static List<User> findAll() throws SQLException {
    String sql = "SELECT * FROM users";
    List<User> users = new ArrayList<>();
    try (Connection conn = connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
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
   * Getters and setters.
   */
  public Integer getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
}
