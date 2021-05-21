package com.iluwatar.serializedlob;

import java.io.IOException;
import java.sql.SQLException;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.jdom2.JDOMException;


/**
 * In serialized LOB pattern, you can save a graph of objects as by serializing
 * them into a single large object (LOB) and store the LOB in a database field.
 *
 * <p>In this example we will use the serialized LOB pattern to store the
 * graph of customer and department class. I will serialize the LOB as textual
 * characters (CLOB) and store it in the database in the format of XML. We
 * can insert and load LOBs from the customer table.</p>
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
   * @param args command line args
   * @throws SQLException  if any error occurs
   * @throws IOException   if any error occurs
   * @throws JDOMException if any error occurs
   */
  public static void main(final String[] args)
          throws SQLException, IOException, JDOMException {

    // Create data source and create the customer table.
    final var dataSource = createDataSource();
    deleteSchema(dataSource);
    createSchema(dataSource);

    var customer = new Customer("customer1", 1);
    var department1 = new Department("department1");
    var department2 = new Department("department2");
    var department3 = new Department("department3");
    department1.addSubsidiary(department2);
    department2.addSubsidiary(department3);
    customer.addDepartment(department1);

    var id = customer.insert(dataSource);
    var element = customer.load(id, dataSource);
    LOGGER.info(Customer.elementToString(customer.departmentsToXmlElement()));
    customer.readDepartments(element);
    LOGGER.info(Customer.elementToString(customer.departmentsToXmlElement()));

    deleteSchema(dataSource);

  }


  private static void deleteSchema(final DataSource dataSource)
          throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(Customer.DELETE_SCHEMA_SQL);
    }
  }

  private static void createSchema(final DataSource dataSource)
          throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(Customer.CREATE_SCHEMA_SQL);
    }
  }

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }
}
