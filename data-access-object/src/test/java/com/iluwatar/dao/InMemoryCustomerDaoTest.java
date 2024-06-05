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
package com.iluwatar.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link InMemoryCustomerDao}.
 */
class InMemoryCustomerDaoTest {

  private InMemoryCustomerDao dao;
  private static final Customer CUSTOMER = new Customer(1, "Freddy", "Krueger");

  @BeforeEach
  void setUp() {
    dao = new InMemoryCustomerDao();
    assertTrue(dao.add(CUSTOMER));
  }

  /**
   * Represents the scenario when the DAO operations are being performed on a non-existent
   * customer.
   */
  @Nested
  class NonExistingCustomer {

    @Test
    void addingShouldResultInSuccess() throws Exception {
      try (var allCustomers = dao.getAll()) {
        assumeTrue(allCustomers.count() == 1);
      }

      final var nonExistingCustomer = new Customer(2, "Robert", "Englund");
      var result = dao.add(nonExistingCustomer);
      assertTrue(result);

      assertCustomerCountIs(2);
      assertEquals(nonExistingCustomer, dao.getById(nonExistingCustomer.getId()).get());
    }

    @Test
    void deletionShouldBeFailureAndNotAffectExistingCustomers() throws Exception {
      final var nonExistingCustomer = new Customer(2, "Robert", "Englund");
      var result = dao.delete(nonExistingCustomer);

      assertFalse(result);
      assertCustomerCountIs(1);
    }

    @Test
    void updateShouldBeFailureAndNotAffectExistingCustomers() {
      final var nonExistingId = getNonExistingCustomerId();
      final var newFirstname = "Douglas";
      final var newLastname = "MacArthur";
      final var customer = new Customer(nonExistingId, newFirstname, newLastname);
      var result = dao.update(customer);

      assertFalse(result);
      assertFalse(dao.getById(nonExistingId).isPresent());
    }

    @Test
    void retrieveShouldReturnNoCustomer() throws Exception {
      assertFalse(dao.getById(getNonExistingCustomerId()).isPresent());
    }
  }

  /**
   * Represents the scenario when the DAO operations are being performed on an already existing
   * customer.
   */
  @Nested
  class ExistingCustomer {

    @Test
    void addingShouldResultInFailureAndNotAffectExistingCustomers() throws Exception {
      var result = dao.add(CUSTOMER);

      assertFalse(result);
      assertCustomerCountIs(1);
      assertEquals(CUSTOMER, dao.getById(CUSTOMER.getId()).get());
    }

    @Test
    void deletionShouldBeSuccessAndCustomerShouldBeNonAccessible() throws Exception {
      var result = dao.delete(CUSTOMER);

      assertTrue(result);
      assertCustomerCountIs(0);
      assertFalse(dao.getById(CUSTOMER.getId()).isPresent());
    }

    @Test
    void updationShouldBeSuccessAndAccessingTheSameCustomerShouldReturnUpdatedInformation() throws
        Exception {
      final var newFirstname = "Bernard";
      final var newLastname = "Montgomery";
      final var customer = new Customer(CUSTOMER.getId(), newFirstname, newLastname);
      var result = dao.update(customer);

      assertTrue(result);

      final var cust = dao.getById(CUSTOMER.getId()).get();
      assertEquals(newFirstname, cust.getFirstName());
      assertEquals(newLastname, cust.getLastName());
    }

    @Test
    void retriveShouldReturnTheCustomer() {
      var optionalCustomer = dao.getById(CUSTOMER.getId());

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
    try (var allCustomers = dao.getAll()) {
      assertEquals(count, allCustomers.count());
    }
  }
}
