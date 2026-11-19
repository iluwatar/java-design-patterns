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
package com.iluwatar.compositionroot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test acts as its own composition root: it wires the same classes as {@link App}, but with a
 * recording notifier in place of the logging one.
 */
class OrderServiceTest {

  private final List<String> sentMessages = new ArrayList<>();
  private OrderRepository repository;
  private OrderService orderService;
  private SalesReport salesReport;

  @BeforeEach
  void compose() {
    var catalog = new InMemoryProductCatalog(Map.of("BOOK-001", 2_500L, "MUG-002", 1_200L));
    repository = new InMemoryOrderRepository();
    Notifier recordingNotifier = (customer, message) -> sentMessages.add(customer + ": " + message);
    orderService = new OrderService(new PricingService(catalog), repository, recordingNotifier);
    salesReport = new SalesReport(repository);
  }

  @Test
  void placesPricesAndStoresOrder() {
    var order = orderService.placeOrder("alice", "BOOK-001", 2);

    assertEquals(new Order(1, "alice", "BOOK-001", 2, 5_000L), order);
    assertEquals(List.of(order), repository.findAll());
  }

  @Test
  void notifiesCustomer() {
    orderService.placeOrder("alice", "BOOK-001", 1);

    assertEquals(1, sentMessages.size());
    assertTrue(sentMessages.getFirst().startsWith("alice: Order #1 confirmed"));
  }

  @Test
  void numbersOrdersInSequence() {
    orderService.placeOrder("alice", "BOOK-001", 1);
    var second = orderService.placeOrder("bob", "MUG-002", 1);

    assertEquals(2, second.id());
  }

  @Test
  void reportSeesOrdersThroughSharedRepository() {
    orderService.placeOrder("alice", "BOOK-001", 2);
    orderService.placeOrder("bob", "MUG-002", 3);

    assertEquals(2, salesReport.orderCount());
    assertEquals(8_600L, salesReport.revenueInCents());
  }

  @Test
  void failedPricingStoresNothingAndSendsNothing() {
    assertThrows(IllegalArgumentException.class, () -> orderService.placeOrder("alice", "NOPE", 1));

    assertTrue(repository.findAll().isEmpty());
    assertTrue(sentMessages.isEmpty());
  }
}
