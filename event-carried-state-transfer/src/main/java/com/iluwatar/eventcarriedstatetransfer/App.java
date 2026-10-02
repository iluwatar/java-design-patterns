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
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;

/**
 * Event-Carried State Transfer (ECST) is an event-driven pattern in which every event carries the
 * complete state of the entity that changed. Consumers keep their own local copy of that state and
 * serve their requests from it, so they neither call the producer back nor stop working when the
 * producer is unavailable.
 *
 * <p>The building blocks are a producer ({@link CustomerService}) that publishes {@link
 * CustomerUpdatedEvent}s containing the full {@link CustomerState}, a channel ({@link EventBus}),
 * and a consumer ({@link OrderService}) that keeps a {@link CustomerReplica} up to date and reads
 * only from it.
 *
 * <p>The demo registers a customer and changes the address, showing the replica following each
 * event. It then takes the customer service offline and places an order anyway, purely from the
 * replica. A stale event is published to show that the replica ignores it, and an order above the
 * replicated credit limit is rejected. Finally the customer service comes back and raises that
 * credit limit: the new state travels in the event, and the order that was just rejected is
 * accepted from the replica alone.
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line arguments, not used
   */
  public static void main(String[] args) {
    var bus = new EventBus();
    var customerService = new CustomerService(bus);
    var orderService = new OrderService(bus);

    LOGGER.info("--- Step 1: every customer change is published with the full customer state ---");
    customerService.register("C-1", "Alice", "1 Harbour Street, Lisbon", new BigDecimal("500.00"));
    logReplica(orderService, "C-1");
    customerService.changeShippingAddress("C-1", "42 Ocean Avenue, Porto");
    logReplica(orderService, "C-1");

    LOGGER.info(
        "--- Step 2: the customer service goes offline, orders still flow from the replica ---");
    customerService.shutdown();
    lookUpDirectly(customerService, "C-1");
    var order = orderService.placeOrder("C-1", new BigDecimal("120.00"));
    LOGGER.info(
        "{} ships to '{}' without asking the customer service",
        order.orderId(),
        order.shippingAddress());

    LOGGER.info("--- Step 3: a stale event arrives late and the replica ignores it ---");
    var stale =
        new CustomerUpdatedEvent(
            99,
            Instant.now(),
            new CustomerState(
                "C-1", "Alice", "1 Harbour Street, Lisbon", new BigDecimal("500.00"), 1));
    bus.publish(stale);
    logReplica(orderService, "C-1");

    LOGGER.info("--- Step 4: the replica is enough to enforce business rules ---");
    tryToOrder(orderService, "C-1", new BigDecimal("900.00"));
    tryToOrder(orderService, "C-2", new BigDecimal("10.00"));

    LOGGER.info(
        "--- Step 5: a new credit limit travels in the event and unblocks the rejected order ---");
    customerService.restart();
    customerService.changeCreditLimit("C-1", new BigDecimal("1500.00"));
    logReplica(orderService, "C-1");
    tryToOrder(orderService, "C-1", new BigDecimal("900.00"));
  }

  /**
   * The call a consumer would have to make without the pattern; it fails while the producer is
   * down.
   */
  static void lookUpDirectly(CustomerService customerService, String customerId) {
    try {
      customerService
          .findCustomer(customerId)
          .ifPresent(
              state ->
                  LOGGER.info(
                      "Direct lookup of {} answered version {}", customerId, state.version()));
    } catch (IllegalStateException e) {
      LOGGER.warn("Direct lookup of {} failed: {}", customerId, e.getMessage());
    }
  }

  private static void logReplica(OrderService orderService, String customerId) {
    orderService
        .replica()
        .find(customerId)
        .ifPresent(
            state ->
                LOGGER.info(
                    "Order service replica: {} version {} at '{}' with limit {}",
                    state.customerId(),
                    state.version(),
                    state.shippingAddress(),
                    state.creditLimit()));
  }

  /** Places an order and logs the outcome instead of failing the demo on a rejection. */
  static void tryToOrder(OrderService orderService, String customerId, BigDecimal amount) {
    try {
      var order = orderService.placeOrder(customerId, amount);
      LOGGER.info("Order {} accepted", order.orderId());
    } catch (OrderRejectedException e) {
      LOGGER.warn("Order rejected: {}", e.getMessage());
    }
  }
}
