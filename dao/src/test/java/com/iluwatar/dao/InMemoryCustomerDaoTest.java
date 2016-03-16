/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class InMemoryCustomerDaoTest {

  private InMemoryCustomerDao dao;
  private List<Customer> customers;
  private static final Customer CUSTOMER = new Customer(1, "Freddy", "Krueger");

  @Before
  public void setUp() {
    customers = new ArrayList<>();
    customers.add(CUSTOMER);
    dao = new InMemoryCustomerDao(customers);
  }

  @Test
  public void deleteExistingCustomer() {
    Assume.assumeTrue(dao.getAll().count() == 1);

    boolean result = dao.delete(CUSTOMER);
    
    assertTrue(result);
    assertTrue(dao.getAll().count() == 0);
  }

  @Test
  public void deleteNonExistingCustomer() {
    final Customer nonExistingCustomer = new Customer(2, "Robert", "Englund");
    boolean result = dao.delete(nonExistingCustomer);
    
    assertFalse(result);
    assertEquals(1, dao.getAll().count());
  }

  @Test
  public void updateExistingCustomer() {
    final String newFirstname = "Bernard";
    final String newLastname = "Montgomery";
    final Customer customer = new Customer(CUSTOMER.getId(), newFirstname, newLastname);
    boolean result = dao.update(customer);
    
    assertTrue(result);
    final Customer cust = dao.getById(CUSTOMER.getId());
    assertEquals(newFirstname, cust.getFirstName());
    assertEquals(newLastname, cust.getLastName());
  }

  @Test
  public void updateNonExistingCustomer() {
    final int nonExistingId = getNonExistingCustomerId();
    final String newFirstname = "Douglas";
    final String newLastname = "MacArthur";
    final Customer customer = new Customer(nonExistingId, newFirstname, newLastname);
    boolean result = dao.update(customer);
    
    assertFalse(result);
    assertNull(dao.getById(nonExistingId));
    final Customer existingCustomer = dao.getById(CUSTOMER.getId());
    assertEquals(CUSTOMER.getFirstName(), existingCustomer.getFirstName());
    assertEquals(CUSTOMER.getLastName(), existingCustomer.getLastName());
  }

  @Test
  public void addCustomer() {
    final Customer newCustomer = new Customer(3, "George", "Patton");
    boolean result = dao.add(newCustomer);
    
    assertTrue(result);
    assertEquals(2, dao.getAll().count());
  }

  @Test
  public void addAlreadyAddedCustomer() {
    final Customer newCustomer = new Customer(3, "George", "Patton");
    dao.add(newCustomer);
    
    boolean result = dao.add(newCustomer);
    assertFalse(result);
    assertEquals(2, dao.getAll().count());
  }

  @Test
  public void getExistinCustomerById() {
    assertEquals(CUSTOMER, dao.getById(CUSTOMER.getId()));
  }

  @Test
  public void getNonExistingCustomerById() {
    final int nonExistingId = getNonExistingCustomerId();
    
    assertNull(dao.getById(nonExistingId));
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
