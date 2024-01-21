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
package com.iluwatar.slob.dbservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;

/**
 * Service to handle database operations.
 */
@Slf4j
public class DatabaseService {

  public static final String CREATE_BINARY_SCHEMA_DDL =
      "CREATE TABLE IF NOT EXISTS FORESTS (ID NUMBER UNIQUE, NAME VARCHAR(30),FOREST VARBINARY)";
  public static final String CREATE_TEXT_SCHEMA_DDL =
      "CREATE TABLE IF NOT EXISTS FORESTS (ID NUMBER UNIQUE, NAME VARCHAR(30),FOREST VARCHAR)";
  public static final String DELETE_SCHEMA_SQL = "DROP TABLE FORESTS IF EXISTS";
  public static final String BINARY_DATA = "BINARY";
  private static final String DB_URL = "jdbc:h2:~/test";
  private static final String INSERT = "insert into FORESTS (id,name, forest) values (?,?,?)";
  private static final String SELECT = "select FOREST from FORESTS where id = ?";
  private static final DataSource dataSource = createDataSource();
  public String dataTypeDb;

  /**
   * Constructor initializes {@link DatabaseService#dataTypeDb}.
   *
   * @param dataTypeDb Type of data that is to be stored in DB can be 'TEXT' or 'BINARY'.
   */
  public DatabaseService(String dataTypeDb) {
    this.dataTypeDb = dataTypeDb;
  }

  /**
   * Initiates Data source.
   *
   * @return created data source
   */
  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }

  /**
   * Shutdown Sequence executes Query {@link DatabaseService#DELETE_SCHEMA_SQL}.
   *
   * @throws SQLException if any issue occurs while executing DROP Query
   */
  public void shutDownService()
      throws SQLException {
    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement()) {
      statement.execute(DELETE_SCHEMA_SQL);
    }
  }

  /**
   * Initaites startup sequence and executes the query
   * {@link DatabaseService#CREATE_BINARY_SCHEMA_DDL} if {@link DatabaseService#dataTypeDb} is
   * binary else will execute the query {@link DatabaseService#CREATE_TEXT_SCHEMA_DDL}.
   *
   * @throws SQLException if there are any issues during DDL execution
   */
  public void startupService()
      throws SQLException {
    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement()) {
      if (dataTypeDb.equals("BINARY")) {
        statement.execute(CREATE_BINARY_SCHEMA_DDL);
      } else {
        statement.execute(CREATE_TEXT_SCHEMA_DDL);
      }
    }
  }

  /**
   * Executes the insert query {@link DatabaseService#INSERT}.
   *
   * @param id   with which row is to be inserted
   * @param name name to be added in the row
   * @param data object data to be saved in the row
   * @throws SQLException if there are any issues in executing insert query
   *                      {@link DatabaseService#INSERT}
   */
  public void insert(int id, String name, Object data)
      throws SQLException {
    try (var connection = dataSource.getConnection();
        var insert = connection.prepareStatement(INSERT)) {
      insert.setInt(1, id);
      insert.setString(2, name);
      insert.setObject(3, data);
      insert.execute();
    }
  }

  /**
   * Runs the select query {@link DatabaseService#SELECT} form the result set returns an
   * {@link java.io.InputStream} if {@link DatabaseService#dataTypeDb} is 'binary' else will return
   * the object as a {@link String}.
   *
   * @param id          with which row is to be selected
   * @param columnsName column in which the object is stored
   * @return object found from DB
   * @throws SQLException if there are any issues in executing insert query *
   *                      {@link DatabaseService#SELECT}
   */
  public Object select(final long id, String columnsName) throws SQLException {
    ResultSet resultSet = null;
    try (var connection = dataSource.getConnection();
        var preparedStatement =
            connection.prepareStatement(SELECT)
    ) {
      Object result = null;
      preparedStatement.setLong(1, id);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        if (dataTypeDb.equals(BINARY_DATA)) {
          result = resultSet.getBinaryStream(columnsName);
        } else {
          result = resultSet.getString(columnsName);
        }
      }
      return result;
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
    }
  }
}
