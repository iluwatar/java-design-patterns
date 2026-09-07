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

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;

/**
 * Composite service that orchestrates the customer, inventory and payment services into one
 * business capability: placing an order.
 *
 * <p>The order service depends only on service names and message contracts, never on the classes
 * that provide them. It sends messages through the {@link ServiceBus}, forwards the caller's
 * credential so that the bus can authorise each downstream call, and composes the responses. This
 * is how SOA builds higher level services out of reusable lower level ones.
 *
 * <p>There is no distributed transaction spanning the services, so the order service compensates
 * instead: it reserves the stock before it charges the customer and releases the reservation again
 * when the charge fails.
 *
 * <p>Operations:
 *
 * <ul>
 *   <li>{@code placeOrder} with {@code customerId}, {@code sku}, {@code quantity} and {@code
 *       amount} returns an {@link OrderConfirmation}
 * </ul>
 */
@RequiredArgsConstructor
public class OrderService implements Service {

  public static final String NAME = "order";

  private static final String CUSTOMER_SERVICE = "customer";
  private static final String INVENTORY_SERVICE = "inventory";
  private static final String PAYMENT_SERVICE = "payment";

  private final ServiceBus bus;
  private final AtomicInteger sequence = new AtomicInteger();

  /**
   * The result of a successfully placed order.
   *
   * @param orderId the generated order identifier
   * @param customerId the identifier of the ordering customer
   * @param customerName the name of the ordering customer
   * @param sku the ordered product
   * @param quantity the ordered quantity
   * @param paymentReference the reference returned by the payment service
   */
  public record OrderConfirmation(
      String orderId,
      String customerId,
      String customerName,
      String sku,
      int quantity,
      String paymentReference) {}

  @Override
  public String name() {
    return NAME;
  }

  @Override
  public ServiceResponse handle(ServiceRequest request) {
    return switch (request.operation()) {
      case "placeOrder" -> placeOrder(request);
      default -> ServiceResponse.error("Unknown operation: " + request.operation());
    };
  }

  private ServiceResponse placeOrder(ServiceRequest request) {
    var customerId = request.param("customerId", String.class);
    var sku = request.param("sku", String.class);
    var quantity = request.param("quantity", Integer.class);
    var amount = request.param("amount", Double.class);
    var credential = request.credential();

    var customer =
        bus.send(
            new ServiceRequest(
                CUSTOMER_SERVICE, "getCustomer", Map.of("customerId", customerId), credential));
    if (!customer.success()) {
      return ServiceResponse.error("Order rejected: " + customer.message());
    }

    // This read is kept to illustrate service composition, not as a guard: reserve re-checks the
    // stock atomically, so the order stays correct even though the level can change in between.
    var stock =
        bus.send(
            new ServiceRequest(
                INVENTORY_SERVICE,
                "checkStock",
                Map.of("sku", sku, "quantity", quantity),
                credential));
    if (!stock.success()) {
      return ServiceResponse.error("Order rejected: " + stock.message());
    }
    if (!Boolean.TRUE.equals(stock.body())) {
      return ServiceResponse.error("Order rejected: insufficient stock for " + sku);
    }

    var reservation =
        bus.send(
            new ServiceRequest(
                INVENTORY_SERVICE,
                "reserve",
                Map.of("sku", sku, "quantity", quantity),
                credential));
    if (!reservation.success()) {
      return ServiceResponse.error("Order rejected: " + reservation.message());
    }

    var payment =
        bus.send(
            new ServiceRequest(
                PAYMENT_SERVICE,
                "charge",
                Map.of("customerId", customerId, "amount", amount),
                credential));
    if (!payment.success()) {
      bus.send(
          new ServiceRequest(
              INVENTORY_SERVICE, "release", Map.of("sku", sku, "quantity", quantity), credential));
      return ServiceResponse.error("Order rejected: " + payment.message());
    }

    var orderingCustomer = (Customer) customer.body();
    var confirmation =
        new OrderConfirmation(
            "ORD-" + sequence.incrementAndGet(),
            orderingCustomer.id(),
            orderingCustomer.name(),
            sku,
            quantity,
            (String) payment.body());
    return ServiceResponse.ok(confirmation);
  }
}
