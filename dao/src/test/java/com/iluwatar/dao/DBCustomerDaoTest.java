package com.iluwatar.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;

@RunWith(HierarchicalContextRunner.class)
public class DBCustomerDaoTest {
  
  private static final String DB_URL = "jdbc:h2:~/dao:customerdb";
  private DBCustomerDao dao;
  private Customer existingCustomer = new Customer(1, "Freddy", "Krueger");

  @Before
  public void createSchema() throws SQLException {
    try (Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement()) {
      statement.execute("CREATE TABLE CUSTOMERS (ID NUMBER, FNAME VARCHAR(100), LNAME VARCHAR(100))");
    }
  }
  
  @Before
  public void setUp() {
    dao = new DBCustomerDao(DB_URL);
    boolean result = dao.add(existingCustomer);
    assumeTrue(result);
  }
  
  public class NonExistantCustomer {
    
    @Test
    public void addingShouldResultInSuccess() {
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
    public void deletionShouldBeFailureAndNotAffectExistingCustomers() {
      final Customer nonExistingCustomer = new Customer(2, "Robert", "Englund");
      boolean result = dao.delete(nonExistingCustomer);
      
      assertFalse(result);
      assertCustomerCountIs(1);
    }
    
    @Test
    public void updationShouldBeFailureAndNotAffectExistingCustomers() {
      final int nonExistingId = getNonExistingCustomerId();
      final String newFirstname = "Douglas";
      final String newLastname = "MacArthur";
      final Customer customer = new Customer(nonExistingId, newFirstname, newLastname);
      boolean result = dao.update(customer);
      
      assertFalse(result);
      assertNull(dao.getById(nonExistingId));
    }
    
    @Test
    public void retrieveShouldReturnNull() {
      assertNull(dao.getById(getNonExistingCustomerId()));
    }
    
  }
  
  public class ExistingCustomer {
    
    @Test
    public void addingShouldResultInFailureAndNotAffectExistingCustomers() {
      Customer existingCustomer = new Customer(1, "Freddy", "Krueger");
      
      boolean result = dao.add(existingCustomer);
      
      assertFalse(result);
      assertCustomerCountIs(1);
      assertEquals(existingCustomer, dao.getById(existingCustomer.getId()));
    }
    
    @Test
    public void deletionShouldBeSuccessAndCustomerShouldBeNonAccessible() {
      boolean result = dao.delete(existingCustomer);
      
      assertTrue(result);
      assertCustomerCountIs(0);
      assertNull(dao.getById(existingCustomer.getId()));
    }
    
    @Test
    public void updationShouldBeSuccessAndAccessingTheSameCustomerShouldReturnUpdatedInformation() {
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

  @After
  public void deleteSchema() throws SQLException {
    try (Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement = connection.createStatement()) {
      statement.execute("DROP TABLE CUSTOMERS");
    }
  }
  
  private void assertCustomerCountIs(int count) {
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
