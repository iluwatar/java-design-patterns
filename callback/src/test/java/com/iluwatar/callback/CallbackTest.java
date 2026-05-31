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
package com.iluwatar.callback;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

/**
 * Add a field as a counter. Every time the callback method is called increment this field. Unit
 * test checks that the field is being incremented.
 *
 * <p>Could be done with mock objects as well where the call method call is verified.
 */
class CallbackTest {

  private Integer callingCount = 0;

  @Test
  void testSynchronousCallback() {
    var counter = new AtomicInteger();
    Callback callback = counter::incrementAndGet;
    var task = new SimpleTask();

    assertEquals(0, counter.get(), "Initial count should be 0");
    task.executeWith(callback);
    assertEquals(1, counter.get(), "Callback should be called once");
    task.executeWith(callback);
    assertEquals(2, counter.get(), "Callback should be called twice");
  }

  @Test
  void testAsynchronousCallback() {
    var task = new SimpleTask();

    var counter1 = new AtomicInteger();
    final CompletableFuture<Void> future1 = new CompletableFuture<>();
    Callback callback1 =
        () -> {
          counter1.incrementAndGet();
          future1.complete(null);
        };
    var f1 = task.executeAsyncWith(callback1);
    future1.orTimeout(1, TimeUnit.SECONDS).join();
    f1.join();
    assertEquals(1, counter1.get(), "Async callback should increment once");

    var counter2 = new AtomicInteger();
    final CompletableFuture<Void> future2 = new CompletableFuture<>();
    Callback callback2 =
        () -> {
          counter2.incrementAndGet();
          future2.complete(null);
        };
    var f2 = task.executeAsyncWith(callback2);
    future2.orTimeout(1, TimeUnit.SECONDS).join();
    f2.join();
    assertEquals(1, counter2.get(), "Async callback should increment once again");
  }
}
