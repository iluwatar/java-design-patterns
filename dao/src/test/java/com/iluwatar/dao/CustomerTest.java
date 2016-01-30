package com.iluwatar.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

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
