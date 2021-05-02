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

package com.iluwatar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * An implementation of {@link CustomerDao} that persists customers in RDBMS.
 */
@Slf4j
@RequiredArgsConstructor
public class DbCustomerDao implements CustomerDao {

  private final DataSource dataSource;

  /**
   * Get all customers as Java Stream.
   *
   * @return a lazily populated stream of customers. Note the stream returned must be closed to free
   *     all the acquired resources. The stream keeps an open connection to the database till it is
   *     complete or is closed manually.
   */
  @Override
  public Stream<Customer> getAll() throws Exception {
    try {
      var connection = getConnection();
      var statement = connection.prepareStatement("SELECT * FROM CUSTOMERS"); // NOSONAR
      var resultSet = statement.executeQuery(); // NOSONAR
      return StreamSupport.stream(new Spliterators.AbstractSpliterator<Customer>(Long.MAX_VALUE,
          Spliterator.ORDERED) {

        @Override
        public boolean tryAdvance(Consumer<? super Customer> action) {
          try {
            if (!resultSet.next()) {
              return false;
            }
            action.accept(createCustomer(resultSet));
            return true;
          } catch (SQLException e) {
            throw new RuntimeException(e); // NOSONAR
          }
        }
      }, false).onClose(() -> mutedClose(connection, statement, resultSet));
    } catch (SQLException e) {
      throw new CustomException(e.getMessage(), e);
    }
  }

  private Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  private void mutedClose(Connection connection, PreparedStatement statement, ResultSet resultSet) {
    try {
      resultSet.close();
      statement.close();
      connection.close();
    } catch (SQLException e) {
      LOGGER.info("Exception thrown " + e.getMessage());
    }
  }

  private Customer createCustomer(ResultSet resultSet) throws SQLException {
    return new Customer(resultSet.getInt("ID"),
        resultSet.getString("FNAME"),
        resultSet.getString("LNAME"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Customer> getById(int id) throws Exception {

    ResultSet resultSet = null;

    try (var connection = getConnection();
         var statement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE ID = ?")) {

      statement.setInt(1, id);
      resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return Optional.of(createCustomer(resultSet));
      } else {
        return Optional.empty();
      }
    } catch (SQLException ex) {
      throw new CustomException(ex.getMessage(), ex);
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean add(Customer customer) throws Exception {
    if (getById(customer.getId()).isPresent()) {
      return false;
    }

    try (var connection = getConnection();
         var statement = connection.prepareStatement("INSERT INTO CUSTOMERS VALUES (?,?,?)")) {
      statement.setInt(1, customer.getId());
      statement.setString(2, customer.getFirstName());
      statement.setString(3, customer.getLastName());
      statement.execute();
      return true;
    } catch (SQLException ex) {
      throw new CustomException(ex.getMessage(), ex);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean update(Customer customer) throws Exception {
    try (var connection = getConnection();
         var statement =
             connection
                 .prepareStatement("UPDATE CUSTOMERS SET FNAME = ?, LNAME = ? WHERE ID = ?")) {
      statement.setString(1, customer.getFirstName());
      statement.setString(2, customer.getLastName());
      statement.setInt(3, customer.getId());
      return statement.executeUpdate() > 0;
    } catch (SQLException ex) {
      throw new CustomException(ex.getMessage(), ex);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean delete(Customer customer) throws Exception {
    try (var connection = getConnection();
         var statement = connection.prepareStatement("DELETE FROM CUSTOMERS WHERE ID = ?")) {
      statement.setInt(1, customer.getId());
      return statement.executeUpdate() > 0;
    } catch (SQLException ex) {
      throw new CustomException(ex.getMessage(), ex);
    }
  }
}
