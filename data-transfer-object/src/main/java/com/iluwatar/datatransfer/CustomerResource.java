/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Gopinath Langote
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.iluwatar.datatransfer;

import java.util.List;

/**
 * The resource class which serves customer information.
 * This class act as server in the demo. Which has all customer details.
 */
public class CustomerResource {
  private List<CustomerDto> customers;

  /**
   * @param customers initialize resource with existing customers. Act as database.
   */
  public CustomerResource(List<CustomerDto> customers) {
    this.customers = customers;
  }

  /**
   * @return : all customers in list.
   */
  public List<CustomerDto> getAllCustomers() {
    return customers;
  }

  /**
   * @param customer save new customer to list.
   */
  public void save(CustomerDto customer) {
    customers.add(customer);
  }

  /**
   * @param customerId delete customer with id {@code customerId}
   */
  public void delete(String customerId) {
    customers.removeIf(customer -> customer.getId().equals(customerId));
  }
}