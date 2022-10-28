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
  private static final String DB_URL = "jdbc:h2:~/test";

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
