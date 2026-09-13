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

import lombok.extern.slf4j.Slf4j;

/**
 * A service whose entry point is protected by a {@link LoadShedder}. Every request first asks the
 * shedder for admission; requests that are shed receive a {@link Response.Status#REJECTED} response
 * right away, admitted requests are handed to the {@link RequestHandler} and always release their
 * capacity slot afterwards, even when the handler fails.
 */
@Slf4j
public class ShedGuardedService {

  private final String name;
  private final LoadShedder shedder;
  private final RequestHandler handler;

  /**
   * Creates a guarded service.
   *
   * @param name service name used in log output
   * @param shedder admission controller protecting the service
   * @param handler business logic executed for admitted requests
   */
  public ShedGuardedService(String name, LoadShedder shedder, RequestHandler handler) {
    this.name = name;
    this.shedder = shedder;
    this.handler = handler;
  }

  /**
   * Handles the request if capacity allows, otherwise fails fast.
   *
   * @param request incoming request
   * @return the handler result, or a rejection when the request was shed
   */
  public Response handle(Request request) {
    int inFlight;
    try {
      // The count is taken from the admission itself: reading it back afterwards could report a
      // value that belongs to a concurrent request.
      inFlight = shedder.acquire(request);
    } catch (LoadShedException e) {
      LOGGER.warn("[{}] shed {} ({}): {}", name, request.id(), request.priority(), e.getMessage());
      return Response.rejected(request, e.getMessage());
    }
    LOGGER.info(
        "[{}] admitted {} ({}), {}/{} in flight",
        name,
        request.id(),
        request.priority(),
        inFlight,
        shedder.getMaxInFlight());
    try {
      return Response.accepted(request, handler.handle(request));
    } finally {
      shedder.release();
    }
  }
}
