/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;

/**
 * Tests {@link InMemoryCustomerDao}.
 */
@RunWith(HierarchicalContextRunner.class)
public class InMemoryCustomerDaoTest {

  private InMemoryCustomerDao dao;
  private static final Customer CUSTOMER = new Customer(1, "Freddy", "Krueger");

  @Before
  public void setUp() {
    dao = new InMemoryCustomerDao();
    assertTrue(dao.add(CUSTOMER));
  }

  /**
   * Represents the scenario when the DAO operations are being performed on a non existent 
   * customer.  
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
      assertEquals(nonExistingCustomer, dao.getById(nonExistingCustomer.getId()).get());
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
      assertFalse(dao.getById(nonExistingId).isPresent());
    }

    @Test
    public void retrieveShouldReturnNoCustomer() throws Exception {
      assertFalse(dao.getById(getNonExistingCustomerId()).isPresent());
    }
  }

  /**
   * Represents the scenario when the DAO operations are being performed on an already existing
   * customer.
   */
  public class ExistingCustomer {

    @Test
    public void addingShouldResultInFailureAndNotAffectExistingCustomers() throws Exception {
      boolean result = dao.add(CUSTOMER);

      assertFalse(result);
      assertCustomerCountIs(1);
      assertEquals(CUSTOMER, dao.getById(CUSTOMER.getId()).get());
    }

    @Test
    public void deletionShouldBeSuccessAndCustomerShouldBeNonAccessible() throws Exception {
      boolean result = dao.delete(CUSTOMER);

      assertTrue(result);
      assertCustomerCountIs(0);
      assertFalse(dao.getById(CUSTOMER.getId()).isPresent());
    }

    @Test
    public void updationShouldBeSuccessAndAccessingTheSameCustomerShouldReturnUpdatedInformation() throws Exception {
      final String newFirstname = "Bernard";
      final String newLastname = "Montgomery";
      final Customer customer = new Customer(CUSTOMER.getId(), newFirstname, newLastname);
      boolean result = dao.update(customer);

      assertTrue(result);

      final Customer cust = dao.getById(CUSTOMER.getId()).get();
      assertEquals(newFirstname, cust.getFirstName());
      assertEquals(newLastname, cust.getLastName());
    }
    
    @Test
    public void retriveShouldReturnTheCustomer() {
      Optional<Customer> optionalCustomer = dao.getById(CUSTOMER.getId());
      
      assertTrue(optionalCustomer.isPresent());
      assertEquals(CUSTOMER, optionalCustomer.get());
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
  
  private void assertCustomerCountIs(int count) throws Exception {
    try (Stream<Customer> allCustomers = dao.getAll()) {
      assertTrue(allCustomers.count() == count);
    }
  }
}
