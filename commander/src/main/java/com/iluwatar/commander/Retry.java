/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.commander;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Retry pattern.
 *
 * @param <T> is the type of object passed into HandleErrorIssue as a parameter.
 */

public class Retry<T> {

  /**
   * Operation Interface will define method to be implemented.
   */

  public interface Operation {
    void operation(List<Exception> list) throws Exception;
  }

  /**
   * HandleErrorIssue defines how to handle errors.
   *
   * @param <T> is the type of object to be passed into the method as parameter.
   */

  public interface HandleErrorIssue<T> {
    void handleIssue(T obj, Exception e);
  }

  private static final Random RANDOM = new Random();

  private final Operation op;
  private final HandleErrorIssue<T> handleError;
  private final int maxAttempts;
  private final long maxDelay;
  private final AtomicInteger attempts;
  private final Predicate<Exception> test;
  private final List<Exception> errors;

  Retry(Operation op, HandleErrorIssue<T> handleError, int maxAttempts,
        long maxDelay, Predicate<Exception>... ignoreTests) {
    this.op = op;
    this.handleError = handleError;
    this.maxAttempts = maxAttempts;
    this.maxDelay = maxDelay;
    this.attempts = new AtomicInteger();
    this.test = Arrays.stream(ignoreTests).reduce(Predicate::or).orElse(e -> false);
    this.errors = new ArrayList<>();
  }

  /**
   * Performing the operation with retries.
   *
   * @param list is the exception list
   * @param obj  is the parameter to be passed into handleIsuue method
   */

  public void perform(List<Exception> list, T obj) {
    do {
      try {
        op.operation(list);
        return;
      } catch (Exception e) {
        this.errors.add(e);
        if (this.attempts.incrementAndGet() >= this.maxAttempts || !this.test.test(e)) {
          this.handleError.handleIssue(obj, e);
          return; //return here...dont go further
        }
        try {
          long testDelay =
              (long) Math.pow(2, this.attempts.intValue()) * 1000 + RANDOM.nextInt(1000);
          long delay = Math.min(testDelay, this.maxDelay);
          Thread.sleep(delay);
        } catch (InterruptedException f) {
          //ignore
        }
      }
    } while (true);
  }

}
