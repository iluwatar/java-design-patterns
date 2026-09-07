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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * The producer side of the pattern: the system of record for customers.
 *
 * <p>Every change to a customer is applied to the authoritative store and then announced with a
 * {@link CustomerUpdatedEvent} that carries the customer's complete new state. Consumers never need
 * to query this service to act on the change, which is demonstrated by taking it offline in the
 * demo while orders keep flowing.
 *
 * <p>Not thread-safe; the demo drives it from a single thread.
 */
@Slf4j
public class CustomerService {

  private final Map<String, CustomerState> customers = new LinkedHashMap<>();
  private final EventBus bus;
  private long eventSequence;
  private boolean online = true;

  /**
   * Creates the service.
   *
   * @param bus the channel on which state events are published
   */
  public CustomerService(EventBus bus) {
    this.bus = bus;
  }

  /**
   * Registers a new customer and publishes its initial state.
   *
   * @param customerId the identifier of the customer
   * @param name the customer's name
   * @param shippingAddress the shipping address
   * @param creditLimit the credit limit
   * @return the stored state
   */
  public CustomerState register(
      String customerId, String name, String shippingAddress, BigDecimal creditLimit) {
    var state = new CustomerState(customerId, name, shippingAddress, creditLimit, 1);
    LOGGER.info("Registering customer {} ({})", customerId, name);
    return store(state);
  }

  /**
   * Changes the shipping address of a customer and publishes the new state.
   *
   * @param customerId the identifier of the customer
   * @param newAddress the new shipping address
   * @return the stored state
   */
  public CustomerState changeShippingAddress(String customerId, String newAddress) {
    LOGGER.info("Customer {} moves to {}", customerId, newAddress);
    return store(existing(customerId).withShippingAddress(newAddress));
  }

  /**
   * Changes the credit limit of a customer and publishes the new state.
   *
   * @param customerId the identifier of the customer
   * @param newLimit the new credit limit
   * @return the stored state
   */
  public CustomerState changeCreditLimit(String customerId, BigDecimal newLimit) {
    LOGGER.info("Customer {} gets a credit limit of {}", customerId, newLimit);
    return store(existing(customerId).withCreditLimit(newLimit));
  }

  /**
   * Looks a customer up directly. This is the call consumers would have to make without the
   * pattern, and it fails once the service is offline.
   *
   * @param customerId the identifier of the customer
   * @return the current state, if the customer exists
   * @throws IllegalStateException if the service has been shut down
   */
  public Optional<CustomerState> findCustomer(String customerId) {
    if (!online) {
      throw new IllegalStateException("customer service is offline");
    }
    return Optional.ofNullable(customers.get(customerId));
  }

  /** Simulates an outage: direct queries fail until the service is back. */
  public void shutdown() {
    online = false;
    LOGGER.warn("Customer service is going offline");
  }

  /** Ends the outage: direct queries are answered again. */
  public void restart() {
    online = true;
    LOGGER.info("Customer service is back online");
  }

  /** Whether direct queries are currently answered. */
  public boolean isOnline() {
    return online;
  }

  private CustomerState existing(String customerId) {
    var state = customers.get(customerId);
    if (state == null) {
      throw new IllegalArgumentException("Unknown customer: " + customerId);
    }
    return state;
  }

  private CustomerState store(CustomerState state) {
    customers.put(state.customerId(), state);
    var event = new CustomerUpdatedEvent(++eventSequence, Instant.now(), state);
    LOGGER.info(
        "Publishing event {} with the full state of {} (version {})",
        event.eventId(),
        state.customerId(),
        state.version());
    bus.publish(event);
    return state;
  }
}
