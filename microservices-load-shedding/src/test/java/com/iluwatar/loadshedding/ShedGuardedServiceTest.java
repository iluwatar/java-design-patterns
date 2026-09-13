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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

class ShedGuardedServiceTest {

  private final LoadShedder shedder = new LoadShedder(2, 1, 0);

  @Test
  void returnsHandlerResultWhenAdmitted() {
    var service = new ShedGuardedService("svc", shedder, request -> "done " + request.id());
    var response = service.handle(new Request("a", Priority.NORMAL, "work"));
    assertEquals(new Response("a", Response.Status.ACCEPTED, "done a"), response);
    assertEquals(0, shedder.getInFlight());
  }

  @Test
  void returnsRejectionWithoutInvokingHandlerWhenShed() {
    var handlerCalled = new AtomicBoolean();
    var service =
        new ShedGuardedService(
            "svc",
            shedder,
            request -> {
              handlerCalled.set(true);
              return "unexpected";
            });
    shedder.acquire(new Request("occupied", Priority.NORMAL, "work"));
    var response = service.handle(new Request("b", Priority.LOW, "work"));
    assertEquals(Response.Status.REJECTED, response.status());
    assertEquals("b", response.requestId());
    assertFalse(handlerCalled.get());
    assertEquals(1, shedder.getInFlight());
    assertEquals(1, shedder.getShed(Priority.LOW));
  }

  @Test
  void releasesCapacityWhenHandlerFails() {
    var service =
        new ShedGuardedService(
            "svc",
            shedder,
            request -> {
              throw new IllegalStateException("boom");
            });
    assertThrows(
        IllegalStateException.class,
        () -> service.handle(new Request("c", Priority.NORMAL, "work")));
    assertEquals(0, shedder.getInFlight());
  }
}
