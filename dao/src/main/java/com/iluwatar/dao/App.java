package com.iluwatar.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Data Access Object (DAO) is an object that provides an abstract interface to some type of database or other 
 * persistence mechanism. By mapping application calls to the persistence layer, DAO provide some specific data 
 * operations without exposing details of the database. This isolation supports the Single responsibility principle. 
 * It separates what data accesses the application needs, in terms of domain-specific objects and data types 
 * (the public interface of the DAO), from how these needs can be satisfied with a specific DBMS.
 * <p>
 * With the DAO pattern, we can use various method calls to retrieve/add/delete/update data without directly
 * interacting with the data. The below example demonstrates basic CRUD operations: select, add, update, and delete.
 * 
 */
public class App {

	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {

		CustomerDao customerDao = new CustomerDaoImpl(generateSampleCustomers());

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
		return new ArrayList(Arrays.<Customer>asList(
                new Customer(1, "Adam", "Adamson"),
                new Customer(2, "Bob", "Bobson"),
                new Customer(3, "Carl", "Carlson")));
	}
}
