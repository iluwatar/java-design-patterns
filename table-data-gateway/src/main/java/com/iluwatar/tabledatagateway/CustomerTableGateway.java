/*
 * This project is licensed under the MIT license. Module table-data-gateway uses H2 database licensed under EPL (see epl-1.0.txt).
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
package com.iluwatar.tabledatagateway;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gateway that encapsulates all SQL operations for the CUSTOMERS table.
 *
 * <p>Unlike a Row Data Gateway, one instance of this class can operate on any row in the table.
 */
public final class CustomerTableGateway {

  /** SQL statement for creating the schema used by this example. */
  public static final String CREATE_SCHEMA_SQL =
      "CREATE TABLE IF NOT EXISTS CUSTOMERS (ID INT PRIMARY KEY, FIRST_NAME VARCHAR(30) "
          + "NOT NULL, LAST_NAME VARCHAR(30) NOT NULL)";

  /** SQL statement for deleting the schema used by this example. */
  public static final String DELETE_SCHEMA_SQL = "DROP TABLE IF EXISTS CUSTOMERS";

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerTableGateway.class);

  private final DataSource dataSource;

  /**
   * Creates a gateway for the CUSTOMERS table.
   *
   * @param dataSource data source used by all gateway operations
   */
  public CustomerTableGateway(final DataSource dataSource) {
    this.dataSource = Objects.requireNonNull(dataSource);
  }

  /**
   * Inserts a customer row.
   *
   * @param customer customer data to insert
   * @return number of rows inserted
   * @throws SQLException if the database operation fails
   */
  public int insert(final Customer customer) throws SQLException {
    Objects.requireNonNull(customer);
    var sql = "INSERT INTO CUSTOMERS (ID, FIRST_NAME, LAST_NAME) VALUES (?,?,?)";
    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, customer.id());
      preparedStatement.setString(2, customer.firstName());
      preparedStatement.setString(3, customer.lastName());
      var inserted = preparedStatement.executeUpdate();
      LOGGER.info("Inserted customer with id {}", customer.id());
      return inserted;
    }
  }

  /**
   * Finds a customer by id.
   *
   * @param id customer identifier
   * @return the customer if a matching row exists, otherwise an empty optional
   * @throws SQLException if the database operation fails
   */
  public Optional<Customer> findById(final int id) throws SQLException {
    var sql = "SELECT ID, FIRST_NAME, LAST_NAME FROM CUSTOMERS WHERE ID=?";
    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, id);
      try (ResultSet resultSet = preparedStatement.executeQuery()) {
        if (resultSet.next()) {
          return Optional.of(mapCustomer(resultSet));
        }
        return Optional.empty();
      }
    }
  }

  /**
   * Returns all customer rows ordered by id.
   *
   * @return all customers in the table
   * @throws SQLException if the database operation fails
   */
  public List<Customer> findAll() throws SQLException {
    var customers = new ArrayList<Customer>();
    var sql = "SELECT ID, FIRST_NAME, LAST_NAME FROM CUSTOMERS ORDER BY ID";
    try (var connection = dataSource.getConnection();
        var statement = connection.createStatement();
        var resultSet = statement.executeQuery(sql)) {
      while (resultSet.next()) {
        customers.add(mapCustomer(resultSet));
      }
    }
    return customers;
  }

  /**
   * Updates a customer row.
   *
   * @param customer customer data with updated values
   * @return number of rows updated, or zero if the row does not exist
   * @throws SQLException if the database operation fails
   */
  public int update(final Customer customer) throws SQLException {
    Objects.requireNonNull(customer);
    var sql = "UPDATE CUSTOMERS SET FIRST_NAME=?, LAST_NAME=? WHERE ID=?";
    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, customer.firstName());
      preparedStatement.setString(2, customer.lastName());
      preparedStatement.setInt(3, customer.id());
      var updated = preparedStatement.executeUpdate();
      LOGGER.info("Updated customer with id {}, affected rows: {}", customer.id(), updated);
      return updated;
    }
  }

  /**
   * Deletes a customer row.
   *
   * @param id customer identifier
   * @return number of rows deleted, or zero if the row does not exist
   * @throws SQLException if the database operation fails
   */
  public int delete(final int id) throws SQLException {
    var sql = "DELETE FROM CUSTOMERS WHERE ID=?";
    try (var connection = dataSource.getConnection();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setInt(1, id);
      var deleted = preparedStatement.executeUpdate();
      LOGGER.info("Deleted customer with id {}, affected rows: {}", id, deleted);
      return deleted;
    }
  }

  private static Customer mapCustomer(final ResultSet resultSet) throws SQLException {
    return new Customer(
        resultSet.getInt("ID"),
        resultSet.getString("FIRST_NAME"),
        resultSet.getString("LAST_NAME"));
  }
}
