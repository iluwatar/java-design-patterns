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
package com.iluwatar.activerecord;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerCrudTest extends BaseTest {

  @Test
  void shouldExecuteCustomerLifecycle() {
    // save two customers
    Customer customer = new Customer();
    customer.setId(1L);
    customer.setCustomerNumber("C123");
    customer.setFirstName("John");
    customer.setLastName("Smith");

    customer.save(Customer.class);

    Customer customerTwo = new Customer();
    customerTwo.setId(2L);
    customerTwo.setCustomerNumber("C798237");
    customerTwo.setFirstName("SecondCustomerName");
    customerTwo.setLastName("SecondCustomerLastName");

    customerTwo.save(Customer.class);

    // find all the customers
    List<Customer> customers = Customer.findAll(Customer.class);
    Customer firstCustomer = customers.get(0);
    assertEquals(2, customers.size());
    assertEquals(1L, firstCustomer.getId());
    assertEquals("C123", firstCustomer.getCustomerNumber());
    assertEquals("John", firstCustomer.getFirstName());
    assertEquals("Smith", firstCustomer.getLastName());

    // find the second customer
    Customer secondCustomer = Customer.findById(2L, Customer.class);
    assertEquals(2L, secondCustomer.getId());
    assertEquals("C798237", secondCustomer.getCustomerNumber());
    assertEquals("SecondCustomerName", secondCustomer.getFirstName());
    assertEquals("SecondCustomerLastName", secondCustomer.getLastName());
  }
}
