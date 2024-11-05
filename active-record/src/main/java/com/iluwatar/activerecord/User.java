package com.iluwatar.activerecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class User {
  private static final String DB_URL = "jdbc:sqlite:database.db";

  private Integer id;
  private String name;
  private String email;

  public User() { }

  public User(Integer id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  // Establish a database connection
  private static Connection connect() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }

  // Initialize the table (if not exists)
  public static void initializeTable() throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT)";
    try (Connection conn = connect();
         Statement stmt = conn.createStatement()) {
      stmt.execute(sql);
    }
  }

  // Insert a new record into the database
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
      if (this.id != null) pstmt.setInt(3, this.id);
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

  // Find a user by ID
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

  // Get all users
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

  // Delete the user from the database
  public void delete() throws SQLException {
    if (this.id == null) return;
    String sql = "DELETE FROM users WHERE id = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, this.id);
      pstmt.executeUpdate();
      this.id = null;
    }
  }

  // Getters and Setters
  public Integer getId() { return id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
}
