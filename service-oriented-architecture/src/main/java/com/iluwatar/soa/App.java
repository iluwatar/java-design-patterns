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
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

/**
 * Service-Oriented Architecture (SOA) structures an application as a set of loosely coupled,
 * reusable services that communicate through well defined, coarse-grained contracts over a shared
 * communication backbone.
 *
 * <p>The building blocks demonstrated here are:
 *
 * <ul>
 *   <li>{@link Service}: the contract every provider implements, expressed with interoperable
 *       {@link ServiceRequest} and {@link ServiceResponse} messages
 *   <li>{@link ServiceRegistry}: discovery, so consumers locate services by name at runtime
 *   <li>{@link ServiceBus}: the backbone that routes messages, applies cross-cutting concerns and
 *       isolates consumers from provider failures
 *   <li>{@link AccessPolicy}: security as a cross-cutting concern, enforced by the bus so that the
 *       services themselves contain no security code
 *   <li>{@link CustomerService}, {@link InventoryService}, {@link PaymentService}: enterprise
 *       services that each own one business capability and keep no per-conversation session state
 *   <li>{@link OrderService}: a composite service that orchestrates the others through the bus
 * </ul>
 *
 * <p>The demo registers the services behind a bus that protects the payment service, places an
 * order with a valid credential that succeeds, an order that fails because of insufficient stock,
 * addresses a service that does not exist, and finally places an order anonymously, which the bus
 * rejects when the order service tries to charge the customer.
 */
@Slf4j
public class App {

  private static final String CHECKOUT_CREDENTIAL = "checkout-service-key";

  /**
   * Program entry point.
   *
   * @param args command line arguments, not used
   */
  public static void main(String[] args) {
    LOGGER.info("Bootstrapping the service registry and the service bus");
    var registry = new ServiceRegistry();
    var policy = new AccessPolicy(Set.of(CHECKOUT_CREDENTIAL), Set.of(PaymentService.NAME));
    var bus = new ServiceBus(registry, policy);

    registry.register(new CustomerService());
    registry.register(new InventoryService());
    registry.register(new PaymentService());
    registry.register(new OrderService(bus));
    LOGGER.info("Available services: {}", registry.serviceNames());

    LOGGER.info("Placing an order with a valid credential, it should succeed");
    var accepted =
        bus.send(
            new ServiceRequest(
                OrderService.NAME,
                "placeOrder",
                Map.of("customerId", "C-1", "sku", "LAPTOP", "quantity", 2, "amount", 899.0),
                CHECKOUT_CREDENTIAL));
    LOGGER.info("Order outcome: {}", accepted);

    LOGGER.info("Placing an order that should be rejected because of stock");
    var rejected =
        bus.send(
            new ServiceRequest(
                OrderService.NAME,
                "placeOrder",
                Map.of("customerId", "C-2", "sku", "PHONE", "quantity", 50, "amount", 499.0),
                CHECKOUT_CREDENTIAL));
    LOGGER.info("Order outcome: {}", rejected);

    LOGGER.info("Addressing a service that is not registered");
    var unknown = bus.send(new ServiceRequest("shipping", "ship", Map.of("orderId", "ORD-1")));
    LOGGER.info("Bus outcome: {}", unknown);

    LOGGER.info("Placing an order anonymously, the bus should deny access to payment");
    var denied =
        bus.send(
            new ServiceRequest(
                OrderService.NAME,
                "placeOrder",
                Map.of("customerId", "C-1", "sku", "LAPTOP", "quantity", 1, "amount", 899.0)));
    LOGGER.info("Order outcome: {}", denied);
  }
}
