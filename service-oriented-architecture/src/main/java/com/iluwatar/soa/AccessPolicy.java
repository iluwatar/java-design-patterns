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

import java.util.Set;

/**
 * Decides whether a request may reach a service.
 *
 * <p>Security is a cross-cutting concern that the {@link ServiceBus} enforces centrally, so the
 * services themselves stay free of security code. The model here is deliberately minimal: a set of
 * valid credentials and a set of protected service names. In a real SOA this is where the bus would
 * authenticate the caller and authorise the operation against a security token service.
 */
public class AccessPolicy {

  private final Set<String> validCredentials;
  private final Set<String> protectedServices;

  /**
   * Creates a policy that protects the given services and accepts the given credentials.
   *
   * @param validCredentials the credentials that unlock protected services
   * @param protectedServices the names of the services that require a valid credential
   */
  public AccessPolicy(Set<String> validCredentials, Set<String> protectedServices) {
    this.validCredentials = Set.copyOf(validCredentials);
    this.protectedServices = Set.copyOf(protectedServices);
  }

  /** Creates a policy that protects nothing. */
  public static AccessPolicy permitAll() {
    return new AccessPolicy(Set.of(), Set.of());
  }

  /**
   * Checks whether the request may be dispatched.
   *
   * @param request the request about to be routed
   * @return {@code true} when the target service is not protected or the request carries a valid
   *     credential
   */
  public boolean allows(ServiceRequest request) {
    if (!protectedServices.contains(request.service())) {
      return true;
    }
    return request.credential() != null && validCredentials.contains(request.credential());
  }
}
