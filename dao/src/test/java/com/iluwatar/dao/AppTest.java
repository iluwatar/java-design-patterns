package com.iluwatar.dao;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 
 * Application test
 *
 */
public class AppTest {

	@Test
	public void test_AddingRemovingUpdateThroughDAO() {
        List<Customer> inputCustomers = new ArrayList<>();
        inputCustomers.add(new Customer(42, "Joseph", "Jones"));
        inputCustomers.add(new Customer(43, "Georgina", "Jones"));

        CustomerDao customerDao = new CustomerDaoImpl(inputCustomers);

        List<Customer> allCustomers = customerDao.getAllCustomers();
        assertEquals(inputCustomers, allCustomers);

        Customer customer0 = allCustomers.get(0);
        Customer customer1 = allCustomers.get(1);

        //check array size
        assertEquals(2, allCustomers.size());

        //check ids
        assertEquals(42, customer0.getId());
        assertEquals(43, customer1.getId());

        //check names
        assertEquals("Joseph", customer0.getFirstName());
        assertEquals("Jones", customer0.getLastName());
        assertEquals("Georgina", customer1.getFirstName());
        assertEquals("Jones", customer1.getLastName());

        assertEquals(42, customerDao.getCustomerById(42).getId());

        Customer customer2 = new Customer(44, "Alexis", "Berry");
        customerDao.addCustomer(customer2);
        assertEquals(3, allCustomers.size());

        customer2.setFirstName("Daniel");
        customer2.setLastName("Danielson");
        customerDao.updateCustomer(customer2);
        Customer custerById = customerDao.getCustomerById(44);
        assertEquals(44, custerById.getId());
        assertEquals("Daniel", custerById.getFirstName());
        assertEquals("Danielson", custerById.getLastName());


        assertEquals(3, allCustomers.size());

        customerDao.deleteCustomer(customer2);

        assertEquals(2, allCustomers.size());
	}
}