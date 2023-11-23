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
package com.iluwatar.dao;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;

/**
 * Data Access Object (DAO) is an object that provides an abstract interface to some type of
 * database or other persistence mechanism. By mapping application calls to the persistence layer,
 * DAO provide some specific data operations without exposing details of the database. This
 * isolation supports the Single responsibility principle. It separates what data accesses the
 * application needs, in terms of domain-specific objects and data types (the public interface of
 * the DAO), from how these needs can be satisfied with a specific DBMS.
 *
 * <p>With the DAO pattern, we can use various method calls to retrieve/add/delete/update data
 * without directly interacting with the data source. The below example demonstrates basic CRUD
 * operations: select, add, update, and delete.
 */
@Slf4j
public class App {
  private static final String DB_URL = "jdbc:h2:mem:dao;DB_CLOSE_DELAY=-1";
  private static final String ALL_CUSTOMERS = "customerDao.getAllCustomers(): ";

  /**
   * Program entry point.
   *
   * @param args command line args.
   * @throws Exception if any error occurs.
   */
  public static void main(final String[] args) throws Exception {
    final var inMemoryDao = new InMemoryCustomerDao();
    performOperationsUsing(inMemoryDao);

    final var dataSource = createDataSource();
    createSchema(dataSource);
    final var dbDao = new DbCustomerDao(dataSource);
    performOperationsUsing(dbDao);
    deleteSchema(dataSource);
  }

  private static void deleteSchema(DataSource dataSource) throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(CustomerSchemaSql.DELETE_SCHEMA_SQL);
    }
  }

  private static void createSchema(DataSource dataSource) throws SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(CustomerSchemaSql.CREATE_SCHEMA_SQL);
    }
  }

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }

  private static void performOperationsUsing(final CustomerDao customerDao) throws Exception {
    addCustomers(customerDao);
    LOGGER.info(ALL_CUSTOMERS);
    try (var customerStream = customerDao.getAll()) {
      customerStream.forEach(customer -> LOGGER.info(customer.toString()));
    }
    LOGGER.info("customerDao.getCustomerById(2): " + customerDao.getById(2));
    final var customer = new Customer(4, "Dan", "Danson");
    customerDao.add(customer);
    LOGGER.info(ALL_CUSTOMERS + customerDao.getAll());
    customer.setFirstName("Daniel");
    customer.setLastName("Danielson");
    customerDao.update(customer);
    LOGGER.info(ALL_CUSTOMERS);
    try (var customerStream = customerDao.getAll()) {
      customerStream.forEach(cust -> LOGGER.info(cust.toString()));
    }
    customerDao.delete(customer);
    LOGGER.info(ALL_CUSTOMERS + customerDao.getAll());
  }

  private static void addCustomers(CustomerDao customerDao) throws Exception {
    for (var customer : generateSampleCustomers()) {
      customerDao.add(customer);
    }
  }

  /**
   * Generate customers.
   *
   * @return list of customers.
   */
  public static List<Customer> generateSampleCustomers() {
    final var customer1 = new Customer(1, "Adam", "Adamson");
    final var customer2 = new Customer(2, "Bob", "Bobson");
    final var customer3 = new Customer(3, "Carl", "Carlson");
    return List.of(customer1, customer2, customer3);
  }
}
