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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
// TODO update the javadoc
public class InMemoryCustomerDao implements CustomerDao {

  private Map<Integer, Customer> idToCustomer = new HashMap<>();

  public InMemoryCustomerDao(final List<Customer> customers) {
    customers.stream()
    .forEach((customer) -> idToCustomer.put(customer.getId(), customer));
  }

  @Override
  public Stream<Customer> getAll() {
    return idToCustomer.values().stream();
  }

  @Override
  public Customer getById(final int id) {
    return idToCustomer.get(id);
  }

  @Override
  public boolean add(final Customer customer) {
    if (getById(customer.getId()) != null) {
      return false;
    }
    
    idToCustomer.put(customer.getId(), customer);
    return true;
  }


  @Override
  public boolean update(final Customer customer) {
    return idToCustomer.replace(customer.getId(), customer) != null;
  }

  @Override
  public boolean delete(final Customer customer) {
    return idToCustomer.remove(customer.getId()) != null;
  }
}
