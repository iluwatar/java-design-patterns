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

package com.iluwatar.fanout.fanin;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * FanOutFanIn class processes long running requests, when any of the processes gets over, result is
 * passed over to the consumer or the callback function. Consumer will aggregate the results as they
 * keep on completing.
 */
public class FanOutFanIn {

  /**
   * the main fanOutFanIn function or orchestrator function.
   * @param requests List of numbers that need to be squared and summed up
   * @param consumer Takes in the squared number from {@link SquareNumberRequest} and sums it up
   * @return Aggregated sum of all squared numbers.
   */
  public static Long fanOutFanIn(
      final List<SquareNumberRequest> requests, final Consumer consumer) {

    ExecutorService service = Executors.newFixedThreadPool(requests.size());

    // fanning out
    List<CompletableFuture<Void>> futures =
        requests.stream()
            .map(
                request ->
                    CompletableFuture.runAsync(() -> request.delayedSquaring(consumer), service))
            .collect(Collectors.toList());

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

    return consumer.getSumOfSquaredNumbers().get();
  }
}
