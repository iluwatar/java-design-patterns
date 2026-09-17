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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class ScatterGatherExecutorTest {

  @Test
  void shouldGatherResultsFromAllSuccessfulTasks() throws InterruptedException {
    var executor = new ScatterGatherExecutor();

    List<TaskSupplier> tasks =
        List.of(
            () -> "Delta Airlines: $350.00",
            () -> "United Airlines: $410.00",
            () -> "Southwest Airlines: $325.00");

    var results = executor.scatterGather(tasks, 1000);

    assertEquals(
        List.of(
            "Delta Airlines: $350.00", "United Airlines: $410.00", "Southwest Airlines: $325.00"),
        results);
  }

  @Test
  void shouldReturnOnlyTasksThatFinishBeforeTimeout() throws InterruptedException {
    var executor = new ScatterGatherExecutor();

    TaskSupplier fastTask = () -> "Fast Airline: $300.00";

    TaskSupplier slowTask =
        () -> {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          return "Slow Airline: $500.00";
        };

    var results = executor.scatterGather(List.of(fastTask, slowTask), 100);

    assertEquals(List.of("Fast Airline: $300.00"), results);
  }

  @Test
  void shouldIgnoreFailedTasksAndKeepSuccessfulResults() throws InterruptedException {
    var executor = new ScatterGatherExecutor();

    TaskSupplier successfulTask = () -> "Delta Airlines: $350.00";

    TaskSupplier failedTask =
        () -> {
          throw new IllegalStateException("Airline service unavailable");
        };

    var results = executor.scatterGather(List.of(successfulTask, failedTask), 1000);

    assertEquals(List.of("Delta Airlines: $350.00"), results);
  }

  @Test
  void shouldReturnEmptyListWhenNoTasksAreProvided() throws InterruptedException {
    var executor = new ScatterGatherExecutor();

    var results = executor.scatterGather(List.of(), 1000);

    assertEquals(List.of(), results);
  }
}
