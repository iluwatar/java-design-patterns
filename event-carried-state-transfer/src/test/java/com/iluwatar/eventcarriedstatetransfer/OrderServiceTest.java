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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

  private final EventBus bus = new EventBus();
  private final CustomerService customerService = new CustomerService(bus);
  private final OrderService orderService = new OrderService(bus);

  @Test
  void placesOrdersFromTheReplicatedState() {
    customerService.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));

    var order = orderService.placeOrder("C-1", new BigDecimal("120.00"));

    assertEquals("ORD-1", order.orderId());
    assertEquals("C-1", order.customerId());
    assertEquals("Lisbon", order.shippingAddress());
    assertEquals(new BigDecimal("120.00"), order.amount());
  }

  @Test
  void usesTheLatestReplicatedAddress() {
    customerService.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));
    customerService.changeShippingAddress("C-1", "Porto");

    var order = orderService.placeOrder("C-1", new BigDecimal("10.00"));

    assertEquals("Porto", order.shippingAddress());
  }

  @Test
  void keepsWorkingWhileTheCustomerServiceIsOffline() {
    customerService.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));
    customerService.shutdown();

    assertThrows(IllegalStateException.class, () -> customerService.findCustomer("C-1"));
    var order = orderService.placeOrder("C-1", new BigDecimal("10.00"));

    assertEquals("Lisbon", order.shippingAddress());
  }

  @Test
  void rejectsCustomersUnknownToTheReplica() {
    var thrown =
        assertThrows(
            OrderRejectedException.class, () -> orderService.placeOrder("C-9", BigDecimal.ONE));

    assertTrue(thrown.getMessage().contains("C-9"));
  }

  @Test
  void rejectsOrdersAboveTheReplicatedCreditLimit() {
    customerService.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));

    var thrown =
        assertThrows(
            OrderRejectedException.class,
            () -> orderService.placeOrder("C-1", new BigDecimal("500.01")));

    assertTrue(thrown.getMessage().contains("exceeds credit limit"));
  }

  @Test
  void acceptsOrdersOnceARaisedCreditLimitHasBeenReplicated() {
    customerService.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));
    assertThrows(
        OrderRejectedException.class,
        () -> orderService.placeOrder("C-1", new BigDecimal("900.00")));

    customerService.changeCreditLimit("C-1", new BigDecimal("1500.00"));

    var order = orderService.placeOrder("C-1", new BigDecimal("900.00"));
    assertEquals(new BigDecimal("900.00"), order.amount());
    assertEquals(
        new BigDecimal("1500.00"), orderService.replica().find("C-1").orElseThrow().creditLimit());
  }

  @Test
  void acceptsOrdersExactlyAtTheCreditLimitAndNumbersThemSequentially() {
    customerService.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));

    orderService.placeOrder("C-1", new BigDecimal("1.00"));
    var second = orderService.placeOrder("C-1", new BigDecimal("500.00"));

    assertEquals("ORD-2", second.orderId());
  }

  @Test
  void exposesItsReplica() {
    customerService.register("C-1", "Alice", "Lisbon", new BigDecimal("500.00"));

    assertEquals(1, orderService.replica().size());
  }
}
