package com.iluwatar.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DBCustomerDao implements CustomerDao {

  private String dbUrl;

  public DBCustomerDao(String dbUrl) {
    this.dbUrl = dbUrl;
  }

  @Override
  public Stream<Customer> getAll() {
    
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
      e.printStackTrace();
      return null;
    }
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
  public Customer getById(int id) {
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE ID = ?")) {
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
          return createCustomer(resultSet);
        }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Override
  public boolean add(Customer customer) {
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
      ex.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean update(Customer customer) {
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE CUSTOMERS SET FNAME = ?, LNAME = ? WHERE ID = ?")) {
      statement.setString(1, customer.getFirstName());
      statement.setString(2, customer.getLastName());
      statement.setInt(3, customer.getId());
      return statement.executeUpdate() > 0;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  @Override
  public boolean delete(Customer customer) {
    try (Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM CUSTOMERS WHERE ID = ?")) {
      statement.setInt(1, customer.getId());
      return statement.executeUpdate() > 0;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(dbUrl);
  }

}
