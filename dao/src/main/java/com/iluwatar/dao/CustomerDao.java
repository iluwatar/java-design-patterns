package com.iluwatar.dao;

import java.util.List;

/**
 * 
 * CustomerDao
 *
 */
public interface CustomerDao {

  List<Customer> getAllCustomers();

  Customer getCustomerById(int id);

  void addCustomer(Customer customer);

  void updateCustomer(Customer customer);

  void deleteCustomer(Customer customer);
}
