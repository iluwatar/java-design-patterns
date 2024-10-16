package com.iluwatar;

import java.sql.*;

public abstract class ActiveRecord {
  // Database connection configuration
  private static final String DB_URL = "jdbc:sqlite:my_database.db";

  protected abstract String getTableName();
  protected abstract String getPrimaryKey();

  protected Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }

  //We are creating a table using SQLlite
  protected void createTable(String createTableSQL) {
    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement()) {
      stmt.execute(createTableSQL);
      System.out.println("Table created or already exists.");
    } catch (SQLException e) {
      System.out.println("Failed to create table: " + e.getMessage());
    }
  }

  // Find record by ID
  public static <T extends ActiveRecord> T find(Class<T> clazz, int id) {
    T instance;
    try {
      instance = clazz.getDeclaredConstructor().newInstance();
      String sql = "SELECT * FROM " + instance.getTableName() +
          " WHERE " + instance.getPrimaryKey() + " = ?";
      try (Connection conn = instance.getConnection();
           PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
          instance.loadFromResultSet(rs);
          return instance;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  // Save the current record (insert or update)
  public void save() {
    try (Connection conn = getConnection()) {
      if (isNewRecord()) {
        insert(conn);
      } else {
        update(conn);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Delete the current record
  public void delete() {
    String sql = "DELETE FROM " + getTableName() + " WHERE " + getPrimaryKey() + " = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Insert a new record into the database
  protected abstract void insert(Connection conn) throws SQLException;

  // Update an existing record
  protected abstract void update(Connection conn) throws SQLException;

  // Check if this is a new record (i.e., the primary key is null)
  protected abstract boolean isNewRecord();

  // Load an object's fields from a result set
  protected abstract void loadFromResultSet(ResultSet rs) throws SQLException;

  // Get the primary key value
  protected abstract int getId();
}
