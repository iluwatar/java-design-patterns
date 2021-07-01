/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Decorates {@link BusinessOperation business operation} with "retry" capabilities.
 *
 * @param <T> the remote op's return type
 * @author George Aristy (george.aristy@gmail.com)
 */
public final class Retry<T> implements BusinessOperation<T> {
  private final BusinessOperation<T> op;
  private final int maxAttempts;
  private final long delay;
  private final AtomicInteger attempts;
  private final Predicate<Exception> test;
  private final List<Exception> errors;

  /**
   * Ctor.
   *
   * @param op          the {@link BusinessOperation} to retry
   * @param maxAttempts number of times to retry
   * @param delay       delay (in milliseconds) between attempts
   * @param ignoreTests tests to check whether the remote exception can be ignored. No exceptions
   *                    will be ignored if no tests are given
   */
  @SafeVarargs
  public Retry(
      BusinessOperation<T> op,
      int maxAttempts,
      long delay,
      Predicate<Exception>... ignoreTests
  ) {
    this.op = op;
    this.maxAttempts = maxAttempts;
    this.delay = delay;
    this.attempts = new AtomicInteger();
    this.test = Arrays.stream(ignoreTests).reduce(Predicate::or).orElse(e -> false);
    this.errors = new ArrayList<>();
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
    do {
      try {
        return this.op.perform();
      } catch (BusinessException e) {
        this.errors.add(e);

        if (this.attempts.incrementAndGet() >= this.maxAttempts || !this.test.test(e)) {
          throw e;
        }

        try {
          Thread.sleep(this.delay);
        } catch (InterruptedException f) {
          //ignore
        }
      }
    } while (true);
  }
}
