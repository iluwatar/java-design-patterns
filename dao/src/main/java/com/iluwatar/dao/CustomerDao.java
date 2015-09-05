package com.iluwatar.dao;

import java.util.List;

/**
 * 
 * CustomerDao
 *
 */
public interface CustomerDao {
	
    List<Customer> getAllCustomers();
    Customer getCustomerById(final int id);
    void addCustomer(final Customer customer);
    void updateCustomer(final Customer customer);
    void deleteCustomer(final Customer customer);
}