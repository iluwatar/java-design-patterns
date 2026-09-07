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

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Counts timeout events per service so that operators can spot dependencies that regularly miss
 * their limits.
 */
public class TimeoutMetrics {

  private final ConcurrentMap<String, AtomicInteger> timeouts = new ConcurrentHashMap<>();

  /**
   * Records one timeout for a service.
   *
   * @param serviceName name of the service that missed its limit
   */
  public void recordTimeout(String serviceName) {
    timeouts.computeIfAbsent(serviceName, name -> new AtomicInteger()).incrementAndGet();
  }

  /**
   * Returns how many times a service has timed out.
   *
   * @param serviceName name of the service
   * @return the timeout count, zero if the service never timed out
   */
  public int timeoutCount(String serviceName) {
    var counter = timeouts.get(serviceName);
    return counter == null ? 0 : counter.get();
  }

  /**
   * Returns a sorted, read-only view of all counters.
   *
   * @return service name to timeout count
   */
  public SortedMap<String, Integer> snapshot() {
    var snapshot = new TreeMap<String, Integer>();
    timeouts.forEach((name, counter) -> snapshot.put(name, counter.get()));
    return Collections.unmodifiableSortedMap(snapshot);
  }
}
