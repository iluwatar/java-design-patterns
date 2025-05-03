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

import org.junit.jupiter.api.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Add a field as a counter. Every time the callback method is called increment this field. Unit
 * test checks that the field is being incremented.
 *
 * <p>Could be done with mock objects as well where the call method call is verified.
 */
class CallbackTest {

  private Integer callingCount = 0;

  @Test
  void test() throws InterruptedException {
    CountDownLatch latch = new CountDownLatch(1);

    CountDownLatch finalLatch = latch;
    Callback callback = () -> {
      callingCount++;
      finalLatch.countDown();
    };

    var task = new SimpleTask();

    task.executeWith(callback);

    latch.await(5, TimeUnit.SECONDS);

    assertEquals(Integer.valueOf(1), callingCount, "Callback called once");

    callingCount = 0;
    latch = new CountDownLatch(1);

    task.executeWith(callback);

    latch.await(5, TimeUnit.SECONDS);

    assertEquals(Integer.valueOf(1), callingCount, "Callback called once again");
  }
}
