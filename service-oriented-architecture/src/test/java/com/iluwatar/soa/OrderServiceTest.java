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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

  private static final String CREDENTIAL = "checkout-key";

  private ServiceBus bus;
  private InventoryService inventory;

  @BeforeEach
  void setUp() {
    var registry = new ServiceRegistry();
    bus =
        new ServiceBus(registry, new AccessPolicy(Set.of(CREDENTIAL), Set.of(PaymentService.NAME)));
    inventory = new InventoryService(Map.of("LAPTOP", 2));
    registry.register(new CustomerService());
    registry.register(inventory);
    registry.register(new PaymentService(1000.0));
    registry.register(new OrderService(bus));
  }

  @Test
  void shouldPlaceOrderAndReserveStock() {
    var response = placeOrder("C-1", "LAPTOP", 2, 899.0, CREDENTIAL);

    assertTrue(response.success());
    var confirmation = (OrderService.OrderConfirmation) response.body();
    assertEquals("ORD-1", confirmation.orderId());
    assertEquals("C-1", confirmation.customerId());
    assertEquals("Alice Smith", confirmation.customerName());
    assertEquals("LAPTOP", confirmation.sku());
    assertEquals(2, confirmation.quantity());
    assertEquals("PAY-1", confirmation.paymentReference());
    assertEquals(false, checkStock("LAPTOP", 1));
  }

  @Test
  void shouldRejectOrderForUnknownCustomer() {
    var response = placeOrder("C-9", "LAPTOP", 1, 899.0, CREDENTIAL);

    assertFalse(response.success());
    assertEquals("Order rejected: Unknown customer: C-9", response.message());
    assertEquals(true, checkStock("LAPTOP", 2));
  }

  @Test
  void shouldRejectOrderWhenStockIsInsufficient() {
    var response = placeOrder("C-1", "LAPTOP", 3, 899.0, CREDENTIAL);

    assertFalse(response.success());
    assertEquals("Order rejected: insufficient stock for LAPTOP", response.message());
    assertEquals(true, checkStock("LAPTOP", 2));
  }

  @Test
  void shouldRejectOrderWhenPaymentIsDeclined() {
    var response = placeOrder("C-1", "LAPTOP", 1, 1500.0, CREDENTIAL);

    assertFalse(response.success());
    assertEquals(
        "Order rejected: Payment of 1500.0 declined for C-1: exceeds credit limit",
        response.message());
    assertEquals(true, checkStock("LAPTOP", 2));
  }

  @Test
  void shouldRejectOrderWhenCallerLacksPaymentCredential() {
    var response = placeOrder("C-1", "LAPTOP", 1, 899.0, null);

    assertFalse(response.success());
    assertEquals("Order rejected: Access denied to payment", response.message());
    assertEquals(true, checkStock("LAPTOP", 2));
  }

  @Test
  void shouldReserveBeforeChargingAndReleaseWhenPaymentFails() {
    var operations = new ArrayList<String>();
    var recordingBus = busWithRecordingInventory(operations);

    var response = placeOrder(recordingBus, "C-1", "LAPTOP", 2, 1500.0, CREDENTIAL);

    assertFalse(response.success());
    assertEquals(
        "Order rejected: Payment of 1500.0 declined for C-1: exceeds credit limit",
        response.message());
    assertEquals(List.of("checkStock", "reserve", "release"), operations);
    assertEquals(true, checkStock("LAPTOP", 2));
  }

  @Test
  void shouldRejectOrderWhenReservationFails() {
    var charges = new AtomicInteger();
    var stubBus =
        busWithInventoryStub(
            ServiceResponse.ok(true), ServiceResponse.error("reservation failed"), charges);

    var response = placeOrder(stubBus, "C-1", "LAPTOP", 1, 899.0, CREDENTIAL);

    assertFalse(response.success());
    assertEquals("Order rejected: reservation failed", response.message());
    assertEquals(0, charges.get());
  }

  @Test
  void shouldRejectOrderWhenStockCheckFails() {
    var charges = new AtomicInteger();
    var stubBus =
        busWithInventoryStub(
            ServiceResponse.error("inventory unavailable"), ServiceResponse.ok(0), charges);

    var response = placeOrder(stubBus, "C-1", "LAPTOP", 1, 899.0, CREDENTIAL);

    assertFalse(response.success());
    assertEquals("Order rejected: inventory unavailable", response.message());
    assertEquals(0, charges.get());
  }

  @Test
  void shouldRejectNonPositiveQuantityWithTheInventoryReason() {
    var response = placeOrder("C-1", "LAPTOP", -5, 899.0, CREDENTIAL);

    assertFalse(response.success());
    assertEquals("Order rejected: Quantity must be positive: -5", response.message());
    assertEquals(true, checkStock("LAPTOP", 2));
  }

  @Test
  void shouldFailForUnknownOperation() {
    var response = bus.send(new ServiceRequest(OrderService.NAME, "cancelOrder", Map.of()));

    assertFalse(response.success());
    assertEquals("Unknown operation: cancelOrder", response.message());
  }

  private static ServiceBus busWithInventoryStub(
      ServiceResponse checkStockResponse, ServiceResponse reserveResponse, AtomicInteger charges) {
    var registry = new ServiceRegistry();
    var stubBus =
        new ServiceBus(registry, new AccessPolicy(Set.of(CREDENTIAL), Set.of(PaymentService.NAME)));
    registry.register(new CustomerService());
    registry.register(countingPaymentService(charges));
    registry.register(new OrderService(stubBus));
    registry.register(
        new Service() {
          @Override
          public String name() {
            return InventoryService.NAME;
          }

          @Override
          public ServiceResponse handle(ServiceRequest request) {
            return "checkStock".equals(request.operation()) ? checkStockResponse : reserveResponse;
          }
        });
    return stubBus;
  }

  private static Service countingPaymentService(AtomicInteger charges) {
    var payment = new PaymentService(1000.0);
    return new Service() {
      @Override
      public String name() {
        return PaymentService.NAME;
      }

      @Override
      public ServiceResponse handle(ServiceRequest request) {
        charges.incrementAndGet();
        return payment.handle(request);
      }
    };
  }

  private ServiceBus busWithRecordingInventory(List<String> operations) {
    var registry = new ServiceRegistry();
    var recordingBus =
        new ServiceBus(registry, new AccessPolicy(Set.of(CREDENTIAL), Set.of(PaymentService.NAME)));
    registry.register(new CustomerService());
    registry.register(new PaymentService(1000.0));
    registry.register(new OrderService(recordingBus));
    registry.register(
        new Service() {
          @Override
          public String name() {
            return InventoryService.NAME;
          }

          @Override
          public ServiceResponse handle(ServiceRequest request) {
            operations.add(request.operation());
            return inventory.handle(request);
          }
        });
    return recordingBus;
  }

  private ServiceResponse placeOrder(
      String customerId, String sku, int quantity, double amount, String credential) {
    return placeOrder(bus, customerId, sku, quantity, amount, credential);
  }

  private static ServiceResponse placeOrder(
      ServiceBus target,
      String customerId,
      String sku,
      int quantity,
      double amount,
      String credential) {
    return target.send(
        new ServiceRequest(
            OrderService.NAME,
            "placeOrder",
            Map.of("customerId", customerId, "sku", sku, "quantity", quantity, "amount", amount),
            credential));
  }

  private Object checkStock(String sku, int quantity) {
    return inventory
        .handle(
            new ServiceRequest(
                InventoryService.NAME, "checkStock", Map.of("sku", sku, "quantity", quantity)))
        .body();
  }
}
