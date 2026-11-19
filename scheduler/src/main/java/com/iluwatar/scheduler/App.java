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
package com.iluwatar.scheduler;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

/**
 * The Scheduler pattern separates what work is done ({@link Task}), when it becomes ready ({@link
 * Trigger}) and in which order it runs ({@link TaskScheduler}). The {@link Simulator} is the
 * execution context that ties them together.
 *
 * <p>This example runs the same workload through every scheduling strategy. The workload uses all
 * three trigger types: tasks arriving at fixed times, a report repeating on an interval, and a
 * cleanup task that starts only after a backup completes.
 */
@Slf4j
public class App {

  /** Program entry point. */
  public static void main(String[] args) {
    List<TaskScheduler> schedulers =
        List.of(
            new FirstComeFirstServedScheduler(),
            new PriorityScheduler(),
            new RoundRobinScheduler(),
            new ShortestRemainingTimeFirstScheduler());
    for (var scheduler : schedulers) {
      var completedAt = new Simulator(scheduler, workload(), 1, 30).simulate();
      LOGGER.info("{} completed tasks at: {}", scheduler.getClass().getSimpleName(), completedAt);
    }
  }

  /** Build a fresh workload, since tasks keep their progress once run. */
  static List<ScheduledJob> workload() {
    var backup = new SimpleTask(1, 5, 1);
    var reportIds = new AtomicInteger(100);
    return List.of(
        ScheduledJob.at(0, backup),
        ScheduledJob.at(1, new SumTask(2, 4, 3)),
        new ScheduledJob(
            new IntervalTrigger(2, 10), () -> new SimpleTask(reportIds.incrementAndGet(), 2, 2)),
        new ScheduledJob(new ConditionTrigger(backup::isComplete), () -> new SimpleTask(3, 3)));
  }
}
