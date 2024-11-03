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

import org.springframework.stereotype.Component;

/**
 * This class represents a state machine for managing request transitions.
 * It supports transitions to the statuses: PENDING, STARTED, and COMPLETED.
 */
@Component
public class RequestStateMachine {

  /**
   * Provides the next possible state of the request based on the current and next status.
   *
   * @param req        The actual request object. This object MUST NOT be null and SHOULD have a valid status.
   * @param nextStatus Represents the next status that the request could transition to. MUST NOT be null.
   * @return A new Request object with updated status if the transition is valid.
   * @throws InvalidNextStateException If an invalid state transition is attempted.
   */
  public Request next(Request req, Request.Status nextStatus) {
    String transitionStr = String.format("Transition: %s -> %s", req.getStatus(), nextStatus);
    switch (nextStatus) {
      case PENDING -> throw new InvalidNextStateException(transitionStr);
      case STARTED -> {
        if (Request.Status.PENDING.equals(req.getStatus())) {
          return new Request(req.getUuid(), Request.Status.STARTED);
        }
        throw new InvalidNextStateException(transitionStr);
      }
      case COMPLETED -> {
        if (Request.Status.STARTED.equals(req.getStatus())) {
          return new Request(req.getUuid(), Request.Status.COMPLETED);
        }
        throw new InvalidNextStateException(transitionStr);
      }
      default -> throw new InvalidNextStateException(transitionStr);
    }
  }
}
