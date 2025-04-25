package com.iluwatar.daofactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by: IntelliJ IDEA
 * User      : dthanh
 * Date      : 16/04/2025
 * Time      : 23:26
 * Filename  : H2CustomerDAO
 */
@Slf4j
public class H2CustomerDAO implements CustomerDAO<Long> {
  static final String JDBC_DRIVER = "org.h2.Driver";
  static final String DB_URL = "jdbc:h2:~/test";

  //  Database credentials
  static final String USER = "sa";
  static final String PASS = "";

  private static final String INSERT_CUSTOMER = "INSERT INTO customer(id, name) VALUES (?, ?)";
  private static final String UPDATE_CUSTOMER = "UPDATE customer SET name = ? WHERE id = ?";
  private static final String DELETE_CUSTOMER = "DELETE FROM customer WHERE id = ?";
  private static final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customer WHERE id= ?";
  private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer";

  @Override
  public void save(Customer<Long> customer) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      Class.forName(JDBC_DRIVER);
      connection = DriverManager.getConnection(DB_URL, USER, PASS);
      preparedStatement = connection.prepareStatement(
          "CREATE TABLE IF NOT EXISTS customer (id BIGINT PRIMARY KEY, name VARCHAR(255))");
      preparedStatement.execute();
      preparedStatement = connection.prepareStatement(INSERT_CUSTOMER);
      preparedStatement.setLong(1, customer.getId());
      preparedStatement.setString(2, customer.getName());
      preparedStatement.execute();
      connection.commit();
    } catch (SQLException | ClassNotFoundException e) {
      try {
        connection.rollback();
      } catch (SQLException ex) {
        LOGGER.error("Exception occurred: " + e);
      }
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        LOGGER.error("Exception occurred: " + e);
      }
    }
  }

  @Override
  public void update(Customer<Long> customer) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      Class.forName(JDBC_DRIVER);
      connection = DriverManager.getConnection(DB_URL, USER, PASS);
      preparedStatement = connection.prepareStatement(UPDATE_CUSTOMER);
      preparedStatement.setString(1, customer.getName());
      preparedStatement.setLong(2, customer.getId());
      preparedStatement.execute();
    } catch (SQLException | ClassNotFoundException e) {
      LOGGER.error("Exception occurred: " + e.getMessage());
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        LOGGER.error("Exception occurred: " + e.getMessage());
      }
    }
  }

  @Override
  public void delete(Long id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      Class.forName(JDBC_DRIVER);
      connection = DriverManager.getConnection(DB_URL, USER, PASS);
      preparedStatement = connection.prepareStatement(DELETE_CUSTOMER);
      preparedStatement.setLong(1, id);
      preparedStatement.execute();
    } catch (SQLException | ClassNotFoundException e) {
      LOGGER.error("Exception occurred: " + e.getMessage());
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        LOGGER.error("Exception occurred: " + e.getMessage());
      }
    }
  }

  @Override
  public List<Customer<Long>> findAll() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    List<Customer<Long>> customers = new LinkedList<>();
    try {
      Class.forName(JDBC_DRIVER);
      connection = DriverManager.getConnection(DB_URL, USER, PASS);
      preparedStatement = connection.prepareStatement(SELECT_ALL_CUSTOMERS);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Long idCustomer = resultSet.getLong("id");
        String nameCustomer = resultSet.getString("name");
        customers.add(new Customer<>(idCustomer, nameCustomer));
      }
      resultSet.close();
    } catch (SQLException | ClassNotFoundException e) {
      LOGGER.error("Exception occurred: " + e.getMessage());
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        LOGGER.error("Exception occurred: " + e.getMessage());
      }
    }
    return customers;
  }

  @Override
  public Optional<Customer<Long>> findById(Long id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Customer<Long> customer = null;
    try {
      Class.forName(JDBC_DRIVER);
      connection = DriverManager.getConnection(DB_URL, USER, PASS);
      preparedStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID);
      preparedStatement.setLong(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        Long idCustomer = resultSet.getLong("id");
        String nameCustomer = resultSet.getString("name");
        customer = new Customer<>(idCustomer, nameCustomer);
      }
      resultSet.close();
    } catch (SQLException | ClassNotFoundException e) {
      LOGGER.error("Exception occurred: " + e.getMessage());
    } finally {
      try {
        if (preparedStatement != null) {
          preparedStatement.close();
        }
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException e) {
        LOGGER.error("Exception occurred: " + e.getMessage());
      }
    }
    return Optional.ofNullable(customer);
  }

  @Override
  public void deleteSchema() {
    try (var connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      var statement = connection.createStatement();
      statement.execute("DROP TABLE customer");
    } catch (SQLException e) {
      throw new RuntimeException("Error while deleting schema", e);
    }
  }
}
