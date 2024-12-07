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
package com.iluwatar.rowdata;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Main application to interact with the database.
 */
public class App {
  private static final Logger logger = LoggerFactory.getLogger(App.class);
  private static Connection connection;

  /**
   * Initializes the database connection.
   */
  public static void initialize() {
    try {
      connection = DriverManager.getConnection("jdbc:sqlite:/path/to/sample.db");
    } catch (SQLException e) {
      throw new DatabaseOperationException("Failed to initialize the database connection", e);
    }
  }

  /**
   * Returns the active database connection.
   *
   * @return the active connection
   */
  public static Connection getConnection() {
    if (connection == null) {
      throw new IllegalStateException("Connection is not initialized. Call initialize() first.");
    }
    return connection;
  }

  /**
   * Closes the database connection.
   */
  public static void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        throw new DatabaseOperationException("Failed to close the database connection", e);
      }
    }
  }

  /**
   * Creates the rowDataTable if it doesn't exist.
   */
  public static void createTable() {
    String sql = "CREATE TABLE IF NOT EXISTS rowDataTable "
        + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "NAME TEXT NOT NULL, "
        + "VALUE INTEGER NOT NULL)";
    try (Statement stmt = connection.createStatement()) {
      stmt.executeUpdate(sql);
      logger.info("Table created or already exists.");
    } catch (SQLException e) {
      logger.error("Error creating table: {}", e.getMessage());
    }
  }

  /**
   * Displays the content of the rowDataTable.
   */
  public static void display() {
    String sql = "SELECT ID, NAME FROM rowDataTable";
    try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
      while (rs.next()) {
        int id = rs.getInt("ID");
        String name = rs.getString("NAME");
        int value = rs.getInt("VALUE");
        if (logger.isInfoEnabled()) {
          logger.info("ID = {}, NAME = {}, VALUE = {}", id, name, value);
        }
      }
    } catch (SQLException e) {
      logger.error("Error displaying table: {}", e.getMessage());
    }
  }
  /**
   * Starting point for the program.
   * @param args command line args
   * @throws SQLException if any error occur, since SQL code is necessary in {@link UserGateway}
   */
  public static void main(String[] args) {
    initialize();
    createTable();

    RowData row1 = new RowData(1, "John", 20);
    RowData row2 = new RowData(2, "Mary", 30);
    RowData row3 = new RowData(3, "Doe", 40);

    UserGateway rowGateway1 = new UserGateway(row1, connection);
    UserGateway rowGateway2 = new UserGateway(row2, connection);
    UserGateway rowGateway3 = new UserGateway(row3, connection);

    rowGateway1.insert();
    rowGateway2.insert();
    rowGateway3.insert();
    display();

    row3.setName("Dorothy");
    rowGateway3.setRowData(row3);
    rowGateway3.update();
    display();

    rowGateway2.delete();
    display();

    closeConnection();
  }
}
