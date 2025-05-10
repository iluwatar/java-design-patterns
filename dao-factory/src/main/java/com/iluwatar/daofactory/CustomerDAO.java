/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.daofactory;

import java.util.List;
import java.util.Optional;

/**
 * The Data Access Object (DAO) pattern provides an abstraction layer between the application and
 * the database. It encapsulates data access logic, allowing the application to work with domain
 * objects instead of direct database operations.
 *
 * <p>Implementations handle specific storage mechanisms (e.g., in-memory, databases) while keeping
 * client code unchanged.
 *
 * @see H2CustomerDAO
 * @see MongoCustomerDAO
 * @see FlatFileCustomerDAO
 */
public interface CustomerDAO<T> {
  /**
   * Persist the given customer
   *
   * @param customer the customer to persist
   */
  void save(Customer<T> customer);

  /**
   * Update the given customer
   *
   * @param customer the customer to update
   */
  void update(Customer<T> customer);

  /**
   * Delete the customer with the given id
   *
   * @param id the id of the customer to delete
   */
  void delete(T id);

  /**
   * Find all customers
   *
   * @return a list of customers
   */
  List<Customer<T>> findAll();

  /**
   * Find the customer with the given id
   *
   * @param id the id of the customer to find
   * @return the customer with the given id
   */
  Optional<Customer<T>> findById(T id);

  /**
   * Delete the customer schema. After executing the statements, this function will be called to
   * clean up the data and delete the records.
   */
  void deleteSchema();
}
