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
package com.iluwatar.soa;

import java.util.Map;

/**
 * Enterprise service that owns customer data.
 *
 * <p>Operations:
 *
 * <ul>
 *   <li>{@code getCustomer} with parameter {@code customerId} returns a {@link Customer}
 * </ul>
 */
public class CustomerService implements Service {

  public static final String NAME = "customer";

  private final Map<String, Customer> customers;

  /** Creates the service with a small in-memory customer base. */
  public CustomerService() {
    this(
        Map.of(
            "C-1", new Customer("C-1", "Alice Smith"),
            "C-2", new Customer("C-2", "Bob Jones")));
  }

  /** Creates the service backed by the given customers. */
  public CustomerService(Map<String, Customer> customers) {
    this.customers = Map.copyOf(customers);
  }

  @Override
  public String name() {
    return NAME;
  }

  @Override
  public ServiceResponse handle(ServiceRequest request) {
    return switch (request.operation()) {
      case "getCustomer" -> getCustomer(request.param("customerId", String.class));
      default -> ServiceResponse.error("Unknown operation: " + request.operation());
    };
  }

  private ServiceResponse getCustomer(String customerId) {
    var customer = customers.get(customerId);
    return customer == null
        ? ServiceResponse.error("Unknown customer: " + customerId)
        : ServiceResponse.ok(customer);
  }
}
