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
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * The service registry provides discovery: services publish themselves under a name and consumers
 * locate them at runtime instead of hard-wiring implementations.
 *
 * <p>The registry is the only place that knows which concrete class provides a contract, so a
 * service can be swapped or moved without touching its consumers.
 */
@Slf4j
public class ServiceRegistry {

  private final Map<String, Service> services = new ConcurrentHashMap<>();

  /**
   * Publishes a service under its name.
   *
   * @param service the service to publish
   * @throws IllegalStateException when another service already uses the same name
   */
  public void register(Service service) {
    var previous = services.putIfAbsent(service.name(), service);
    if (previous != null) {
      throw new IllegalStateException("Service already registered: " + service.name());
    }
    LOGGER.info("Registered service '{}' ({})", service.name(), service.getClass().getSimpleName());
  }

  /** Finds a service by name. */
  public Optional<Service> lookup(String name) {
    return Optional.ofNullable(services.get(name));
  }

  /** The names of all published services. */
  public Set<String> serviceNames() {
    return Set.copyOf(services.keySet());
  }
}
