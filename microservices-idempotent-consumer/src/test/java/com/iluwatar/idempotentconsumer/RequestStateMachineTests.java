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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestStateMachineTests {
  private RequestStateMachine requestStateMachine;

  @BeforeEach
  public void setUp() {
    requestStateMachine = new RequestStateMachine();
  }

  @Test
  void transitionPendingToStarted() {
    Request startedRequest = requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.PENDING),
        Request.Status.STARTED);
    assertEquals(Request.Status.STARTED, startedRequest.getStatus());
  }

  @Test
  void transitionAnyToPending_shouldThrowError() {
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.PENDING),
            Request.Status.PENDING));
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.STARTED),
            Request.Status.PENDING));
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.COMPLETED),
            Request.Status.PENDING));
  }

  @Test
  void transitionCompletedToAny_shouldThrowError() {
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.COMPLETED),
            Request.Status.PENDING));
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.COMPLETED),
            Request.Status.STARTED));
    assertThrows(InvalidNextStateException.class,
        () -> requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.COMPLETED),
            Request.Status.COMPLETED));
  }

  @Test
  void transitionStartedToCompleted() {
    Request completedRequest = requestStateMachine.next(new Request(UUID.randomUUID(), Request.Status.STARTED),
        Request.Status.COMPLETED);
    assertEquals(Request.Status.COMPLETED, completedRequest.getStatus());
  }


}