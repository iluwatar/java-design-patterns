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

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

/** Unit tests for {@link Retry}. */
class RetryTest {

  /** Should contain all errors thrown. */
  @Test
  void errors() {
    final var e = new BusinessException("unhandled");
    final var retry =
        new Retry<String>(
            () -> {
              throw e;
            },
            2,
            0);
    try {
      retry.perform();
    } catch (BusinessException ex) {
      // ignore
    }

    assertThat(retry.errors(), hasItem(e));
  }

  /**
   * No exceptions will be ignored, hence final number of attempts should be 1 even if we're asking
   * it to attempt twice.
   */
  @Test
  void attempts() {
    final var e = new BusinessException("unhandled");
    final var retry =
        new Retry<String>(
            () -> {
              throw e;
            },
            2,
            0);
    try {
      retry.perform();
    } catch (BusinessException ex) {
      // ignore
    }

    assertThat(retry.attempts(), is(1));
  }

  /**
   * Final number of attempts should be equal to the number of attempts asked because we are asking
   * it to ignore the exception that will be thrown.
   */
  @Test
  void ignore() {
    final var e = new CustomerNotFoundException("customer not found");
    final var retry =
        new Retry<String>(
            () -> {
              throw e;
            },
            2,
            0,
            ex -> CustomerNotFoundException.class.isAssignableFrom(ex.getClass()));
    try {
      retry.perform();
    } catch (BusinessException ex) {
      // ignore
    }

    assertThat(retry.attempts(), is(2));
  }
}
