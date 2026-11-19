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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The execution context: advances a clock, fires the triggers of each job, hands the created tasks
 * to the scheduler and records when each task completes.
 */
public class Simulator {
  private final TaskScheduler scheduler;
  private final List<ScheduledJob> jobs;
  private final int deltaTime;
  private final int simulateTime;

  /**
   * Create a simulator.
   *
   * @param scheduler the scheduling strategy under test
   * @param jobs the jobs whose triggers create tasks; jobs firing at the same time are scheduled in
   *     list order
   * @param deltaTime time units the scheduler runs per step
   * @param simulateTime total time units to simulate
   */
  public Simulator(
      TaskScheduler scheduler, List<ScheduledJob> jobs, int deltaTime, int simulateTime) {
    if (deltaTime <= 0) {
      throw new IllegalArgumentException("Delta time must be positive");
    }
    this.scheduler = scheduler;
    this.jobs = List.copyOf(jobs);
    this.deltaTime = deltaTime;
    this.simulateTime = simulateTime;
  }

  /**
   * Run the simulation.
   *
   * @return task ids in the order they completed, each mapped to the time it completed
   */
  public Map<Integer, Integer> simulate() {
    Map<Integer, Integer> completedAt = new LinkedHashMap<>();
    List<Task> running = new ArrayList<>();
    for (var time = 0; time < simulateTime; time += deltaTime) {
      for (var job : jobs) {
        if (job.trigger().fires(time)) {
          var task = job.taskFactory().get();
          running.add(task);
          scheduler.scheduleTask(task);
        }
      }
      scheduler.update(deltaTime);
      var finishedAt = time + deltaTime;
      running.removeIf(
          task -> {
            if (task.isComplete()) {
              completedAt.put(task.getId(), finishedAt);
              return true;
            }
            return false;
          });
    }
    return completedAt;
  }
}
