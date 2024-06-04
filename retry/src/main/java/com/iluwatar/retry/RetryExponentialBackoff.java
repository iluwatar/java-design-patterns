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
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Decorates {@link BusinessOperation business operation} with "retry"
 * capabilities using exponential backoff strategy.
 *
 * @param <T> the remote op's return type
 */
public final class RetryExponentialBackoff<T> implements BusinessOperation<T> {
  private static final Random RANDOM = new Random();
  private final BusinessOperation<T> op;
  private final int maxAttempts;
  private final long maxDelay;
  private final Predicate<Exception> test;
  private final List<Exception> errors;
  private final AtomicInteger attempts;

  /**
   * Constructor.
   *
   * @param op          the {@link BusinessOperation} to retry
   * @param maxAttempts number of times to retry
   * @param maxDelay    the maximum delay between retries (in milliseconds)
   * @param ignoreTests tests to check whether the remote exception can be
   *                    ignored. No exceptions
   *                    will be ignored if no tests are given
   */
  @SafeVarargs
  public RetryExponentialBackoff(BusinessOperation<T> op, int maxAttempts, long maxDelay,
      Predicate<Exception>... ignoreTests) {
    this.op = op;
    this.maxAttempts = maxAttempts;
    this.maxDelay = maxDelay;
    this.attempts = new AtomicInteger();
    this.test = Arrays.stream(ignoreTests).reduce(Predicate::or).orElse(e -> false);
    this.errors = new ArrayList<>();
  }

  /**
   * The errors encountered while retrying, in the order of occurrence.
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
    retryAttempt(future, 1);
    try {
      return future.get();
    } catch (Exception e) {
      throw new BusinessException("Max retry attempts exceeded.", e);
    }
  }

  /**
   * Recursive method to perform retry attempts asynchronously using
   * CompletableFuture.
   *
   * @param future  CompletableFuture to handle the result of the retry attempts
   * @param attempt current attempt number
   */
  private void retryAttempt(CompletableFuture<T> future, int attempt) {
    if (attempt > maxAttempts) {
      future.completeExceptionally(new BusinessException("Max retry attempts exceeded."));
      return;
    }

    CompletableFuture.runAsync(() -> {
      try {
        T result = op.perform();
        future.complete(result);
      } catch (BusinessException e) {
        errors.add(e);
        attempts.incrementAndGet();
        if (attempts.get() >= maxAttempts || !test.test(e)) {
          future.completeExceptionally(e);
        } else {
          long delay = calculateDelay(attempts.get());
          try {
            Thread.sleep(delay);
          } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
          }
          retryAttempt(future, attempt + 1);
        }
      }
    });
  }

  /**
   * Calculates the delay for exponential backoff based on the current attempt
   * number.
   *
   * @param attempt the current attempt number
   * @return the calculated delay (in milliseconds)
   */
  private long calculateDelay(int attempt) {
    long testDelay = (long) Math.pow(2, attempt) * 1000 + RANDOM.nextInt(1000);
    return Math.min(testDelay, maxDelay);
  }
}
