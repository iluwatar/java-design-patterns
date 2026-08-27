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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/** Executes multiple tasks concurrently and gathers the results that complete within a timeout. */
public class ScatterGatherExecutor {

  /**
   * Executes the supplied tasks concurrently.
   *
   * @param tasks tasks to execute
   * @param timeoutMs maximum time to wait in milliseconds
   * @return results from tasks that completed successfully
   * @throws InterruptedException if the current thread is interrupted while waiting
   */
  public List<String> scatterGather(List<TaskSupplier> tasks, long timeoutMs)
      throws InterruptedException {

    if (tasks.isEmpty()) {
      return List.of();
    }

    ExecutorService executor = Executors.newFixedThreadPool(tasks.size());

    try {
      List<Callable<String>> callables =
          tasks.stream().map(task -> (Callable<String>) task::execute).toList();

      List<Future<String>> futures =
          executor.invokeAll(callables, timeoutMs, TimeUnit.MILLISECONDS);

      List<String> results = new ArrayList<>();

      for (Future<String> future : futures) {
        if (!future.isCancelled()) {
          try {
            results.add(future.get());
          } catch (ExecutionException ignored) {
            // Failed tasks do not prevent successful results from being gathered.
          }
        }
      }

      return results;

    } finally {
      executor.shutdownNow();
    }
  }
}
