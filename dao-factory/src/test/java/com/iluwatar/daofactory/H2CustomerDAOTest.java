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
package com.iluwatar.daofactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/** Tests {@link H2CustomerDAO} */
class H2CustomerDAOTest {
  private static final String DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
  private static final String USER = "sa";
  private static final String PASS = "";
  private static final String CREATE_SCHEMA =
      "CREATE TABLE IF NOT EXISTS customer (id BIGINT PRIMARY KEY, name VARCHAR(255))";
  private static final String DROP_SCHEMA = "DROP TABLE IF EXISTS customer";
  private final Customer<Long> existingCustomer = new Customer<>(1L, "Nguyen");
  private H2CustomerDAO h2CustomerDAO;

  @BeforeEach
  void createSchema() throws SQLException {
    try (var connection = DriverManager.getConnection(DB_URL, USER, PASS);
        var statement = connection.createStatement()) {
      statement.execute(CREATE_SCHEMA);
    }
  }

  @AfterEach
  void deleteSchema() throws SQLException {
    try (var connection = DriverManager.getConnection(DB_URL, USER, PASS);
        var statement = connection.createStatement()) {
      statement.execute(DROP_SCHEMA);
    }
  }

  /** Class test for scenario connect with datasource succeed */
  @Nested
  class ConnectionSucceed {

    @BeforeEach
    void setUp() {
      var dataSource = new JdbcDataSource();
      dataSource.setURL(DB_URL);
      dataSource.setUser(USER);
      dataSource.setPassword(PASS);
      h2CustomerDAO = new H2CustomerDAO(dataSource);
      assertDoesNotThrow(() -> h2CustomerDAO.save(existingCustomer));
      var customer = h2CustomerDAO.findById(existingCustomer.getId());
      assertTrue(customer.isPresent());
      assertEquals(customer.get().getName(), existingCustomer.getName());
      assertEquals(customer.get().getId(), existingCustomer.getId());
    }

    @Nested
    class SaveCustomer {
      @Test
      void givenValidCustomer_whenSaveCustomer_thenAddSucceed() {
        var customer = new Customer<>(2L, "Duc");
        assertDoesNotThrow(() -> h2CustomerDAO.save(customer));
        var customerInDb = h2CustomerDAO.findById(customer.getId());
        assertTrue(customerInDb.isPresent());
        assertEquals(customerInDb.get().getName(), customer.getName());
        assertEquals(customerInDb.get().getId(), customer.getId());
        List<Customer<Long>> customers = h2CustomerDAO.findAll();
        assertEquals(2, customers.size());
      }

      @Test
      void givenIdCustomerDuplicated_whenSaveCustomer_thenThrowException() {
        var customer = new Customer<>(existingCustomer.getId(), "Duc");
        assertThrows(CustomException.class, () -> h2CustomerDAO.save(customer));
        List<Customer<Long>> customers = h2CustomerDAO.findAll();
        assertEquals(1, customers.size());
      }
    }

    @Nested
    class UpdateCustomer {
      @Test
      void givenValidCustomer_whenUpdateCustomer_thenUpdateSucceed() {
        var customerUpdate = new Customer<>(existingCustomer.getId(), "Duc");
        assertDoesNotThrow(() -> h2CustomerDAO.update(customerUpdate));
        var customerInDb = h2CustomerDAO.findById(customerUpdate.getId());
        assertTrue(customerInDb.isPresent());
        assertEquals(customerInDb.get().getName(), customerUpdate.getName());
      }

      @Test
      void givenIdCustomerNotExist_whenUpdateCustomer_thenThrowException() {
        var customerUpdate = new Customer<>(100L, "Duc");
        var customerInDb = h2CustomerDAO.findById(customerUpdate.getId());
        assertTrue(customerInDb.isEmpty());
        assertThrows(CustomException.class, () -> h2CustomerDAO.update(customerUpdate));
      }

      @Test
      void givenNull_whenUpdateCustomer_thenThrowException() {
        assertThrows(CustomException.class, () -> h2CustomerDAO.update(null));
        List<Customer<Long>> customers = h2CustomerDAO.findAll();
        assertEquals(1, customers.size());
      }
    }

    @Nested
    class DeleteCustomer {
      @Test
      void givenValidId_whenDeleteCustomer_thenDeleteSucceed() {
        assertDoesNotThrow(() -> h2CustomerDAO.delete(existingCustomer.getId()));
        var customerInDb = h2CustomerDAO.findById(existingCustomer.getId());
        assertTrue(customerInDb.isEmpty());
        List<Customer<Long>> customers = h2CustomerDAO.findAll();
        assertEquals(0, customers.size());
      }

      @Test
      void givenIdCustomerNotExist_whenDeleteCustomer_thenThrowException() {
        var customerInDb = h2CustomerDAO.findById(100L);
        assertTrue(customerInDb.isEmpty());
        assertThrows(CustomException.class, () -> h2CustomerDAO.delete(100L));
        List<Customer<Long>> customers = h2CustomerDAO.findAll();
        assertEquals(1, customers.size());
        assertEquals(existingCustomer.getName(), customers.get(0).getName());
        assertEquals(existingCustomer.getId(), customers.get(0).getId());
      }

      @Test
      void givenNull_whenDeleteCustomer_thenThrowException() {
        assertThrows(CustomException.class, () -> h2CustomerDAO.delete(null));
        List<Customer<Long>> customers = h2CustomerDAO.findAll();
        assertEquals(1, customers.size());
        assertEquals(existingCustomer.getName(), customers.get(0).getName());
      }
    }

    @Nested
    class FindAllCustomers {
      @Test
      void givenNonCustomerInDb_whenFindAllCustomer_thenReturnEmptyList() {
        assertDoesNotThrow(() -> h2CustomerDAO.delete(existingCustomer.getId()));
        List<Customer<Long>> customers = h2CustomerDAO.findAll();
        assertEquals(0, customers.size());
      }

      @Test
      void givenCustomerExistInDb_whenFindAllCustomer_thenReturnCustomers() {
        List<Customer<Long>> customers = h2CustomerDAO.findAll();
        assertEquals(1, customers.size());
        assertEquals(existingCustomer.getName(), customers.get(0).getName());
        assertEquals(existingCustomer.getId(), customers.get(0).getId());
      }
    }

    @Nested
    class FindCustomerById {
      @Test
      void givenValidId_whenFindById_thenReturnCustomer() {
        var customerInDb = h2CustomerDAO.findById(existingCustomer.getId());
        assertTrue(customerInDb.isPresent());
        assertEquals(existingCustomer.getName(), customerInDb.get().getName());
        assertEquals(existingCustomer.getId(), customerInDb.get().getId());
      }

      @Test
      void givenIdCustomerNotExist_whenFindById_thenReturnEmpty() {
        var customerNotExist = h2CustomerDAO.findById(100L);
        assertTrue(customerNotExist.isEmpty());
      }

      @Test
      void givenNull_whenFindById_thenThrowException() {
        assertThrows(CustomException.class, () -> h2CustomerDAO.findById(null));
      }
    }

    @Nested
    class CreateSchema {
      @Test
      void whenCreateSchema_thenNotThrowException() {
        assertDoesNotThrow(() -> h2CustomerDAO.createSchema());
      }
    }

    @Nested
    class DeleteSchema {
      @Test
      void whenDeleteSchema_thenNotThrowException() {
        assertDoesNotThrow(() -> h2CustomerDAO.deleteSchema());
      }
    }
  }

  /** Class test with scenario connect with data source failed */
  @Nested
  class ConnectionFailed {
    private static final String EXCEPTION_CAUSE = "Connection not available";

    @BeforeEach
    void setUp() throws SQLException {
      h2CustomerDAO = new H2CustomerDAO(mockedDataSource());
    }

    private DataSource mockedDataSource() throws SQLException {
      var mockedDataSource = mock(DataSource.class);
      var mockedConnection = mock(Connection.class);
      var exception = new SQLException(EXCEPTION_CAUSE);
      doThrow(exception).when(mockedConnection).prepareStatement(Mockito.anyString());
      doThrow(exception).when(mockedConnection).createStatement();
      doReturn(mockedConnection).when(mockedDataSource).getConnection();
      return mockedDataSource;
    }

    @Test
    void givenValidCustomer_whenSaveCustomer_thenThrowException() {
      var customer = new Customer<>(2L, "Duc");
      CustomException exception =
          assertThrows(CustomException.class, () -> h2CustomerDAO.save(customer));
      assertEquals(EXCEPTION_CAUSE, exception.getMessage());
    }

    @Test
    void givenValidCustomer_whenUpdateCustomer_thenThrowException() {
      var customerUpdate = new Customer<>(existingCustomer.getId(), "Duc");
      CustomException exception =
          assertThrows(CustomException.class, () -> h2CustomerDAO.update(customerUpdate));
      assertEquals(EXCEPTION_CAUSE, exception.getMessage());
    }

    @Test
    void givenValidId_whenDeleteCustomer_thenThrowException() {
      Long idCustomer = existingCustomer.getId();
      CustomException exception =
          assertThrows(CustomException.class, () -> h2CustomerDAO.delete(idCustomer));
      assertEquals(EXCEPTION_CAUSE, exception.getMessage());
    }

    @Test
    void whenFindAll_thenThrowException() {
      CustomException exception = assertThrows(CustomException.class, h2CustomerDAO::findAll);
      assertEquals(EXCEPTION_CAUSE, exception.getMessage());
    }

    @Test
    void whenFindById_thenThrowException() {
      Long idCustomer = existingCustomer.getId();
      CustomException exception =
          assertThrows(CustomException.class, () -> h2CustomerDAO.findById(idCustomer));
      assertEquals(EXCEPTION_CAUSE, exception.getMessage());
    }

    @Test
    void whenCreateSchema_thenThrowException() {
      CustomException exception = assertThrows(CustomException.class, h2CustomerDAO::createSchema);
      assertEquals(EXCEPTION_CAUSE, exception.getMessage());
    }

    @Test
    void whenDeleteSchema_thenThrowException() {
      CustomException exception = assertThrows(CustomException.class, h2CustomerDAO::deleteSchema);
      assertEquals(EXCEPTION_CAUSE, exception.getMessage());
    }
  }
}
