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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.iluwatar.datatransfer.customer.CustomerDto;
import com.iluwatar.datatransfer.customer.CustomerResource;
import org.junit.jupiter.api.Test;

/**
 * tests {@link CustomerResource}.
 */
class CustomerResourceTest {

  @Test
  void shouldGetAllCustomers() {
    var customers = List.of(new CustomerDto("1", "Melody", "Yates"));
    var customerResource = new CustomerResource(customers);
    var allCustomers = customerResource.getAllCustomers();

    assertEquals(1, allCustomers.size());
    assertEquals("1", allCustomers.get(0).getId());
    assertEquals("Melody", allCustomers.get(0).getFirstName());
    assertEquals("Yates", allCustomers.get(0).getLastName());
  }

  @Test
  void shouldSaveCustomer() {
    var customer = new CustomerDto("1", "Rita", "Reynolds");
    var customerResource = new CustomerResource(new ArrayList<>());

    customerResource.save(customer);

    var allCustomers = customerResource.getAllCustomers();
    assertEquals("1", allCustomers.get(0).getId());
    assertEquals("Rita", allCustomers.get(0).getFirstName());
    assertEquals("Reynolds", allCustomers.get(0).getLastName());
  }

  @Test
  void shouldDeleteCustomer() {
    var customer = new CustomerDto("1", "Terry", "Nguyen");
    var customers = new ArrayList<>(List.of(customer));
    var customerResource = new CustomerResource(customers);

    customerResource.delete(customer.getId());

    var allCustomers = customerResource.getAllCustomers();
    assertTrue(allCustomers.isEmpty());
  }

}