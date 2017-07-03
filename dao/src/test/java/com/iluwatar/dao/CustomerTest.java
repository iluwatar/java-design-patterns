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
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link Customer}.
 */
public class CustomerTest {

  private Customer customer;
  private static final int ID = 1;
  private static final String FIRSTNAME = "Winston";
  private static final String LASTNAME = "Churchill";

  @Before
  public void setUp() {
    customer = new Customer(ID, FIRSTNAME, LASTNAME);
  }

  @Test
  public void getAndSetId() {
    final int newId = 2;
    customer.setId(newId);
    assertEquals(newId, customer.getId());
  }

  @Test
  public void getAndSetFirstname() {
    final String newFirstname = "Bill";
    customer.setFirstName(newFirstname);
    assertEquals(newFirstname, customer.getFirstName());
  }

  @Test
  public void getAndSetLastname() {
    final String newLastname = "Clinton";
    customer.setLastName(newLastname);
    assertEquals(newLastname, customer.getLastName());
  }

  @Test
  public void notEqualWithDifferentId() {
    final int newId = 2;
    final Customer otherCustomer = new Customer(newId, FIRSTNAME, LASTNAME);
    assertNotEquals(customer, otherCustomer);
    assertNotEquals(customer.hashCode(), otherCustomer.hashCode());
  }

  @Test
  public void equalsWithSameObjectValues() {
    final Customer otherCustomer = new Customer(ID, FIRSTNAME, LASTNAME);
    assertEquals(customer, otherCustomer);
    assertEquals(customer.hashCode(), otherCustomer.hashCode());
  }

  @Test
  public void equalsWithSameObjects() {
    assertEquals(customer, customer);
    assertEquals(customer.hashCode(), customer.hashCode());
  }

  @Test
  public void testToString() {
    final StringBuffer buffer = new StringBuffer();
    buffer.append("Customer{id=")
            .append("" + customer.getId())
            .append(", firstName='")
            .append(customer.getFirstName())
            .append("\', lastName='")
            .append(customer.getLastName() + "\'}");
    assertEquals(buffer.toString(), customer.toString());
  }
}
