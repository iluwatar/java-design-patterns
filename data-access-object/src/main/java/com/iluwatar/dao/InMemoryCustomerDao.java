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
package com.iluwatar.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * An in memory implementation of {@link CustomerDao}, which stores the customers in JVM memory and
 * data is lost when the application exits.
 * <br>
 * This implementation is useful as temporary database or for testing.
 */
public class InMemoryCustomerDao implements CustomerDao {

  private final Map<Integer, Customer> idToCustomer = new HashMap<>();

  /**
   * An eagerly evaluated stream of customers stored in memory.
   */
  @Override
  public Stream<Customer> getAll() {
    return idToCustomer.values().stream();
  }

  @Override
  public Optional<Customer> getById(final int id) {
    return Optional.ofNullable(idToCustomer.get(id));
  }

  @Override
  public boolean add(final Customer customer) {
    if (getById(customer.getId()).isPresent()) {
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
