package com.iluwatar.activerecord;

import java.sql.SQLException;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;

/**
 * The amin application for the manual testing purposes.
 */
@Slf4j
public class App {

  private static final String DB_URL = "jdbc:h2:mem:dao;DB_CLOSE_DELAY=-1";

  private static final String CREATE_SCHEMA_SQL = "CREATE TABLE customer\n"
      + "(\n"
      + "    id              BIGINT      NOT NULL,\n"
      + "    customer_number VARCHAR(15) NOT NULL,\n"
      + "    first_name      VARCHAR(45) NOT NULL,\n"
      + "    last_name       VARCHAR(45) NOT NULL,\n"
      + "    CONSTRAINT customer_pkey PRIMARY KEY (id)\n"
      + ");\n"
      + "\n"
      + "CREATE TABLE \"order\"\n"
      + "(\n"
      + "    id           BIGINT      NOT NULL,\n"
      + "    order_number VARCHAR(15) NOT NULL,\n"
      + "    customer_id  BIGINT      NOT NULL,\n"
      + "    CONSTRAINT order_pkey PRIMARY KEY (id),\n"
      + "    CONSTRAINT customer_id_fk FOREIGN KEY (customer_id) REFERENCES customer (id)\n"
      + ")";

  /**
   * Java main method to execute all the logic out there.
   *
   * @param args arguments.
   * @throws Exception Any sort of exception that have to be picked up by the JVM.
   */
  public static void main(final String[] args) throws Exception {
    final var dataSource = createDataSource();
    createSchema(dataSource);
    RecordBase.setDataSource(dataSource);
    executeOperation();
  }

  private static void executeOperation() {
    LOGGER.info("saving the customer data...");
    Customer customer = new Customer();
    customer.setId(1L);
    customer.setCustomerNumber("C123");
    customer.setFirstName("John");
    customer.setLastName("Smith");

    Order order = new Order();
    order.setId(1L);
    order.setOrderNumber("O123");

    // customer.addOrder(order);
    customer.save();

    LOGGER.info("The customer data by ID={}", customer.findById(1L));

    LOGGER.info("find all the customers={}", customer.findAll());
  }

  private static void createSchema(DataSource dataSource) throws SQLException {
    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement()) {
      statement.execute(CREATE_SCHEMA_SQL);
    }
  }

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }

}
