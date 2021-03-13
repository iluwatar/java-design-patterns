/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.datatransfer.customer;

import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * The resource class which serves customer information. This class act as server in the demo. Which
 * has all customer details.
 */
@RequiredArgsConstructor
public class CustomerResource {
  private final List<CustomerDto> customers;

  /**
   * Get all customers.
   *
   * @return : all customers in list.
   */
  public List<CustomerDto> getAllCustomers() {
    return customers;
  }

  /**
   * Save new customer.
   *
   * @param customer save new customer to list.
   */
  public void save(CustomerDto customer) {
    customers.add(customer);
  }

  /**
   * Delete customer with given id.
   *
   * @param customerId delete customer with id {@code customerId}
   */
  public void delete(String customerId) {
    customers.removeIf(customer -> customer.getId().equals(customerId));
  }
}