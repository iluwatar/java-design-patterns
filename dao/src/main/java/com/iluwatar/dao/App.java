package com.iluwatar.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * With the DAO pattern, we can use various method calls to retrieve/add/delete/update data without directly
 * interacting with the data. The below example demonstrates basic operations(CRUD): select, add, update, and delete.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {

		CustomerDaoImpl customerDao = new CustomerDaoImpl(generateSampleCustomers());

		System.out.println("customerDao.getAllCustomers(): " + customerDao.getAllCustomers());
		System.out.println("customerDao.getCusterById(2): " + customerDao.getCusterById(2));

		Customer customer = new Customer(4, "Dan", "Danson");
		customerDao.addCustomer(customer);

		System.out.println("customerDao.getAllCustomers(): " + customerDao.getAllCustomers());

		customer.setFirstName("Daniel");
		customer.setLastName("Danielson");
		customerDao.updateCustomer(customer);

		System.out.println("customerDao.getAllCustomers(): " + customerDao.getAllCustomers());

		customerDao.deleteCustomer(customer);

		System.out.println("customerDao.getAllCustomers(): " + customerDao.getAllCustomers());
	}

	/**
	 * Generate customers
	 * @return list of customers
	 */
	public static List<Customer> generateSampleCustomers() {
		Customer customer1 = new Customer(1, "Adam", "Adamson");
		Customer customer2 = new Customer(2, "Bob", "Bobson");
		Customer customer3 = new Customer(3, "Carl", "Carlson");

		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer3);
		return customers;
	}
}
