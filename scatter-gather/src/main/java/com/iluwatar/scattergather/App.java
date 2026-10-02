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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

/**
 * The Scatter-Gather pattern sends one request to several independent recipients at the same time,
 * gathers whatever replies arrive within a deadline, and aggregates them into a single answer. It
 * is the integration counterpart of the fan-out/fan-in pattern: fan-out/fan-in splits one job into
 * sub-tasks of the same kind, whereas scatter-gather broadcasts the same message to different
 * services and has to cope with some of them being slow or unavailable.
 *
 * <p>This demo models a travel site that asks four hotel rate providers for the price of the same
 * stay. One provider is slower than the gather timeout and one is down. The site still answers,
 * using the quotes from the two healthy providers, and the {@link Aggregator} picks the cheapest.
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line arguments, unused
   */
  public static void main(String[] args) {
    var request = new RateRequest("Lisbon", LocalDate.of(2026, 10, 3), 3);
    var providers =
        List.<RateProvider>of(
            new InMemoryRateProvider("Atlas Hotels", new BigDecimal("129.00")),
            new InMemoryRateProvider("Harbor Stays", new BigDecimal("98.50")),
            new DelayedRateProvider(
                new InMemoryRateProvider("Sleepy Suites", new BigDecimal("75.00")),
                Duration.ofSeconds(2)),
            new FailingRateProvider("Flaky Inns"));

    try (var scatterGather =
        new ScatterGather(Executors.newFixedThreadPool(providers.size()), Duration.ofMillis(300))) {
      LOGGER.info("Scatter phase: broadcasting the same request to every provider");
      var pending = scatterGather.scatter(request, providers);

      LOGGER.info("Gather phase: collecting replies that arrive within the timeout");
      var quotes = scatterGather.gather(pending);

      LOGGER.info("Aggregate phase: choosing the cheapest of {} quotes", quotes.size());
      reportBestOffer(Aggregator.cheapestQuote().aggregate(quotes));
    }
  }

  /**
   * Logs the aggregated result, or the fact that no provider answered in time.
   *
   * @param best the cheapest gathered quote, empty when every provider failed or timed out
   */
  static void reportBestOffer(Optional<RateQuote> best) {
    best.ifPresentOrElse(
        offer -> LOGGER.info("Best offer: {} at {}", offer.provider(), offer.total()),
        () -> LOGGER.info("No provider answered in time"));
  }
}
