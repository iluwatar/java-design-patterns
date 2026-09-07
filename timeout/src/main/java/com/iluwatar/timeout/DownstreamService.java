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
package com.iluwatar.timeout;

import java.time.Duration;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Simulated downstream service used by the demo.
 *
 * <p>The simulated latency decides whether the service answers within the {@link TimeoutPolicy} of
 * the caller or misses it. The sleep is interruptible, so the interrupt sent by {@link
 * TimeoutExecutor} actually stops the work instead of leaving it running in the background.
 */
@Slf4j
public class DownstreamService {

  private final String name;
  private final Duration latency;
  private final List<String> items;

  /**
   * Creates the service.
   *
   * @param name name the service is known by
   * @param latency simulated response time
   * @param items payload returned once the simulated call completes
   */
  public DownstreamService(String name, Duration latency, List<String> items) {
    this.name = name;
    this.latency = latency;
    this.items = List.copyOf(items);
  }

  /**
   * Returns the name the service is known by.
   *
   * @return the service name
   */
  public String name() {
    return name;
  }

  /**
   * Answers the call after the simulated latency.
   *
   * @return the payload of the service
   * @throws InterruptedException if the call is cancelled before the simulated latency elapses
   */
  public List<String> fetch() throws InterruptedException {
    LOGGER.info("{}: responding, expected latency {} ms", name, latency.toMillis());
    try {
      Thread.sleep(latency);
    } catch (InterruptedException e) {
      LOGGER.info("{}: interrupted, abandoning the call", name);
      throw e;
    }
    return items;
  }
}
