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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Reduces the gathered replies into a single result. The aggregation strategy is pluggable so the
 * same scatter and gather machinery can serve callers that want the cheapest quote, the average
 * price, or the full list.
 *
 * @param <R> the type of the aggregated result
 */
@FunctionalInterface
public interface Aggregator<R> {

  /**
   * Combines the gathered replies.
   *
   * @param replies the replies that arrived in time, possibly empty
   * @return the aggregated result
   */
  R aggregate(List<RateQuote> replies);

  /** Returns an aggregator that picks the quote with the lowest total price. */
  static Aggregator<Optional<RateQuote>> cheapestQuote() {
    return replies -> replies.stream().min(Comparator.comparing(RateQuote::total));
  }
}
