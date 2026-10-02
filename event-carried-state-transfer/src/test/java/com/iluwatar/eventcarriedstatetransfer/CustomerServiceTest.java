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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerServiceTest {

  private final EventBus bus = new EventBus();
  private final List<CustomerUpdatedEvent> published = new ArrayList<>();
  private final CustomerService service = new CustomerService(bus);

  @BeforeEach
  void subscribe() {
    bus.subscribe(CustomerUpdatedEvent.class, published::add);
  }

  @Test
  void registrationPublishesTheFullInitialState() {
    var state = service.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));

    assertEquals(1, published.size());
    var event = published.get(0);
    assertEquals(1, event.eventId());
    assertEquals(state, event.state());
    assertEquals(1, event.state().version());
    assertEquals("Lisbon", event.state().shippingAddress());
    assertEquals(new BigDecimal("500.00"), event.state().creditLimit());
  }

  @Test
  void everyChangePublishesANewVersionWithTheWholeState() {
    service.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));
    service.changeShippingAddress("C-1", "Porto");
    service.changeCreditLimit("C-1", new BigDecimal("900.00"));

    assertEquals(
        List.of(1L, 2L, 3L), published.stream().map(CustomerUpdatedEvent::eventId).toList());
    var latest = published.get(2).state();
    assertEquals(3, latest.version());
    assertEquals("Porto", latest.shippingAddress());
    assertEquals(new BigDecimal("900.00"), latest.creditLimit());
    assertEquals("Alice", latest.name());
  }

  @Test
  void rejectsChangesToUnknownCustomers() {
    assertThrows(IllegalArgumentException.class, () -> service.changeShippingAddress("C-9", "x"));
    assertThrows(
        IllegalArgumentException.class, () -> service.changeCreditLimit("C-9", BigDecimal.TEN));
    assertTrue(published.isEmpty());
  }

  @Test
  void answersDirectLookupsWhileOnline() {
    service.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));

    assertTrue(service.isOnline());
    assertEquals("Alice", service.findCustomer("C-1").orElseThrow().name());
    assertTrue(service.findCustomer("C-9").isEmpty());
  }

  @Test
  void refusesDirectLookupsWhenOffline() {
    service.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));
    service.shutdown();

    assertFalse(service.isOnline());
    var thrown = assertThrows(IllegalStateException.class, () -> service.findCustomer("C-1"));
    assertEquals("customer service is offline", thrown.getMessage());
  }

  @Test
  void answersDirectLookupsAgainAfterARestart() {
    service.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));
    service.shutdown();

    service.restart();

    assertTrue(service.isOnline());
    assertEquals("Alice", service.findCustomer("C-1").orElseThrow().name());
  }
}
