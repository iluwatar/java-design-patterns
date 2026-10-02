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
package com.iluwatar.eventcarriedstatetransfer;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The complete state of a customer as the customer service knows it.
 *
 * <p>Every {@link CustomerUpdatedEvent} carries one of these, so a consumer that receives the event
 * has everything it needs to serve its own requests without calling the customer service back. The
 * {@code version} grows with every change and lets consumers recognise stale or duplicated events.
 *
 * @param customerId the identifier of the customer
 * @param name the customer's name
 * @param shippingAddress where orders for this customer are shipped
 * @param creditLimit the maximum order amount the customer may place
 * @param version monotonically increasing change counter, starts at 1
 */
public record CustomerState(
    String customerId, String name, String shippingAddress, BigDecimal creditLimit, long version) {

  /** Validates the state. */
  public CustomerState {
    Objects.requireNonNull(customerId, "customerId");
    Objects.requireNonNull(name, "name");
    Objects.requireNonNull(shippingAddress, "shippingAddress");
    Objects.requireNonNull(creditLimit, "creditLimit");
    if (version < 1) {
      throw new IllegalArgumentException("version must be at least 1");
    }
  }

  /**
   * Returns a copy with a new shipping address and the next version.
   *
   * @param newAddress the new shipping address
   * @return the updated state
   */
  public CustomerState withShippingAddress(String newAddress) {
    return new CustomerState(customerId, name, newAddress, creditLimit, version + 1);
  }

  /**
   * Returns a copy with a new credit limit and the next version.
   *
   * @param newLimit the new credit limit
   * @return the updated state
   */
  public CustomerState withCreditLimit(BigDecimal newLimit) {
    return new CustomerState(customerId, name, shippingAddress, newLimit, version + 1);
  }
}
