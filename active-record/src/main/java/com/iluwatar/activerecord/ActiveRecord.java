package com.iluwatar.activerecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

public abstract class ActiveRecord {
  // Database connection configuration
  private static final String DB_URL = "jdbc:sqlite:my_database.db";
  private static final Logger log = LoggerFactory.getLogger(ActiveRecord.class);

  protected abstract String getTableName();
  protected abstract String getPrimaryKey();

  protected static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL);
  }

  //Create table
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
        } else {
          throw new Exception("No record found with id: " + id);
        }
      } catch (SQLException e) {
        throw new RuntimeException("Database exception occurred while finding record with id: " + id, e);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  // Save the current record (insert or update)
  public String save() {
    try (Connection conn = getConnection()) {
      if (isNewRecord()) {
        insert(conn);
        return "Inserted new entry successfully";
      } else {
        update(conn);
        return "Updated existing entry successfully";

      }
    } catch (SQLException e) {
      throw new RuntimeException("Database exception occurred while saving record", e);
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
      throw new RuntimeException("Database exception occurred while deleting record", e);

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
