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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class SimulatorTest {

  @Test
  void intervalTriggerSchedulesFreshTaskEachPeriod() {
    var ids = new AtomicInteger();
    var job =
        new ScheduledJob(new IntervalTrigger(0, 5), () -> new SimpleTask(ids.incrementAndGet(), 1));

    var completedAt =
        new Simulator(new FirstComeFirstServedScheduler(), List.of(job), 1, 15).simulate();

    assertEquals(Map.of(1, 1, 2, 6, 3, 11), completedAt);
  }

  @Test
  void conditionTriggerStartsTaskAfterAnotherCompletes() {
    var first = new SimpleTask(1, 2);
    var jobs =
        List.of(
            ScheduledJob.at(0, first),
            new ScheduledJob(new ConditionTrigger(first::isComplete), () -> new SimpleTask(2, 1)));

    var completedAt = new Simulator(new FirstComeFirstServedScheduler(), jobs, 1, 10).simulate();

    assertEquals(List.of(1, 2), List.copyOf(completedAt.keySet()));
    assertEquals(2, completedAt.get(1));
    assertEquals(3, completedAt.get(2));
  }

  @Test
  void simulatorRejectsNonPositiveDeltaTime() {
    var scheduler = new FirstComeFirstServedScheduler();
    List<ScheduledJob> jobs = List.of();
    assertThrows(IllegalArgumentException.class, () -> new Simulator(scheduler, jobs, 0, 10));
  }
}
