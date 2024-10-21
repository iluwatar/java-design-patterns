package com.iluwatar.activerecord;

import java.sql.*;

public class User extends ActiveRecord {
  private Integer id;
  private String name;
  private String email;
  private String tableName;

  public User() {
  }
  public User(Integer id, String name, String email){
    this.id = id;
    this.name = name;
    this.email = email;
    this.tableName = "users";
  }

  public User(Integer id, String name, String email, String tableName){
    this.id = id;
    this.name = name;
    this.email = email;
    this.tableName = tableName;
  }

  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  @Override
  protected String getTableName() {
    return this.tableName;
  }

  @Override
  protected String getPrimaryKey() {
    return "id";
  }

  @Override
  protected void insert(Connection conn) throws SQLException {
    String sql = "INSERT INTO ? (name, email) VALUES (?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(
        sql, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, tableName);
      stmt.setString(2, name);
      stmt.setString(3, email);
      stmt.executeUpdate();
      ResultSet generatedKeys = stmt.getGeneratedKeys();
      if (generatedKeys.next()) {
        this.id = generatedKeys.getInt(1);
      }
    }
  }

  @Override
  protected void update(Connection conn) throws SQLException {
    String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, name);
      stmt.setString(2, email);
      stmt.setInt(3, id);
      stmt.executeUpdate();
    }
  }

  @Override
  protected boolean isNewRecord() {
    return this.id == null;
  }

  @Override
  protected void loadFromResultSet(ResultSet rs) throws SQLException {
    this.id = rs.getInt("id");
    this.name = rs.getString("name");
    this.email = rs.getString("email");

  }

  @Override
  protected int getId() {
    return this.id;
  }


  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public static void createTable() {
    String sql = "CREATE TABLE IF NOT EXISTS users (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "name TEXT NOT NULL, " +
        "email TEXT NOT NULL)";

    // Call the method from the ActiveRecord base class
    new User().createTable(sql);
  }
}
