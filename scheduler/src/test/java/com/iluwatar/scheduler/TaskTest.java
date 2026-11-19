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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TaskTest {

  @Test
  void taskCompletesAfterItsExecutionTime() {
    var task = new SimpleTask(1, 3, 2);
    task.execute(2);
    assertFalse(task.isComplete());
    assertEquals(1, task.getRemainingTime());
    task.execute(1);
    assertTrue(task.isComplete());
    assertEquals(0, task.getRemainingTime());
    assertEquals(2, task.getPriority());
  }

  @Test
  void executingCompletedTaskThrows() {
    var task = new SimpleTask(1, 1);
    task.execute(1);
    assertThrows(IllegalStateException.class, () -> task.execute(1));
  }

  @Test
  void taskRejectsNonPositiveExecutionTime() {
    assertThrows(IllegalArgumentException.class, () -> new SimpleTask(1, 0));
  }

  @Test
  void sumTaskResumesWorkAcrossSlices() {
    var task = new SumTask(1, 10);
    task.execute(4);
    assertEquals(10, task.getSum());
    task.execute(100);
    assertEquals(55, task.getSum());
    assertTrue(task.isComplete());
  }
}
