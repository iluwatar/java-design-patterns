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

/**
 * Outcome returned to the caller. A shed request gets a {@link Status#REJECTED} response
 * immediately instead of waiting in a queue, which is the fail-fast behaviour that keeps the
 * service responsive under overload.
 *
 * @param requestId identifier of the request this response belongs to
 * @param status whether the request was processed or shed
 * @param message result of the work, or the reason the request was shed
 */
public record Response(String requestId, Status status, String message) {

  /** Result of admission control. */
  public enum Status {
    /** The request was admitted and processed. */
    ACCEPTED,
    /** The request was shed because the service is at capacity. Callers may retry later. */
    REJECTED
  }

  /** Creates the response for a request that was processed. */
  public static Response accepted(Request request, String result) {
    return new Response(request.id(), Status.ACCEPTED, result);
  }

  /** Creates the fast-failure response for a request that was shed. */
  public static Response rejected(Request request, String reason) {
    return new Response(request.id(), Status.REJECTED, reason);
  }
}
