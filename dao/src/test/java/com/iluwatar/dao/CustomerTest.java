package com.iluwatar.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
	public void getIndex() {
		assertEquals(ID, customer.getId());
	}
	
	@Test
	public void getFirstname() {
		assertEquals(FIRSTNAME, customer.getFirstName());
	}
	
	@Test
	public void getLastname() {
		assertEquals(LASTNAME, customer.getLastName());
	}
	
	@Test
	public void setIndex() {
		final int newId = 2;
		customer.setId(newId);
		assertEquals(newId, customer.getId());
	}
	
	@Test
	public void setFirstname() {
		final String newFirstname = "Bill";
		customer.setFirstName(newFirstname);
		assertEquals(newFirstname, customer.getFirstName());
	}
	
	@Test
	public void setLastname() {
		final String newLastname = "Clinton";
		customer.setLastName(newLastname);
		assertEquals(newLastname, customer.getLastName());
	}
	
	@Test
	public void equalsWithDifferentId() {
		final int newId = 2;
		final Customer otherCustomer = new Customer(newId, FIRSTNAME, LASTNAME);
		assertFalse(customer.equals(otherCustomer));
	}
	
	@Test
	public void equalsWithSameObjects() {
		final Customer otherCustomer = new Customer(ID, FIRSTNAME, LASTNAME);
		assertTrue(customer.equals(otherCustomer));
	}
	
	@Test
	public void testHashCode() {
		assertTrue(customer.hashCode() > 0);
	}
	
	@Test
	public void testToString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Customer{id=");
        buffer.append(""+customer.getId());
        buffer.append(", firstName='");
        buffer.append(customer.getFirstName());
        buffer.append("\', lastName='");
        buffer.append(customer.getLastName() + "\'}");
		assertEquals(buffer.toString(), customer.toString());
	}
}
