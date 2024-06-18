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
package com.iluwatar.retry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Decorates {@link BusinessOperation business operation} with "retry"
 * capabilities.
 *
 * @param <T> the remote op's return type
 */
public final class Retry<T> implements BusinessOperation<T> {
  private final BusinessOperation<T> op;
  private final int maxAttempts;
  private final long delay;
  private final AtomicInteger attempts;
  private final Predicate<Exception> test;
  private final List<Exception> errors;
  private final ScheduledExecutorService scheduler;

  /**
   * Ctor.
   *
   * @param op          the {@link BusinessOperation} to retry
   * @param maxAttempts number of times to retry
   * @param delay       delay (in milliseconds) between attempts
   * @param ignoreTests tests to check whether the remote exception can be
   *                    ignored. No exceptions
   *                    will be ignored if no tests are given
   */
  @SafeVarargs
  public Retry(
      BusinessOperation<T> op,
      int maxAttempts,
      long delay,
      Predicate<Exception>... ignoreTests) {
    this.op = op;
    this.maxAttempts = maxAttempts;
    this.delay = delay;
    this.attempts = new AtomicInteger();
    this.test = Arrays.stream(ignoreTests).reduce(Predicate::or).orElse(e -> false);
    this.errors = new ArrayList<>();
    this.scheduler = Executors.newScheduledThreadPool(1);
  }

  /**
   * The errors encountered while retrying, in the encounter order.
   *
   * @return the errors encountered while retrying
   */
  public List<Exception> errors() {
    return Collections.unmodifiableList(this.errors);
  }

  /**
   * The number of retries performed.
   *
   * @return the number of retries performed
   */
  public int attempts() {
    return this.attempts.intValue();
  }

  @Override
  public T perform() throws BusinessException {
    CompletableFuture<T> future = new CompletableFuture<>();
    performWithRetry(future);
    try {
      return future.get();
    } catch (Exception e) {
      throw new BusinessException("Operation failed after retries");
    } finally {
      scheduler.shutdown();
    }
  }

  private void performWithRetry(CompletableFuture<T> future) {
    scheduler.schedule(() -> {
      try {
        future.complete(this.op.perform());
      } catch (Exception e) {
        this.errors.add((Exception) e);
        if (this.attempts.incrementAndGet() >= this.maxAttempts || !this.test.test(e)) {
          future.completeExceptionally(e);
          scheduler.shutdown();
        } else {
          performWithRetry(future);
        }
      }
    }, calculateDelay(), TimeUnit.MILLISECONDS);
  }

  private long calculateDelay() {
    if (attempts.get() == 0) {
      return 0;
    }
    return delay;
  }
}
