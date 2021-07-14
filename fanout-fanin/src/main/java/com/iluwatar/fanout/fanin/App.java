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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;



/**
 * FanOut/FanIn pattern is a concurrency pattern that refers to executing multiple instances of the
 * activity function concurrently. The "fan out" part is essentially splitting the data into
 * multiple chunks and then calling the activity function multiple times, passing the chunks.
 *
 * <p>When each chunk has been processed, the "fan in" takes place that aggregates results from each
 * instance of function and forms a single final result.
 *
 * <p>This pattern is only really useful if you can “chunk” the workload in a meaningful way for
 * splitting up to be processed in parallel.
 */
@Slf4j
public class App {

  /**
   * Entry point.
   *
   * <p>Implementation provided has a list of numbers that has to be squared and added. The list can
   * be chunked in any way and the "activity function" {@link
   * SquareNumberRequest#delayedSquaring(Consumer)} i.e. squaring the number ca be done
   * concurrently. The "fan in" part is handled by the {@link Consumer} that takes in the result
   * from each instance of activity and aggregates it whenever that particular activity function
   * gets over.
   */
  public static void main(String[] args) {
    final List<Long> numbers = Arrays.asList(1L, 3L, 4L, 7L, 8L);

    LOGGER.info("Numbers to be squared and get sum --> {}", numbers);

    final List<SquareNumberRequest> requests =
        numbers.stream().map(SquareNumberRequest::new).collect(Collectors.toList());

    var consumer = new Consumer(0L);

    // Pass the request and the consumer to fanOutFanIn or sometimes referred as Orchestrator
    // function
    final Long sumOfSquaredNumbers = FanOutFanIn.fanOutFanIn(requests, consumer);

    LOGGER.info("Sum of all squared numbers --> {}", sumOfSquaredNumbers);
  }
}
