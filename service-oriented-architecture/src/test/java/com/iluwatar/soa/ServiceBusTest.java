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

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ServiceBusTest {

  private ServiceRegistry registry;
  private ServiceBus bus;

  @BeforeEach
  void setUp() {
    registry = new ServiceRegistry();
    bus = new ServiceBus(registry);
  }

  @Test
  void shouldRouteRequestToRegisteredServiceWithoutAlteringResponse() {
    var customerService = new CustomerService();
    registry.register(customerService);
    var request =
        new ServiceRequest(CustomerService.NAME, "getCustomer", Map.of("customerId", "C-1"));

    var viaBus = bus.send(request);
    var direct = customerService.handle(request);

    assertEquals(direct, viaBus);
    assertTrue(viaBus.success());
    assertEquals("Alice Smith", ((Customer) viaBus.body()).name());
  }

  @Test
  void shouldReturnErrorForUnknownService() {
    var response = bus.send(new ServiceRequest("shipping", "ship", Map.of()));

    assertFalse(response.success());
    assertEquals("No such service: shipping", response.message());
  }

  @Test
  void shouldTranslateServiceExceptionIntoErrorResponse() {
    registry.register(new CustomerService());

    var response = bus.send(new ServiceRequest(CustomerService.NAME, "getCustomer", Map.of()));

    assertFalse(response.success());
    assertTrue(response.message().startsWith("Service customer failed:"));
    assertTrue(response.message().contains("customerId"));
  }

  @Test
  void shouldDenyProtectedServiceWithoutInvokingIt() {
    var invocations = new AtomicInteger();
    registry.register(countingService(invocations));
    var securedBus = new ServiceBus(registry, new AccessPolicy(Set.of("key"), Set.of("counter")));

    var response = securedBus.send(new ServiceRequest("counter", "count", Map.of()));

    assertFalse(response.success());
    assertEquals("Access denied to counter", response.message());
    assertEquals(0, invocations.get());
  }

  @Test
  void shouldDenyProtectedServiceWithWrongCredential() {
    var invocations = new AtomicInteger();
    registry.register(countingService(invocations));
    var securedBus = new ServiceBus(registry, new AccessPolicy(Set.of("key"), Set.of("counter")));

    var response = securedBus.send(new ServiceRequest("counter", "count", Map.of(), "wrong-key"));

    assertFalse(response.success());
    assertEquals("Access denied to counter", response.message());
    assertEquals(0, invocations.get());
  }

  @Test
  void shouldDenyProtectedServiceBeforeRevealingThatItIsNotRegistered() {
    var securedBus = new ServiceBus(registry, new AccessPolicy(Set.of("key"), Set.of("counter")));

    var response = securedBus.send(new ServiceRequest("counter", "count", Map.of()));

    assertFalse(response.success());
    assertEquals("Access denied to counter", response.message());
  }

  @Test
  void shouldPassValidCredentialThroughToProtectedService() {
    var invocations = new AtomicInteger();
    registry.register(countingService(invocations));
    var securedBus = new ServiceBus(registry, new AccessPolicy(Set.of("key"), Set.of("counter")));

    var response = securedBus.send(new ServiceRequest("counter", "count", Map.of(), "key"));

    assertTrue(response.success());
    assertEquals(1, response.body());
    assertEquals(1, invocations.get());
  }

  private static Service countingService(AtomicInteger invocations) {
    return new Service() {
      @Override
      public String name() {
        return "counter";
      }

      @Override
      public ServiceResponse handle(ServiceRequest request) {
        return ServiceResponse.ok(invocations.incrementAndGet());
      }
    };
  }
}
