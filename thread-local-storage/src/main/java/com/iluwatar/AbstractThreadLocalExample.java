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
package com.iluwatar;

import java.security.SecureRandom;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Class with main logic.
 */
public abstract class AbstractThreadLocalExample implements Runnable {

  private static final SecureRandom RND = new SecureRandom();

  private static final Integer RANDOM_THREAD_PARK_START = 1_000_000_000;
  private static final Integer RANDOM_THREAD_PARK_END = 2_000_000_000;

  @Override
  public void run() {
    long nanosToPark = RND.nextInt(RANDOM_THREAD_PARK_START, RANDOM_THREAD_PARK_END);
    LockSupport.parkNanos(nanosToPark);

    System.out.println(getThreadName() + ", before value changing: " + getter().get());
    setter().accept(RND.nextInt());
  }

  /**
   * Setter for our value.
   *
   * @return consumer
   */
  protected abstract Consumer<Integer> setter();

  /**
   * Getter for our value.
   *
   * @return supplier
   */
  protected abstract Supplier<Integer> getter();

  private String getThreadName() {
    return Thread.currentThread().getName();
  }
}
