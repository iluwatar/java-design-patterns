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

/**
 * The service contract every provider exposes to the {@link ServiceBus}.
 *
 * <p>In a service-oriented architecture a service is a coarse-grained, stateless unit of business
 * functionality. Consumers never depend on the implementation class; they only know the service
 * name and the message formats ({@link ServiceRequest} and {@link ServiceResponse}). This keeps
 * services loosely coupled and independently replaceable.
 */
public interface Service {

  /** The unique name consumers use to address this service through the bus. */
  String name();

  /**
   * Handles a single request. Implementations hold no per-conversation session state: every request
   * carries everything the service needs to process it, and any state a service owns is shared
   * between callers and guarded for concurrent access.
   *
   * @param request the incoming message
   * @return the outcome of the operation
   */
  ServiceResponse handle(ServiceRequest request);
}
