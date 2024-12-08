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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * This class is responsible for performing CRUD operations on RowData.
 * It provides methods to insert, update, and delete rows in the rowDataTable.
 */
@Getter
@Setter
public class UserGateway {

  private static final Logger logger = LoggerFactory.getLogger(UserGateway.class);

  @Getter
  @Setter
  private RowData rowData;

  private Connection connection;

  /**
   * Constructs a UserGateway with a RowData object and a database connection.
   *
   * @param rowData    the RowData object to be operated on.
   * @param connection the database connection to interact with.
   */
  public UserGateway(RowData rowData, Connection connection) {
    this.rowData = rowData;
    this.connection = connection;
  }

  /**
   * Reads a row from the database by ID.
   *
   * @return the retrieved RowData object, or null if no matching row is found.
   */
  public Optional<RowData> read() {
    String sql = "SELECT ID, NAME, VALUE FROM rowDataTable WHERE ID = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, rowData.getId());
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          int id = rs.getInt("ID");
          String name = rs.getString("NAME");
          int value = rs.getInt("VALUE");
          logger.info("Row retrieved: ID={}, NAME={}, VALUE={}", id, name, value);
          return Optional.of(new RowData(id, name, value));
        }
      }
    } catch (SQLException e) {
      logger.error("Error reading row: {}", e.getMessage());
    }
    return Optional.empty();
  }


  /**
   * Inserts the current RowData object into the rowDataTable.
   * Logs the SQL query upon successful insertion.
   */
  public void insert() {
    String sql = "INSERT INTO rowDataTable (NAME, VALUE) VALUES (?, ?)";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, rowData.getName());
      pstmt.setInt(2, rowData.getValue());
      pstmt.executeUpdate();
      logger.info("Row inserted with name: {} and value: {}", rowData.getName(),
          rowData.getValue());
    } catch (SQLException e) {
      logger.error("Error inserting row: {}", e.getMessage());
    }
  }

  /**
   * Updates the row in rowDataTable corresponding to the current RowData object.
   * The row is updated based on the ID of the RowData object.
   * Logs the SQL query upon successful update.
   */
  public void update() {
    String sql = "UPDATE rowDataTable SET NAME = ?, VALUE = ? WHERE ID = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
      pstmt.setString(1, rowData.getName());
      pstmt.setInt(2, rowData.getValue());
      pstmt.setInt(3, rowData.getId());
      int affectedRows = pstmt.executeUpdate();
      logger.info("Row updated, affected rows: {}", affectedRows);
    } catch (SQLException e) {
      logger.error("Error updating row: {}", e.getMessage());
    }
  }

  /**
   * Deletes the row in rowDataTable corresponding to the current RowData object.
   * The row is deleted based on the ID of the RowData object.
   * Logs the SQL query upon successful deletion.
   */
  public void delete() {
    String sql = "DELETE FROM rowDataTable WHERE ID = ?";
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setInt(1, rowData.getId());
      int rowsAffected = stmt.executeUpdate();
      logger.info("Row deleted. Rows affected: {}", rowsAffected);
    } catch (SQLException e) {
      logger.error("Error deleting row: {}", e.getMessage());
    }
  }
}
