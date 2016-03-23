package com.iluwatar.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import de.bechte.junit.runners.context.HierarchicalContextRunner;

/**
 * Tests {@link DbCustomerDao}.
 */
@RunWith(HierarchicalContextRunner.class)
public class DbCustomerDaoTest {

  private static final String DB_URL = "jdbc:h2:~/dao:customerdb";
  private DbCustomerDao dao;
  private Customer existingCustomer = new Customer(1, "Freddy", "Krueger");

  /**
   * Creates customers schema.
   * @throws SQLException if there is any error while creating schema.
   */
  @Before
  public void createSchema() throws SQLException {
    try (Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement()) {
      statement.execute("CREATE TABLE CUSTOMERS (ID NUMBER, FNAME VARCHAR(100),"
          + " LNAME VARCHAR(100))");
    }
  }

  /**
   * Represents the scenario where DB connectivity is present.
   */
  public class ConnectionSuccess {

    /**
     * Setup for connection success scenario.
     * @throws Exception if any error occurs.
     */
    @Before
    public void setUp() throws Exception {
      JdbcDataSource dataSource = new JdbcDataSource();
      dataSource.setURL(DB_URL);
      dao = new DbCustomerDao(dataSource);
      boolean result = dao.add(existingCustomer);
      assertTrue(result);
    }

    /**
     * Represents the scenario when DAO operations are being performed on a non existing customer.
     */
    public class NonExistingCustomer {

      @Test
      public void addingShouldResultInSuccess() throws Exception {
        try (Stream<Customer> allCustomers = dao.getAll()) {
          assumeTrue(allCustomers.count() == 1);
        }

        final Customer nonExistingCustomer = new Customer(2, "Robert", "Englund");
        boolean result = dao.add(nonExistingCustomer);
        assertTrue(result);

        assertCustomerCountIs(2);
        assertEquals(nonExistingCustomer, dao.getById(nonExistingCustomer.getId()));
      }

      @Test
      public void deletionShouldBeFailureAndNotAffectExistingCustomers() throws Exception {
        final Customer nonExistingCustomer = new Customer(2, "Robert", "Englund");
        boolean result = dao.delete(nonExistingCustomer);

        assertFalse(result);
        assertCustomerCountIs(1);
      }

      @Test
      public void updationShouldBeFailureAndNotAffectExistingCustomers() throws Exception {
        final int nonExistingId = getNonExistingCustomerId();
        final String newFirstname = "Douglas";
        final String newLastname = "MacArthur";
        final Customer customer = new Customer(nonExistingId, newFirstname, newLastname);
        boolean result = dao.update(customer);

        assertFalse(result);
        assertNull(dao.getById(nonExistingId));
      }

      @Test
      public void retrieveShouldReturnNull() throws Exception {
        assertNull(dao.getById(getNonExistingCustomerId()));
      }
    }

    /**
     * Represents a scenario where DAO operations are being performed on an already existing
     * customer.
     *
     */
    public class ExistingCustomer {

      @Test
      public void addingShouldResultInFailureAndNotAffectExistingCustomers() throws Exception {
        Customer existingCustomer = new Customer(1, "Freddy", "Krueger");

        boolean result = dao.add(existingCustomer);

        assertFalse(result);
        assertCustomerCountIs(1);
        assertEquals(existingCustomer, dao.getById(existingCustomer.getId()));
      }

      @Test
      public void deletionShouldBeSuccessAndCustomerShouldBeNonAccessible() throws Exception {
        boolean result = dao.delete(existingCustomer);

        assertTrue(result);
        assertCustomerCountIs(0);
        assertNull(dao.getById(existingCustomer.getId()));
      }

      @Test
      public void updationShouldBeSuccessAndAccessingTheSameCustomerShouldReturnUpdatedInformation() throws Exception {
        final String newFirstname = "Bernard";
        final String newLastname = "Montgomery";
        final Customer customer = new Customer(existingCustomer.getId(), newFirstname, newLastname);
        boolean result = dao.update(customer);

        assertTrue(result);

        final Customer cust = dao.getById(existingCustomer.getId());
        assertEquals(newFirstname, cust.getFirstName());
        assertEquals(newLastname, cust.getLastName());
      }
    }
  }

  /**
   * Represents a scenario where DB connectivity is not present due to network issue, or
   * DB service unavailable.
   * 
   */
  public class ConnectivityIssue {
    
    private static final String EXCEPTION_CAUSE = "Connection not available";
    @Rule public ExpectedException exception = ExpectedException.none();
    
    /**
     * setup a connection failure scenario.
     * @throws SQLException if any error occurs.
     */
    @Before
    public void setUp() throws SQLException {
      dao = new DbCustomerDao(mockedDatasource());
      exception.expect(Exception.class);
      exception.expectMessage(EXCEPTION_CAUSE);
    }
    
    private DataSource mockedDatasource() throws SQLException {
      DataSource mockedDataSource = mock(DataSource.class);
      Connection mockedConnection = mock(Connection.class);
      SQLException exception = new SQLException(EXCEPTION_CAUSE);
      doThrow(exception).when(mockedConnection).prepareStatement(Mockito.anyString());
      doReturn(mockedConnection).when(mockedDataSource).getConnection();
      return mockedDataSource;
    }

    @Test
    public void addingACustomerFailsWithExceptionAsFeedbackToClient() throws Exception {
      dao.add(new Customer(2, "Bernard", "Montgomery"));
    }
    
    @Test
    public void deletingACustomerFailsWithExceptionAsFeedbackToTheClient() throws Exception {
      dao.delete(existingCustomer);
    }
    
    @Test
    public void updatingACustomerFailsWithFeedbackToTheClient() throws Exception {
      final String newFirstname = "Bernard";
      final String newLastname = "Montgomery";
      
      dao.update(new Customer(existingCustomer.getId(), newFirstname, newLastname));
    }
    
    @Test
    public void retrievingACustomerByIdReturnsNull() throws Exception {
      dao.getById(existingCustomer.getId());
    }
    
    @Test
    public void retrievingAllCustomersReturnsAnEmptyStream() throws Exception {
      dao.getAll();
    }

  }

  /**
   * Delete customer schema for fresh setup per test.
   * @throws SQLException if any error occurs.
   */
  @After
  public void deleteSchema() throws SQLException {
    try (Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement()) {
      statement.execute("DROP TABLE CUSTOMERS");
    }
  }

  private void assertCustomerCountIs(int count) throws Exception {
    try (Stream<Customer> allCustomers = dao.getAll()) {
      assertTrue(allCustomers.count() == count);
    }
  }


  /**
   * An arbitrary number which does not correspond to an active Customer id.
   * 
   * @return an int of a customer id which doesn't exist
   */
  private int getNonExistingCustomerId() {
    return 999;
  }
}
