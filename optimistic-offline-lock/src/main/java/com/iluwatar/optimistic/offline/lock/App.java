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

package com.iluwatar.optimistic.offline.lock;

import com.iluwatar.optimistic.offline.lock.customer.Customer;
import com.iluwatar.optimistic.offline.lock.customer.CustomerDataMapper;
import com.iluwatar.optimistic.offline.lock.customer.CustomerDataMapperImpl;
import com.iluwatar.optimistic.offline.lock.customer.CustomerSchemaSql;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.util.JdbcUtils;

/** Application demonstrating optimistic offline lock pattern. */
@Slf4j
public class App {
  private static final String DB_URL = "jdbc:h2:~/optimistic-offline-lock";

  /**
   * Program entry point.
   *
   * @param args no argument sent
   */
  public static void main(String[] args) {
    final DataSource dataSource = createDataSource();
    final CustomerDataMapperImpl customerDataMapper = new CustomerDataMapperImpl(dataSource);

    createSchema(dataSource);
    showAllCustomers(customerDataMapper);
    updateCustomerOptimisticLockExample(customerDataMapper);
    deleteUpdatedCustomerOptimisticLockExample(customerDataMapper);
    updateDeletedCustomerOptimisticLockExample(customerDataMapper);
    showAllCustomers(customerDataMapper);
    deleteSchema(dataSource);
  }

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    return dataSource;
  }

  private static void createSchema(final DataSource dataSource) {
    Connection connection = null;
    Statement statement = null;
    try {
      connection = dataSource.getConnection();
      statement = connection.createStatement();
      statement.execute(CustomerSchemaSql.CREATE_SCHEMA_SQL + CustomerSchemaSql.POPULATE_DB);
    } catch (SQLException exception) {
      LOGGER.error(exception.getMessage(), exception);
    } finally {
      closeDbResources(statement, connection);
    }
  }

  private static void showAllCustomers(final CustomerDataMapper customerDataMapper) {
    LOGGER.info("LIST OF CUSTOMERS IN DB:");
    for (Customer customer : customerDataMapper.getAllCustomers()) {
      System.out.println(customer);
    }
  }

  private static void updateCustomerOptimisticLockExample(
      final CustomerDataMapper customerDataMapper) {
    LOGGER.info("EXAMPLE OF OPTIMISTIC OFFLINE LOCK FOR UPDATE OPERATION.");
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    executorService.execute(
        () -> updateCustomer(customerDataMapper, 1L, "Aubrey", "Miller", 4000L));
    executorService.execute(() -> updateCustomer(customerDataMapper, 1L, "Hawa", "Parra", 2000L));
    waitForThreadsToFinishExecution(executorService);
    LOGGER.info("Current customer with id=1 in DB: " + customerDataMapper.find(1L));
  }

  private static void updateCustomer(
      final CustomerDataMapper customerDataMapper,
      long id,
      String setFirstName,
      String setLastName,
      long sleepTime) {
    Customer customer = customerDataMapper.find(id);
    LOGGER.info(
        Thread.currentThread().getName() + " found customer with id=" + id + ": " + customer + ".");
    customer.setFirstName(setFirstName);
    customer.setLastName(setLastName);
    try {
      Thread.sleep(sleepTime);
      customerDataMapper.update(customer);
      LOGGER.info("Successful update by " + Thread.currentThread().getName() + ".");
    } catch (Exception exception) {
      LOGGER.error(exception.getMessage(), exception);
    }
  }

  private static void waitForThreadsToFinishExecution(ExecutorService executorService) {
    try {
      if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
        executorService.shutdownNow();
      }
    } catch (InterruptedException exception) {
      LOGGER.error(exception.getMessage(), exception);
      executorService.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

  private static void deleteUpdatedCustomerOptimisticLockExample(
      final CustomerDataMapper customerDataMapper) {
    LOGGER.info("EXAMPLE OF OPTIMISTIC OFFLINE LOCK FOR DELETE OPERATION ON UPDATED CUSTOMER.");
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    executorService.execute(
        () -> updateCustomer(customerDataMapper, 2L, "Emmett", "Marshall", 2000L));
    executorService.execute(() -> deleteCustomer(customerDataMapper, 2L, 4000L));
    waitForThreadsToFinishExecution(executorService);
    LOGGER.info("Current customer with id=2 in DB: " + customerDataMapper.find(2L));
  }

  private static void deleteCustomer(
      final CustomerDataMapper customerDataMapper, long id, long sleepTime) {
    Customer customer = customerDataMapper.find(id);
    LOGGER.info(
        Thread.currentThread().getName() + " found customer with id=" + id + ": " + customer + ".");
    try {
      Thread.sleep(sleepTime);
      customerDataMapper.delete(customer);
      LOGGER.info("Successful delete by " + Thread.currentThread().getName() + ".");
    } catch (Exception exception) {
      LOGGER.error(exception.getMessage(), exception);
    }
  }

  private static void updateDeletedCustomerOptimisticLockExample(
      final CustomerDataMapper customerDataMapper) {
    LOGGER.info("EXAMPLE OF OPTIMISTIC OFFLINE LOCK FOR UPDATE OPERATION ON DELETED CUSTOMER.");
    ExecutorService executorService = Executors.newFixedThreadPool(2);
    executorService.execute(() -> updateCustomer(customerDataMapper, 3L, "Wynne", "Badman", 4000L));
    executorService.execute(() -> deleteCustomer(customerDataMapper, 3L, 2000L));
    waitForThreadsToFinishExecution(executorService);
    LOGGER.info("Current customer with id=3 in DB: " + customerDataMapper.find(3L));
  }

  private static void deleteSchema(final DataSource dataSource) {
    Connection connection = null;
    Statement statement = null;
    try {
      connection = dataSource.getConnection();
      statement = connection.createStatement();
      statement.execute(CustomerSchemaSql.DELETE_SCHEMA_SQL);
    } catch (SQLException exception) {
      LOGGER.error(exception.getMessage(), exception);
    } finally {
      closeDbResources(statement, connection);
    }
  }

  private static void closeDbResources(Statement statement, Connection connection) {
    JdbcUtils.closeSilently(statement);
    JdbcUtils.closeSilently(connection);
  }
}
