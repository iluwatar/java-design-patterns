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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * The consumer's local copy of customer state, fed exclusively by {@link CustomerUpdatedEvent}s.
 *
 * <p>Because each event carries the full state, applying one is a simple upsert. The version
 * carried by the state guards against events that arrive late or twice: an event is ignored unless
 * its version is newer than what the replica already holds.
 *
 * <p>Not thread-safe; the demo drives it from a single thread.
 */
@Slf4j
public class CustomerReplica {

  private final Map<String, CustomerState> customers = new LinkedHashMap<>();

  /**
   * Applies an event to the replica.
   *
   * @param event the received event
   * @return {@code true} if the replica was updated, {@code false} if the event was stale
   */
  public boolean apply(CustomerUpdatedEvent event) {
    var incoming = event.state();
    var current = customers.get(incoming.customerId());
    if (current != null && current.version() >= incoming.version()) {
      LOGGER.info(
          "Ignoring event {} for {}: version {} is not newer than replica version {}",
          event.eventId(),
          incoming.customerId(),
          incoming.version(),
          current.version());
      return false;
    }
    customers.put(incoming.customerId(), incoming);
    LOGGER.info(
        "Replica updated from event {}: {} is now at version {} with address '{}' and limit {}",
        event.eventId(),
        incoming.customerId(),
        incoming.version(),
        incoming.shippingAddress(),
        incoming.creditLimit());
    return true;
  }

  /**
   * Reads a customer from the local copy.
   *
   * @param customerId the identifier of the customer
   * @return the replicated state, if any event for the customer has been received
   */
  public Optional<CustomerState> find(String customerId) {
    return Optional.ofNullable(customers.get(customerId));
  }

  /** Number of customers known to the replica. */
  public int size() {
    return customers.size();
  }
}
