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
package com.iluwatar.idempotentconsumer;

import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * This service is responsible for handling request operations including
 * creation, start, and completion of requests.
 */
@Service
public class RequestService {
  RequestRepository requestRepository;
  RequestStateMachine requestStateMachine;

  public RequestService(RequestRepository requestRepository,
                        RequestStateMachine requestStateMachine) {
    this.requestRepository = requestRepository;
    this.requestStateMachine = requestStateMachine;
  }

  /**
   * Creates a new Request or returns an existing one by it's UUID.
   * This operation is idempotent: performing it once or several times
   * successively leads to an equivalent result.
   *
   * @param uuid The unique identifier for the Request.
   * @return Return existing Request or save and return a new Request.
   */
  public Request create(UUID uuid) {
    Optional<Request> optReq = requestRepository.findById(uuid);
    if (!optReq.isEmpty()) {
      return optReq.get();
    }
    return requestRepository.save(new Request(uuid));
  }

  /**
   * Starts the Request assigned with the given UUID.
   *
   * @param uuid The unique identifier for the Request.
   * @return The started Request.
   * @throws RequestNotFoundException if a Request with the given UUID is not found.
   */
  public Request start(UUID uuid) {
    Optional<Request> optReq = requestRepository.findById(uuid);
    if (optReq.isEmpty()) {
      throw new RequestNotFoundException(uuid);
    }
    return requestRepository.save(requestStateMachine.next(optReq.get(), Request.Status.STARTED));
  }

  /**
   * Complete the Request assigned with the given UUID.
   *
   * @param uuid The unique identifier for the Request.
   * @return The completed Request.
   * @throws RequestNotFoundException if a Request with the given UUID is not found.
   */
  public Request complete(UUID uuid) {
    Optional<Request> optReq = requestRepository.findById(uuid);
    if (optReq.isEmpty()) {
      throw new RequestNotFoundException(uuid);
    }
    return requestRepository.save(requestStateMachine.next(optReq.get(), Request.Status.COMPLETED));
  }
}
