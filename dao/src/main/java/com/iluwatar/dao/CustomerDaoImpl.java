/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.dao;

import java.util.List;

/**
 * 
 * The data access object (DAO) is an object that provides an abstract interface to some type of
 * database or other persistence mechanism. By mapping application calls to the persistence layer,
 * DAO provide some specific data operations without exposing details of the database. This
 * isolation supports the Single responsibility principle. It separates what data accesses the
 * application needs, in terms of domain-specific objects and data types (the public interface of
 * the DAO), from how these needs can be satisfied with a specific DBMS, database schema, etc.
 * 
 */
public class CustomerDaoImpl implements CustomerDao {

  // Represents the DB structure for our example so we don't have to managed it ourselves
  // Note: Normally this would be in the form of an actual database and not part of the Dao Impl.
  private List<Customer> customers;

  public CustomerDaoImpl(final List<Customer> customers) {
    this.customers = customers;
  }

  @Override
  public List<Customer> getAllCustomers() {
    return customers;
  }

  @Override
  public Customer getCustomerById(final int id) {
    Customer customer = null;
    for (final Customer cus : getAllCustomers()) {
      if (cus.getId() == id) {
        customer = cus;
        break;
      }
    }
    return customer;
  }

  @Override
  public void addCustomer(final Customer customer) {
    if (getCustomerById(customer.getId()) == null) {
      customers.add(customer);
    }
  }


  @Override
  public void updateCustomer(final Customer customer) {
    if (getAllCustomers().contains(customer)) {
      final int index = getAllCustomers().indexOf(customer);
      getAllCustomers().set(index, customer);
    }
  }

  @Override
  public void deleteCustomer(final Customer customer) {
    getAllCustomers().remove(customer);
  }
}
