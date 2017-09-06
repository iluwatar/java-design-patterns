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

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * tests {@link CustomerResource}.
 */
public class CustomerResourceTest {
  @Test
  public void shouldGetAllCustomers() {
    CustomerDto customer = new CustomerDto("1", "Melody", "Yates");
    List<CustomerDto> customers = new ArrayList<>();
    customers.add(customer);

    CustomerResource customerResource = new CustomerResource(customers);

    List<CustomerDto> allCustomers = customerResource.getAllCustomers();

    assertEquals(allCustomers.size(), 1);
    assertEquals(allCustomers.get(0).getId(), "1");
    assertEquals(allCustomers.get(0).getFirstName(), "Melody");
    assertEquals(allCustomers.get(0).getLastName(), "Yates");
  }

  @Test
  public void shouldSaveCustomer() {
    CustomerDto customer = new CustomerDto("1", "Rita", "Reynolds");
    CustomerResource customerResource = new CustomerResource(new ArrayList<>());

    customerResource.save(customer);

    List<CustomerDto> allCustomers = customerResource.getAllCustomers();
    assertEquals(allCustomers.get(0).getId(), "1");
    assertEquals(allCustomers.get(0).getFirstName(), "Rita");
    assertEquals(allCustomers.get(0).getLastName(), "Reynolds");
  }

  @Test
  public void shouldDeleteCustomer() {
    CustomerDto customer = new CustomerDto("1", "Terry", "Nguyen");
    List<CustomerDto> customers = new ArrayList<>();
    customers.add(customer);

    CustomerResource customerResource = new CustomerResource(customers);

    customerResource.delete(customer.getId());

    List<CustomerDto> allCustomers = customerResource.getAllCustomers();
    assertEquals(allCustomers.size(), 0);
  }

}