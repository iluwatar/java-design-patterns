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
package com.iluwatar.join;

import java.util.concurrent.CountDownLatch;

/**
 * The Join design pattern allows multiple concurrent processes or threads to be
 * synchronized such that they all must complete before any subsequent tasks can
 * proceed. This pattern is particularly useful in scenarios where tasks can be
 * executed in parallel but the subsequent tasks must wait for the completion of
 * these parallel tasks.
 */
public class JoinPattern {

  int noOfDemoThreads;
  private CountDownLatch latch;
  int[] executionOrder;

  public JoinPattern(int noOfDemoThreads, int[] executionOrder) {
    latch = new CountDownLatch(noOfDemoThreads);
    this.executionOrder = executionOrder;
  }

  public void countdown() {
    latch.countDown();
  }

  public void await() throws InterruptedException {
    latch.await();
  }

}
