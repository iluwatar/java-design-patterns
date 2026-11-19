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

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * The task with the shortest remaining time runs next, so short tasks preempt long ones. Ties go to
 * the task created first.
 */
public class ShortestRemainingTimeFirstScheduler implements TaskScheduler {
  private final Queue<Task> taskQueue =
      new PriorityQueue<>(
          Comparator.comparingInt(Task::getRemainingTime).thenComparingInt(Task::getId));

  @Override
  public void scheduleTask(Task task) {
    taskQueue.add(task);
  }

  @Override
  public void update(int deltaTime) {
    // Poll and re-add: the remaining time is the queue key and changes on every run.
    var task = taskQueue.poll();
    if (task == null) {
      return;
    }
    task.execute(deltaTime);
    if (!task.isComplete()) {
      taskQueue.add(task);
    }
  }
}
