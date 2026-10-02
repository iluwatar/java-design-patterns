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

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * The service bus is the communication backbone of the architecture.
 *
 * <p>Consumers hand every request to the bus. The bus applies cross-cutting concerns in one place
 * (access control through the {@link AccessPolicy}, tracing and timing), discovers the target
 * service in the {@link ServiceRegistry} and shields consumers from provider failures by
 * translating exceptions into error responses. Access control comes first, so a caller that may not
 * reach a service learns nothing about what the registry holds. Because the consumer only talks to
 * the bus, the provider can be replaced or relocated transparently.
 */
@Slf4j
public class ServiceBus {

  private final ServiceRegistry registry;
  private final AccessPolicy policy;

  /** Creates a bus that routes to the given registry and lets every request through. */
  public ServiceBus(ServiceRegistry registry) {
    this(registry, AccessPolicy.permitAll());
  }

  /** Creates a bus that routes to the given registry and enforces the given access policy. */
  public ServiceBus(ServiceRegistry registry, AccessPolicy policy) {
    this.registry = registry;
    this.policy = policy;
  }

  /**
   * Routes the request to the service it addresses.
   *
   * @param request the message to deliver
   * @return the service response, or an error response when the service is unknown, access is
   *     denied or the service fails
   */
  public ServiceResponse send(ServiceRequest request) {
    if (!policy.allows(request)) {
      LOGGER.warn(
          "Access denied to {}.{} for {} caller",
          request.service(),
          request.operation(),
          request.credential() == null ? "anonymous" : "credentialed");
      return ServiceResponse.error("Access denied to " + request.service());
    }
    var service = registry.lookup(request.service());
    if (service.isEmpty()) {
      LOGGER.warn("No service registered under '{}'", request.service());
      return ServiceResponse.error("No such service: " + request.service());
    }
    LOGGER.info("-> {}.{} payload={}", request.service(), request.operation(), request.payload());
    var start = System.nanoTime();
    try {
      var response = service.get().handle(request);
      LOGGER.info(
          "<- {}.{} success={} in {} ms",
          request.service(),
          request.operation(),
          response.success(),
          TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
      return response;
    } catch (RuntimeException e) {
      LOGGER.error("<- {}.{} failed: {}", request.service(), request.operation(), e.getMessage());
      return ServiceResponse.error("Service " + request.service() + " failed: " + e.getMessage());
    }
  }
}
