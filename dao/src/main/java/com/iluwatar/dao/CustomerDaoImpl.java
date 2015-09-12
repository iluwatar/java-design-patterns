package com.iluwatar.dao;

import java.util.List;

/**
 * 
 * The data access object (DAO) is an object that provides an abstract interface to some type of database or other persistence mechanism.
 * By mapping application calls to the persistence layer, DAO provide some specific data operations without exposing details of the database.
 * This isolation supports the Single responsibility principle. It separates what data accesses the application needs, in terms of
 * domain-specific objects and data types (the public interface of the DAO), from how these needs can be satisfied with a specific DBMS,
 * database schema, etc.
 * 
 */
public class CustomerDaoImpl implements CustomerDao {

    // Represents the DB structure for our example so we don't have to managed it ourselves
    // Note: Normally this would be in the form of an actual database and not part of the Dao Impl.
    private List<Customer> customers;

    public CustomerDaoImpl(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @Override
    public Customer getCusterById(int id) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getId() == id) {
                return customers.get(i);
            }
        }
        // No customer found
        return null;
    }

    @Override
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }


    @Override
    public void updateCustomer(Customer customer) {
        if (customers.contains(customer)) {
            customers.set(customers.indexOf(customer), customer);
        }
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
    }
}