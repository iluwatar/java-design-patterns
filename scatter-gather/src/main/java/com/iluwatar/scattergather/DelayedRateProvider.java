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
package com.iluwatar.scattergather;

import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

/**
 * Wraps another provider and delays its reply, simulating a slow remote service. When the delay
 * exceeds the gather timeout the reply is dropped and the remaining quotes are used instead.
 */
@Slf4j
public class DelayedRateProvider implements RateProvider {

  private final RateProvider delegate;
  private final Duration delay;

  /**
   * Creates a provider that answers only after the given delay.
   *
   * @param delegate the provider that produces the actual quote
   * @param delay how long to wait before delegating
   */
  public DelayedRateProvider(RateProvider delegate, Duration delay) {
    this.delegate = delegate;
    this.delay = delay;
  }

  @Override
  public String name() {
    return delegate.name();
  }

  @Override
  public RateQuote quote(RateRequest request) {
    LOGGER.info("{} is slow and will need {} ms to answer", name(), delay.toMillis());
    try {
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException(name() + " was interrupted before answering", e);
    }
    return delegate.quote(request);
  }
}
