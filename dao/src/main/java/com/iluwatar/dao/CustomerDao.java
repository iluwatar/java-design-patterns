package com.iluwatar.dao;

import java.util.List;

/**
 * 
 * CustomerDao
 *
 */
public interface CustomerDao {
	
    public List<Customer> getAllCustomers();
    public Customer getCusterById(int id);
    public void addCustomer(Customer customer);
    public void updateCustomer(Customer customer);
    public void deleteCustomer(Customer customer);
}