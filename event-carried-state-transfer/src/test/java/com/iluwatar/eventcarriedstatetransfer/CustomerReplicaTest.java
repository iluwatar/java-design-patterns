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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class CustomerReplicaTest {

  private final CustomerReplica replica = new CustomerReplica();

  @Test
  void appliesTheFirstEventForACustomer() {
    assertTrue(replica.apply(event(1, state("Lisbon", 1))));

    assertEquals(1, replica.size());
    assertEquals("Lisbon", replica.find("C-1").orElseThrow().shippingAddress());
  }

  @Test
  void appliesNewerVersions() {
    replica.apply(event(1, state("Lisbon", 1)));

    assertTrue(replica.apply(event(2, state("Porto", 2))));

    assertEquals("Porto", replica.find("C-1").orElseThrow().shippingAddress());
    assertEquals(2, replica.find("C-1").orElseThrow().version());
  }

  @Test
  void ignoresOlderVersionsThatArriveLate() {
    replica.apply(event(2, state("Porto", 2)));

    assertFalse(replica.apply(event(1, state("Lisbon", 1))));

    assertEquals("Porto", replica.find("C-1").orElseThrow().shippingAddress());
  }

  @Test
  void ignoresDuplicateDeliveries() {
    replica.apply(event(1, state("Lisbon", 1)));

    assertFalse(replica.apply(event(1, state("Lisbon", 1))));

    assertEquals(1, replica.size());
  }

  @Test
  void unknownCustomersAreAbsent() {
    assertTrue(replica.find("C-9").isEmpty());
    assertEquals(0, replica.size());
  }

  private static CustomerState state(String address, long version) {
    return new CustomerState("C-1", "Alice", address, new BigDecimal("500.00"), version);
  }

  private static CustomerUpdatedEvent event(long id, CustomerState state) {
    return new CustomerUpdatedEvent(id, Instant.EPOCH, state);
  }
}
