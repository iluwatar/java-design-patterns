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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PrioritySchedulerTest {
  @Test
  void testExecuteTasksFromBeginning() {
    List<ScheduledJob> jobs = new ArrayList<>();
    jobs.add(ScheduledJob.at(0, new SimpleTask(1, 3, 0)));
    jobs.add(ScheduledJob.at(0, new SimpleTask(2, 2, 2)));
    jobs.add(ScheduledJob.at(0, new SimpleTask(3, 1, 1)));

    Simulator simulator = new Simulator(new PriorityScheduler(), jobs, 1, 10);

    Map<Integer, Integer> taskCompletedOrder = simulator.simulate();

    assertEquals(3, taskCompletedOrder.size());
    assertIterableEquals(List.of(2, 3, 1), taskCompletedOrder.keySet());
    assertEquals(2, taskCompletedOrder.get(2));
    assertEquals(3, taskCompletedOrder.get(3));
    assertEquals(6, taskCompletedOrder.get(1));
  }

  @Test
  void testExecuteTasks() {
    List<ScheduledJob> jobs = new ArrayList<>();
    jobs.add(ScheduledJob.at(0, new SimpleTask(1, 3, 1)));
    jobs.add(ScheduledJob.at(1, new SimpleTask(2, 2, 0)));
    jobs.add(ScheduledJob.at(2, new SimpleTask(3, 4, 5)));
    jobs.add(ScheduledJob.at(7, new SimpleTask(4, 2, 4)));
    jobs.add(ScheduledJob.at(8, new SimpleTask(5, 1, 2)));

    Simulator simulator = new Simulator(new PriorityScheduler(), jobs, 1, 100);

    Map<Integer, Integer> taskCompletedOrder = simulator.simulate();

    assertEquals(5, taskCompletedOrder.size());
    assertIterableEquals(List.of(3, 1, 4, 5, 2), taskCompletedOrder.keySet());
    assertEquals(6, taskCompletedOrder.get(3));
    assertEquals(7, taskCompletedOrder.get(1));
    assertEquals(9, taskCompletedOrder.get(4));
    assertEquals(10, taskCompletedOrder.get(5));
    assertEquals(12, taskCompletedOrder.get(2));
  }
}
