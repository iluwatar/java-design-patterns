package com.iluwatar.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 
 * With the DAO pattern, we can use various method calls to retrieve/add/delete/update data without directly
 * interacting with the data. The below example demonstrates basic operations(CRUD): select, add, update, and delete.
 * 
 */
public class App {

	private static Logger LOGGER = Logger.getLogger(App.class);

	/**
	 * Program entry point.
	 * 
	 * @param args command line args.
	 */
	public static void main(final String[] args) {
		final CustomerDaoImpl customerDao = new CustomerDaoImpl(generateSampleCustomers());
		LOGGER.info("customerDao.getAllCustomers(): " + customerDao.getAllCustomers());
		LOGGER.info("customerDao.getCusterById(2): " + customerDao.getCustomerById(2));
		final Customer customer = new Customer(4, "Dan", "Danson");
		customerDao.addCustomer(customer);
		LOGGER.info("customerDao.getAllCustomers(): " + customerDao.getAllCustomers());
		customer.setFirstName("Daniel");
		customer.setLastName("Danielson");
		customerDao.updateCustomer(customer);
		LOGGER.info("customerDao.getAllCustomers(): " + customerDao.getAllCustomers());
		customerDao.deleteCustomer(customer);
		LOGGER.info("customerDao.getAllCustomers(): " + customerDao.getAllCustomers());
	}

	/**
	 * Generate customers.
	 * 
	 * @return list of customers.
	 */
	public static List<Customer> generateSampleCustomers() {
		final Customer customer1 = new Customer(1, "Adam", "Adamson");
		final Customer customer2 = new Customer(2, "Bob", "Bobson");
		final Customer customer3 = new Customer(3, "Carl", "Carlson");
		final List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);
		return customers;
	}
}
