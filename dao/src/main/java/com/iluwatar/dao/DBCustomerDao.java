/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.sql.DataSource;

/**
 * 
 *
 */
public class DBCustomerDao implements CustomerDao {

  private final DataSource dataSource;

  public DBCustomerDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Stream<Customer> getAll() throws Exception {
    
    Connection connection;
    try {
      connection = getConnection();
      PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMERS");
      ResultSet resultSet = statement.executeQuery();
      return StreamSupport.stream(new Spliterators.AbstractSpliterator<Customer>(Long.MAX_VALUE, Spliterator.ORDERED) {
        
        @Override
        public boolean tryAdvance(Consumer<? super Customer> action) {
          try {
            if (!resultSet.next()) {
              return false;
            }
            action.accept(createCustomer(resultSet));
            return true;
          } catch (SQLException e) {
            e.printStackTrace();
            return false;
          }
        }}, false).onClose(() -> mutedClose(connection));
    } catch (SQLException e) {
      throw new Exception(e.getMessage(), e);
    }
  }
  
  private Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  private void mutedClose(Connection connection) {
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private Customer createCustomer(ResultSet resultSet) throws SQLException {
    return new Customer(resultSet.getInt("ID"), 
        resultSet.getString("FNAME"), 
        resultSet.getString("LNAME"));
  }
  
  @Override
  public Customer getById(int id) throws Exception {
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE ID = ?")) {
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
          return createCustomer(resultSet);
        } else {
          return null;
        }
    } catch (SQLException ex) {
      throw new Exception(ex.getMessage(), ex);
    }
  }

  @Override
  public boolean add(Customer customer) throws Exception {
    if (getById(customer.getId()) != null) {
      return false;
    }
    
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO CUSTOMERS VALUES (?,?,?)")) {
      statement.setInt(1, customer.getId());
      statement.setString(2, customer.getFirstName());
      statement.setString(3, customer.getLastName());
      statement.execute();
      return true;
    } catch (SQLException ex) {
      throw new Exception(ex.getMessage(), ex);
    }
  }

  @Override
  public boolean update(Customer customer) throws Exception {
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE CUSTOMERS SET FNAME = ?, LNAME = ? WHERE ID = ?")) {
      statement.setString(1, customer.getFirstName());
      statement.setString(2, customer.getLastName());
      statement.setInt(3, customer.getId());
      return statement.executeUpdate() > 0;
    } catch (SQLException ex) {
      throw new Exception(ex.getMessage(), ex);
    }
  }

  @Override
  public boolean delete(Customer customer) throws Exception {
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM CUSTOMERS WHERE ID = ?")) {
      statement.setInt(1, customer.getId());
      return statement.executeUpdate() > 0;
    } catch (SQLException ex) {
      throw new Exception(ex.getMessage(), ex);
    }
  }
}
