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
package com.iluwatar.loadshedding;

import lombok.Getter;

/**
 * Thrown by {@link LoadShedder#acquire(Request)} when a request must be shed. It is the in-process
 * equivalent of an HTTP 503 "Service Unavailable" response: the service is healthy but has no spare
 * capacity for this request right now, and the caller should back off and retry later.
 */
@Getter
public class LoadShedException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final String requestId;
  private final Priority priority;

  /**
   * Creates the exception for a shed request.
   *
   * @param request the request that was shed
   * @param inFlight number of requests being processed at the moment of the decision
   * @param limit admission limit that applies to the request's priority
   */
  public LoadShedException(Request request, int inFlight, int limit) {
    super(
        String.format(
            "Request %s shed: %d requests in flight, limit for %s priority is %d",
            request.id(), inFlight, request.priority(), limit));
    this.requestId = request.id();
    this.priority = request.priority();
  }
}
