package com.iluwatar.rowdatagateway;

import java.sql.SQLException;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;


/**
 * An object that acts as a Gateway to a single record in a data source. There
 * is one instance per row. Here Gateway means an object that encapsulates
 * access to an external system or resource This object does not contain
 * domain logic methods.
 *
 * <p>In this example I use the row data gateway pattern to access the person
 * table. The main method will initialise an instance of {@link PersonGateway}
 * for each row and use it to access each row in the database.</p>
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
    // Create data source and create the person table.
    final var dataSource = createDataSource();
    createSchema(dataSource);

    // Initialize three persons and access the database using PersonGateway.
    var person1 = new Person(1, "San", "Zhang");
    var person2 = new Person(2, "Si", "Li");
    var person3 = new Person(1, "Wu", "Wang");

    PersonGateway personGateway1 = new PersonGateway(person1, dataSource);
    PersonGateway personGateway2 = new PersonGateway(person2, dataSource);
    PersonGateway personGateway3 = new PersonGateway(person3, dataSource);

    personGateway1.insert();
    personGateway2.insert();
    personGateway3.update();
    personGateway3.delete();
    personGateway2.delete();

    deleteSchema(dataSource);
  }

  private static void deleteSchema(final DataSource dataSource)
          throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(PersonGateway.DELETE_SCHEMA_SQL);
    }
  }

  private static void createSchema(final DataSource dataSource)
          throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(PersonGateway.CREATE_SCHEMA_SQL);
    }
  }

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }
}
