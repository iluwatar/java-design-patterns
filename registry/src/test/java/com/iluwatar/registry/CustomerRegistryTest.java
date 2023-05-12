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
package com.iluwatar.registry;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CustomerRegistryTest {

  private static CustomerRegistry customerRegistry;

  @BeforeAll
  public static void setUp() {
    customerRegistry = CustomerRegistry.getInstance();
  }

  @Test
  void shouldBeAbleToAddAndQueryCustomerObjectFromRegistry() {
    Customer john = new Customer("1", "john");
    Customer julia = new Customer("2", "julia");

    customerRegistry.addCustomer(john);
    customerRegistry.addCustomer(julia);

    Customer customerWithId1 = customerRegistry.getCustomer("1");
    assertNotNull(customerWithId1);
    assertEquals("1", customerWithId1.id());
    assertEquals("john", customerWithId1.name());

    Customer customerWithId2 = customerRegistry.getCustomer("2");
    assertNotNull(customerWithId2);
    assertEquals("2", customerWithId2.id());
    assertEquals("julia", customerWithId2.name());
  }

  @Test
  void shouldReturnNullWhenQueriedCustomerIsNotInRegistry() {
    Customer customerWithId5 = customerRegistry.getCustomer("5");
    assertNull(customerWithId5);
  }
}
