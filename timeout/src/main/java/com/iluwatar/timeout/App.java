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
 * The Timeout pattern bounds how long a caller waits for a downstream service. Without a limit a
 * single slow dependency can hold threads, connections and user requests hostage until the whole
 * system stalls. With a limit the caller abandons the slow call, records the event and continues
 * with a fallback, keeping latency predictable and failures contained.
 *
 * <p>The building blocks are a {@link TimeoutPolicy} per service and a {@link TimeoutExecutor} that
 * enforces them, cancels calls that overrun, and counts timeouts in {@link TimeoutMetrics}.
 *
 * <p>The demo wires two services with different limits. The product catalog answers well within its
 * 500 ms budget and returns real data. The recommendation engine needs 400 ms but is only allowed
 * 100 ms, so its call is cancelled and the customer sees popular items instead. The timeout
 * counters are printed at the end.
 */
@Slf4j
public class App {

  private static final List<String> POPULAR_ITEMS = List.of("Wireless mouse", "Webcam");

  /**
   * Program entry point.
   *
   * @param args command line arguments, not used
   */
  public static void main(String[] args) {
    var catalog =
        new DownstreamService(
            "product-catalog", Duration.ofMillis(50), List.of("Laptop", "Headphones", "Monitor"));
    var recommendations =
        new DownstreamService(
            "recommendations",
            Duration.ofMillis(400),
            List.of("Mechanical keyboard", "USB-C dock"));
    var catalogPolicy = TimeoutPolicy.of(catalog.name(), 500);
    var recommendationPolicy = TimeoutPolicy.of(recommendations.name(), 100);
    LOGGER.info("Configured per-service limits: catalog 500 ms, recommendations 100 ms");

    try (var executor = new TimeoutExecutor()) {
      LOGGER.info("Calling {}", catalog.name());
      var products = call(executor, catalogPolicy, catalog, List.of());
      LOGGER.info("Products: {}", products);

      LOGGER.info("Calling {}", recommendations.name());
      var suggested = call(executor, recommendationPolicy, recommendations, POPULAR_ITEMS);
      LOGGER.info("Recommendations shown to alice: {}", suggested);

      LOGGER.info("Timeouts per service: {}", executor.metrics().snapshot());
    }
  }

  /**
   * Calls a service under its time limit, answering with the fallback if the limit is exceeded.
   *
   * @param executor executor enforcing the limit
   * @param policy limit that applies to the service
   * @param service the downstream service to call
   * @param fallback answer to use when the limit is exceeded
   * @return the response of the service, or the fallback on timeout
   */
  static List<String> call(
      TimeoutExecutor executor,
      TimeoutPolicy policy,
      DownstreamService service,
      List<String> fallback) {
    return executor.execute(policy, service::fetch, () -> fallback);
  }
}
