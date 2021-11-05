/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTableModuleTest {
  private static final String DB_URL = "jdbc:h2:~/test";

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }

  @BeforeEach
  void setUp() throws SQLException {
    try (var connection = DriverManager.getConnection(DB_URL);
         var statement = connection.createStatement()) {
      statement.execute(UserTableModule.DELETE_SCHEMA_SQL);
      statement.execute(UserTableModule.CREATE_SCHEMA_SQL);
    }
  }

  @AfterEach
  void tearDown() throws SQLException {
    try (var connection = DriverManager.getConnection(DB_URL);
         var statement = connection.createStatement()) {
      statement.execute(UserTableModule.DELETE_SCHEMA_SQL);
    }
  }

  @Test
  void loginShouldFail() throws SQLException {
    var dataSource = createDataSource();
    var userTableModule = new UserTableModule(dataSource);
    var user = new User(1, "123456", "123456");
    assertEquals(0, userTableModule.login(user.getUsername(),
            user.getPassword()));
  }

  @Test
  void loginShouldSucceed() throws SQLException {
    var dataSource = createDataSource();
    var userTableModule = new UserTableModule(dataSource);
    var user = new User(1, "123456", "123456");
    userTableModule.registerUser(user);
    assertEquals(1, userTableModule.login(user.getUsername(),
            user.getPassword()));
  }

  @Test
  void registerShouldFail() throws SQLException {
    var dataSource = createDataSource();
    var userTableModule = new UserTableModule(dataSource);
    var user = new User(1, "123456", "123456");
    userTableModule.registerUser(user);
    assertThrows(SQLException.class, () -> {
      userTableModule.registerUser(user);
    });
  }

  @Test
  void registerShouldSucceed() throws SQLException {
    var dataSource = createDataSource();
    var userTableModule = new UserTableModule(dataSource);
    var user = new User(1, "123456", "123456");
    assertEquals(1, userTableModule.registerUser(user));
  }
}