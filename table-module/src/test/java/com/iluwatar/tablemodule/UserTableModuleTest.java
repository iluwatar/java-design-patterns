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