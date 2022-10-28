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

package com.iluwatar.leaderfollowers;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
public class Worker implements Runnable {

  @EqualsAndHashCode.Include
  private final long id;
  private final WorkCenter workCenter;
  private final TaskSet taskSet;
  private final TaskHandler taskHandler;

  /**
   * Constructor to create a worker which will take work from the work center.
   */
  public Worker(long id, WorkCenter workCenter, TaskSet taskSet, TaskHandler taskHandler) {
    super();
    this.id = id;
    this.workCenter = workCenter;
    this.taskSet = taskSet;
    this.taskHandler = taskHandler;
  }

  /**
   * The leader thread listens for task. When task arrives, it promotes one of the followers to be
   * the new leader. Then it handles the task and add himself back to work center.
   */
  @Override
  public void run() {
    while (!Thread.interrupted()) {
      try {
        if (workCenter.getLeader() != null && !workCenter.getLeader().equals(this)) {
          synchronized (workCenter) {
            workCenter.wait();
          }
          continue;
        }
        final Task task = taskSet.getTask();
        synchronized (workCenter) {
          workCenter.removeWorker(this);
          workCenter.promoteLeader();
          workCenter.notifyAll();
        }
        taskHandler.handleTask(task);
        LOGGER.info("The Worker with the ID " + id + " completed the task");
        workCenter.addWorker(this);
      } catch (InterruptedException e) {
        LOGGER.warn("Worker interrupted");
        Thread.currentThread().interrupt();
        return;
      }
    }
  }

}
