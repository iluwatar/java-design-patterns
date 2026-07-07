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
package com.iluwatar.bff.service.impl;

import com.iluwatar.bff.model.User;
import com.iluwatar.bff.service.AuthService;
import java.util.Map;

/**
 * In-memory stand-in for the real customer authentication service API. Real deployments would
 * replace this with an HTTP/gRPC client; the BFFs are written against the {@link AuthService}
 * interface, so swapping the implementation later requires no change to any BFF.
 */
public final class InMemoryAuthService implements AuthService {

  /** Users stored in memory, keyed by user id. */
  private final Map<String, User> users;

  /**
   * Creates the service with a fixed backing map of users, keyed by user id.
   *
   * @param userData the user data this service serves
   */
  public InMemoryAuthService(final Map<String, User> userData) {
    this.users = userData;
  }

  @Override
  public User getUser(final String userId) {
    var user = users.get(userId);
    if (user == null) {
      throw new IllegalArgumentException("Unknown user id: " + userId);
    }
    return user;
  }
}
