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
import lombok.extern.slf4j.Slf4j;

/**
 * The consumer side of the pattern.
 *
 * <p>The order service subscribes its {@link CustomerReplica} to customer events and afterwards
 * answers every order using only that replica. It has no reference to the customer service at all,
 * so it keeps working while the customer service is down and it never adds load to it.
 *
 * <p>Not thread-safe; the demo drives it from a single thread.
 */
@Slf4j
public class OrderService {

  private final CustomerReplica replica = new CustomerReplica();
  private long orderSequence;

  /**
   * Creates the service and subscribes its replica to customer events.
   *
   * @param bus the channel that delivers customer events
   */
  public OrderService(EventBus bus) {
    bus.subscribe(CustomerUpdatedEvent.class, replica::apply);
  }

  /**
   * Places an order using the replicated customer state.
   *
   * @param customerId the ordering customer
   * @param amount the order amount
   * @return the accepted order
   * @throws OrderRejectedException if the customer is unknown to the replica or the amount exceeds
   *     the replicated credit limit
   */
  public Order placeOrder(String customerId, BigDecimal amount) {
    var customer =
        replica
            .find(customerId)
            .orElseThrow(
                () -> new OrderRejectedException("Unknown customer " + customerId + " in replica"));
    if (amount.compareTo(customer.creditLimit()) > 0) {
      throw new OrderRejectedException(
          "Amount "
              + amount
              + " exceeds credit limit "
              + customer.creditLimit()
              + " of "
              + customerId);
    }
    var order = new Order("ORD-" + ++orderSequence, customerId, customer.shippingAddress(), amount);
    LOGGER.info(
        "Accepted {} for {} ({}) shipping to '{}' using replica version {}",
        order.orderId(),
        customerId,
        amount,
        order.shippingAddress(),
        customer.version());
    return order;
  }

  /** The local copy of customer state this service works from. */
  public CustomerReplica replica() {
    return replica;
  }
}
