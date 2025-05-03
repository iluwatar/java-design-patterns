package com.iluwatar.daofactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * An implementation of {@link CustomerDAO} that uses H2 database (http://www.h2database.com/)
 * which is an in-memory database and data will lost after application exits.
 */
@Slf4j
@RequiredArgsConstructor
public class H2CustomerDAO implements CustomerDAO<Long> {
  private final DataSource dataSource;
  private final String INSERT_CUSTOMER = "INSERT INTO customer(id, name) VALUES (?, ?)";
  private final String UPDATE_CUSTOMER = "UPDATE customer SET name = ? WHERE id = ?";
  private final String DELETE_CUSTOMER = "DELETE FROM customer WHERE id = ?";
  private final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customer WHERE id= ?";
  private final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer";
  private final String CREATE_SCHEMA =
      "CREATE TABLE IF NOT EXISTS customer (id BIGINT PRIMARY KEY, name VARCHAR(255))";
  private final String DROP_SCHEMA = "DROP TABLE IF EXISTS customer";

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(Customer<Long> customer) {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement saveStatement = connection.prepareStatement(INSERT_CUSTOMER)) {
      saveStatement.setLong(1, customer.getId());
      saveStatement.setString(2, customer.getName());
      saveStatement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(Customer<Long> customer) {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement selectStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID);
         PreparedStatement updateStatement = connection.prepareStatement(UPDATE_CUSTOMER)) {
      selectStatement.setLong(1, customer.getId());
      try (ResultSet resultSet = selectStatement.executeQuery()) {
        if (!resultSet.next()) {
          throw new RuntimeException("Customer not found with id: " + customer.getId());
        }
      }
      updateStatement.setString(1, customer.getName());
      updateStatement.setLong(2, customer.getId());
      updateStatement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(Long id) {
    try (Connection connection = dataSource.getConnection();
         PreparedStatement selectStatement = connection.prepareStatement(SELECT_CUSTOMER_BY_ID);
         PreparedStatement deleteStatement = connection.prepareStatement(DELETE_CUSTOMER)
    ) {
      selectStatement.setLong(1, id);
      try (ResultSet resultSet = selectStatement.executeQuery()) {
        if (!resultSet.next()) {
          throw new RuntimeException("Customer not found with id: " + id);
        }
      }
      deleteStatement.setLong(1, id);
      deleteStatement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Customer<Long>> findAll() {
    List<Customer<Long>> customers = new LinkedList<>();
    try (Connection connection = dataSource.getConnection();
         PreparedStatement selectStatement = connection.prepareStatement(SELECT_ALL_CUSTOMERS)) {
      try (ResultSet resultSet = selectStatement.executeQuery()) {
        while (resultSet.next()) {
          Long idCustomer = resultSet.getLong("id");
          String nameCustomer = resultSet.getString("name");
          customers.add(new Customer<>(idCustomer, nameCustomer));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    return customers;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Customer<Long>> findById(Long id) {
    Customer<Long> customer = null;
    try (Connection connection = dataSource.getConnection();
         PreparedStatement selectByIdStatement = connection.prepareStatement(
             SELECT_CUSTOMER_BY_ID)) {
      selectByIdStatement.setLong(1, id);
      try (ResultSet resultSet = selectByIdStatement.executeQuery()) {
        while (resultSet.next()) {
          Long idCustomer = resultSet.getLong("id");
          String nameCustomer = resultSet.getString("name");
          customer = new Customer<>(idCustomer, nameCustomer);
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    return Optional.ofNullable(customer);
  }

  /**
   * Create customer schema.
   */
  public void createSchema() {
    try (Connection connection = dataSource.getConnection();
         Statement statement = connection.createStatement()) {
      statement.execute(CREATE_SCHEMA);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  /**
   * {@inheritDoc}}
   */
  @Override
  public void deleteSchema() {
    try (Connection connection = dataSource.getConnection();
         Statement statement = connection.createStatement();) {
      statement.execute(DROP_SCHEMA);
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }
}
