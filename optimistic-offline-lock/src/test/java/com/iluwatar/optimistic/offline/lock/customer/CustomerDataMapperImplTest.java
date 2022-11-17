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
import org.h2.jdbcx.JdbcDataSource;
import org.h2.util.JdbcUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Nested;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

/** Tests {@link CustomerDataMapperImpl}. */
class CustomerDataMapperImplTest {

  private static final String DB_URL = "jdbc:h2:~/optimistic-offline-lock";
  private CustomerDataMapperImpl customerDataMapper;

  @Test
  @DisplayName("is instantiated with data source")
  void isInstantiated() {
    new CustomerDataMapperImpl(new JdbcDataSource());
  }

  @Nested
  @DisplayName("when instantiated")
  class WhenInstantiated {

    @BeforeEach
    void createSchema() throws SQLException {
      Connection connection = null;
      Statement statement = null;

      try {
        connection = DriverManager.getConnection(DB_URL);
        statement = connection.createStatement();
        statement.execute(CustomerSchemaSql.CREATE_SCHEMA_SQL + CustomerSchemaSql.POPULATE_DB);
      } finally {
        closeDbResources(statement, connection);
      }
    }

    @BeforeEach
    void createCustomerDataMapper() {
      var dataSource = new JdbcDataSource();
      dataSource.setURL(DB_URL);
      customerDataMapper = new CustomerDataMapperImpl(dataSource);
    }

    @AfterEach
    void deleteSchema() throws SQLException {
      Connection connection = null;
      Statement statement = null;

      try {
        connection = DriverManager.getConnection(DB_URL);
        statement = connection.createStatement();
        statement.execute(CustomerSchemaSql.DELETE_SCHEMA_SQL);
      } finally {
        closeDbResources(statement, connection);
      }
    }

    @Test
    @DisplayName("find returns existing customer with correct id")
    void find_ExistingIDGiven_ReturnsCustomer() {
      long id = 2;
      Customer customer = customerDataMapper.find(id);

      assertNotNull(customer);
      assertEquals(id, customer.getId());
    }

    @Test
    @DisplayName("find returns null when customer with given id does not exist")
    void find_NonExistingIDGiven_ReturnsNull() {
      long id = 999;
      Customer customer = customerDataMapper.find(id);

      assertNull(customer);
    }

    @Test
    @DisplayName("insert adds non existing customer to DB")
    void insert_NonExistingCustomer_ReturnsTrue() {
      Customer customer = new Customer(4L, "Lilly", "Kaunas");

      assertTrue(customerDataMapper.insert(customer));
    }

    @Test
    @DisplayName("insert does not add customer with existing id to DB")
    void insert_CustomerWithExistingIdGiven_ReturnsFalse() {
      Customer customer = new Customer(3L, "Branimir", "Wilton");

      assertFalse(customerDataMapper.insert(customer));
    }

    @Test
    @DisplayName("update correctly updates existing customer")
    void update_ExistingCustomerGiven_() {
      long id = 1L;
      String newLastName = "Kavanaugh";
      Customer customer = customerDataMapper.find(id);

      assertNotNull(customer);

      customer.setLastName(newLastName);
      customerDataMapper.update(customer);
      Customer currentCustomerInDb = customerDataMapper.find(id);

      assertEquals(id, currentCustomerInDb.getId());
      assertEquals(newLastName, currentCustomerInDb.getLastName());
      assertNotEquals(customer.getModified(), currentCustomerInDb.getModified());
      assertEquals(customer.getVersion() + 1, currentCustomerInDb.getVersion());
    }

    @Test
    @DisplayName(
        "update does not update customer and throws OptimisticLockException when "
            + "customer with old version is given")
    void update_ExistingCustomerWithOldVersionGiven_ThrowsOptimisticLockException() {
      long id = 2L;
      String newLastName = "Green";
      Customer customer = customerDataMapper.find(id);

      assertNotNull(customer);

      Customer customerWithOldVersion =
          new Customer(
              id,
              customer.getFirstName(),
              newLastName,
              customer.getModifiedBy(),
              customer.getModified(),
              customer.getVersion() - 1);

      assertThrows(
          OptimisticLockException.class, () -> customerDataMapper.update(customerWithOldVersion));
      assertEquals(customer, customerDataMapper.find(id));
    }

    @Test
    @DisplayName("delete removes existing customer")
    void delete_ExistingCustomerGiven() {
      long id = 3L;
      Customer customer = customerDataMapper.find(id);

      assertNotNull(customer);

      customerDataMapper.delete(customer);

      assertNull(customerDataMapper.find(id));
    }

    @Test
    @DisplayName(
        "delete does not remove customer and throws OptimisticLockException when "
            + "customer with old version is given")
    void delete_ExistingCustomerWithOldVersionGiven_ThrowsOptimisticLockException() {
      long id = 3L;
      Customer customer = customerDataMapper.find(id);

      assertNotNull(customer);

      Customer customerWithOldVersion =
          new Customer(
              id,
              customer.getFirstName(),
              customer.getLastName(),
              customer.getModifiedBy(),
              customer.getModified(),
              customer.getVersion() - 1);

      assertThrows(
          OptimisticLockException.class, () -> customerDataMapper.delete(customerWithOldVersion));
      assertEquals(customer, customerDataMapper.find(id));
    }
  }

  private void closeDbResources(Statement statement, Connection connection) {
    JdbcUtils.closeSilently(statement);
    JdbcUtils.closeSilently(connection);
  }
}
