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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AccessPolicyTest {

  private final AccessPolicy policy = new AccessPolicy(Set.of("secret"), Set.of("payment"));

  @Test
  void permitAllShouldAllowEverything() {
    var permitAll = AccessPolicy.permitAll();

    assertTrue(permitAll.allows(request("payment", null)));
    assertTrue(permitAll.allows(request("customer", null)));
  }

  @Test
  void shouldDenyProtectedServiceWithoutCredential() {
    assertFalse(policy.allows(request("payment", null)));
  }

  @Test
  void shouldDenyProtectedServiceWithUnknownCredential() {
    assertFalse(policy.allows(request("payment", "wrong")));
  }

  @Test
  void shouldAllowProtectedServiceWithValidCredential() {
    assertTrue(policy.allows(request("payment", "secret")));
  }

  @Test
  void shouldAllowUnprotectedServiceAnonymously() {
    assertTrue(policy.allows(request("customer", null)));
  }

  private static ServiceRequest request(String service, String credential) {
    return new ServiceRequest(service, "op", Map.of(), credential);
  }
}
