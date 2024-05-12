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
package com.iluwatar.tablemodule;

import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;


/**
 * Table Module pattern is a domain logic pattern.
 * In Table Module a single class encapsulates all the domain logic for all
 * records stored in a table or view. It's important to note that there is no
 * translation of data between objects and rows, as it happens in Domain Model,
 * hence implementation is relatively simple when compared to the Domain
 * Model pattern.
 *
 * <p>In this example we will use the Table Module pattern to implement register
 * and login methods for the records stored in the user table. The main
 * method will initialise an instance of {@link UserTableModule} and use it to
 * handle the domain logic for the user table.</p>
 */
@Slf4j
public final class App {
  private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";

  /**
   * Private constructor.
   */
  private App() {

  }

  /**
   * Program entry point.
   *
   * @param args command line args.
   * @throws SQLException if any error occurs.
   */
  public static void main(final String[] args) throws SQLException {
    // Create data source and create the user table.
    final var dataSource = createDataSource();
    createSchema(dataSource);
    var userTableModule = new UserTableModule(dataSource);

    // Initialize two users.
    var user1 = new User(1, "123456", "123456");
    var user2 = new User(2, "test", "password");

    // Login and register using the instance of userTableModule.
    userTableModule.registerUser(user1);
    userTableModule.login(user1.getUsername(), user1.getPassword());
    userTableModule.login(user2.getUsername(), user2.getPassword());
    userTableModule.registerUser(user2);
    userTableModule.login(user2.getUsername(), user2.getPassword());

    deleteSchema(dataSource);
  }

  private static void deleteSchema(final DataSource dataSource)
          throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(UserTableModule.DELETE_SCHEMA_SQL);
    }
  }

  private static void createSchema(final DataSource dataSource)
          throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(UserTableModule.CREATE_SCHEMA_SQL);
    }
  }

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }
}
