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

import lombok.Getter;

/**
 * Base class that keeps the time accounting shared by every task. Subclasses only implement {@link
 * #doWork(int)}, the actual work done in one time slice.
 */
@Getter
public abstract class AbstractTask implements Task {
  private final int id;
  private final int totalExecutionTime;
  private final int priority;
  private int elapsedTime;

  protected AbstractTask(int id, int totalExecutionTime, int priority) {
    if (totalExecutionTime <= 0) {
      throw new IllegalArgumentException("Total execution time must be positive");
    }
    this.id = id;
    this.totalExecutionTime = totalExecutionTime;
    this.priority = priority;
  }

  @Override
  public int getRemainingTime() {
    return totalExecutionTime - elapsedTime;
  }

  @Override
  public boolean isComplete() {
    return elapsedTime >= totalExecutionTime;
  }

  @Override
  public void execute(int deltaTime) {
    if (isComplete()) {
      throw new IllegalStateException("Task " + id + " is already complete");
    }
    var units = Math.min(deltaTime, getRemainingTime());
    doWork(units);
    elapsedTime += units;
  }

  /** Perform the task's own work for the given number of time units. */
  protected abstract void doWork(int units);
}
