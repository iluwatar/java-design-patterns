/*
*The MIT License
*Copyright © 2014-2021 Ilkka Seppälä
*
*Permission is hereby granted, free of charge, to any person obtaining a copy
*of this software and associated documentation files (the "Software"), to deal
*in the Software without restriction, including without limitation the rights
*to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*copies of the Software, and to permit persons to whom the Software is
*furnished to do so, subject to the following conditions:
*
*The above copyright notice and this permission notice shall be included in
*all copies or substantial portions of the Software.
*
*THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
*IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
*FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
*AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
*LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
*THE SOFTWARE.
*/

/*
 *The MIT License
 *Copyright © 2014-2021 Ilkka Seppälä
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 */

package com.iluwatar.optimistic.offline.lock.customer;

import com.iluwatar.optimistic.offline.lock.OptimisticLockException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.util.JdbcUtils;

/**
 * Implementation of actions performed on customer data listed by the CustomerDataMapper interface.
 */
@Slf4j
@RequiredArgsConstructor
public class CustomerDataMapperImpl implements CustomerDataMapper {

  private final DataSource dataSource;

  @Override
  public Customer find(long id) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Customer customer = null;
    try {
      connection = getConnection();
      preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE id = ?");
      preparedStatement.setLong(1, id);
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        customer = createCustomer(resultSet);
      }
    } catch (SQLException exception) {
      LOGGER.error(exception.getMessage(), exception);
    } finally {
      closeDbResources(resultSet, preparedStatement, connection);
    }
    return customer;
  }

  @Override
  public List<Customer> getAllCustomers() {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    List<Customer> customers = new ArrayList<>();
    try {
      connection = getConnection();
      preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS");
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        customers.add(createCustomer(resultSet));
      }
    } catch (SQLException exception) {
      LOGGER.error(exception.getMessage(), exception);
    } finally {
      closeDbResources(resultSet, preparedStatement, connection);
    }
    return customers;
  }

  @Override
  public boolean insert(Customer customer) {
    if (find(customer.getId()) != null) {
      return false;
    }

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = getConnection();
      preparedStatement =
          connection.prepareStatement("INSERT INTO CUSTOMERS VALUES(?, ?, ?, ?, ?, ?)");
      preparedStatement.setLong(1, customer.getId());
      preparedStatement.setString(2, customer.getFirstName());
      preparedStatement.setString(3, customer.getLastName());
      preparedStatement.setString(4, Thread.currentThread().getName());
      preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
      preparedStatement.setInt(6, customer.getVersion());
      preparedStatement.execute();
    } catch (SQLException exception) {
      LOGGER.error(exception.getMessage(), exception);
    } finally {
      closeDbResources(preparedStatement, connection);
    }
    return true;
  }

  @Override
  public void update(Customer customer) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = getConnection();
      preparedStatement =
          connection.prepareStatement(
              "UPDATE CUSTOMERS SET first_name = ?, last_name = ?,"
                  + "modified_by = ?, modified = ?, version = ? WHERE id = ? AND version = ?");
      preparedStatement.setString(1, customer.getFirstName());
      preparedStatement.setString(2, customer.getLastName());
      preparedStatement.setString(3, Thread.currentThread().getName());
      preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
      preparedStatement.setInt(5, customer.getVersion() + 1);
      preparedStatement.setLong(6, customer.getId());
      preparedStatement.setInt(7, customer.getVersion());
      if (preparedStatement.executeUpdate() == 0) {
        throwOptimisticLockException(customer);
      }
    } catch (SQLException exception) {
      LOGGER.error(exception.getMessage(), exception);
    } finally {
      closeDbResources(preparedStatement, connection);
    }
  }

  @Override
  public void delete(Customer customer) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    try {
      connection = getConnection();
      preparedStatement =
          connection.prepareStatement("DELETE FROM CUSTOMERS WHERE id = ? AND version = ?");
      preparedStatement.setLong(1, customer.getId());
      preparedStatement.setInt(2, customer.getVersion());
      if (preparedStatement.executeUpdate() == 0) {
        throwOptimisticLockException(customer);
      }
    } catch (SQLException exception) {
      LOGGER.error(exception.getMessage(), exception);
    } finally {
      closeDbResources(preparedStatement, connection);
    }
  }

  private Connection getConnection() throws SQLException {
    return this.dataSource.getConnection();
  }

  private Customer createCustomer(ResultSet resultSet) throws SQLException {
    return new Customer(
        resultSet.getLong("id"),
        resultSet.getString("first_name"),
        resultSet.getString("last_name"),
        resultSet.getString("modified_by"),
        resultSet.getTimestamp("modified"),
        resultSet.getInt("version"));
  }

  private void throwOptimisticLockException(Customer customer) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
      connection = getConnection();
      preparedStatement =
          connection.prepareStatement(
              "SELECT version, modified_by, modified FROM CUSTOMERS " + "WHERE id = ?");
      preparedStatement.setLong(1, customer.getId());
      resultSet = preparedStatement.executeQuery();
      if (resultSet.next()) {
        if (resultSet.getInt("version") > customer.getVersion()) {
          throw new OptimisticLockException(
              "Customer with id="
                  + customer.getId()
                  + " was modified by "
                  + resultSet.getString("modified_by")
                  + " at "
                  + resultSet.getTimestamp("modified")
                  + ".");
        }
      } else {
        throw new OptimisticLockException(
            "Customer with id=" + customer.getId() + " has been deleted.");
      }
    } catch (SQLException exception) {
      LOGGER.error(exception.getMessage(), exception);
    } finally {
      closeDbResources(resultSet, preparedStatement, connection);
    }
  }

  private void closeDbResources(PreparedStatement preparedStatement, Connection connection) {
    JdbcUtils.closeSilently(preparedStatement);
    JdbcUtils.closeSilently(connection);
  }

  private void closeDbResources(
      ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
    JdbcUtils.closeSilently(resultSet);
    closeDbResources(preparedStatement, connection);
  }
}
