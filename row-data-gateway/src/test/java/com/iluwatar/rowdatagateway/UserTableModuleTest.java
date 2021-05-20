package com.iluwatar.rowdatagateway;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
      statement.execute(PersonGateway.DELETE_SCHEMA_SQL);
      statement.execute(PersonGateway.CREATE_SCHEMA_SQL);
    }
  }

  @AfterEach
  void tearDown() throws SQLException {
    try (var connection = DriverManager.getConnection(DB_URL);
         var statement = connection.createStatement()) {
      statement.execute(PersonGateway.DELETE_SCHEMA_SQL);
    }
  }

  @Test
  void insertShouldSucceed() throws SQLException {
    var person = new Person(1, "Si", "Li");
    var dataSource = createDataSource();
    var personGateway = new PersonGateway(person, dataSource);
    assertEquals(person.getId(), personGateway.insert());
  }

  @Test
  void updateShouldSucceed() throws SQLException {
    var dataSource = createDataSource();
    var person1 = new Person(1, "Si", "Li");
    var personGateway1 = new PersonGateway(person1, dataSource);
    var person2 = new Person(1, "San", "Zhang");
    var personGateway2 = new PersonGateway(person2, dataSource);
    personGateway1.insert();
    assertEquals(person2.getId(), personGateway2.update());
  }

  @Test
  void deleteShouldSucceed() throws SQLException {
    var person = new Person(1, "Si", "Li");
    var dataSource = createDataSource();
    var personGateway = new PersonGateway(person, dataSource);
    personGateway.insert();
    assertEquals(person.getId(), personGateway.delete());

  }
}