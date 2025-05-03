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
package com.iluwatar.leaderfollowers;

import java.security.SecureRandom;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

  public static void main(String[] args) throws InterruptedException {
    var taskSet = new TaskSet();
    var taskHandler = new TaskHandler();
    var workCenter = new WorkCenter();
    workCenter.createWorkers(4, taskSet, taskHandler);
    execute(workCenter, taskSet);
  }

  private static void execute(WorkCenter workCenter, TaskSet taskSet) throws InterruptedException {
    var workers = workCenter.getWorkers();
    var exec = Executors.newFixedThreadPool(workers.size());

    try {
      workers.forEach(exec::submit);
      Thread.sleep(1000);
      addTasks(taskSet);
      boolean terminated = exec.awaitTermination(2, TimeUnit.SECONDS);
      if (!terminated) {
        LOGGER.warn("Executor did not terminate in the given time.");
      }
    } finally {
      exec.shutdownNow();
    }
  }

  private static void addTasks(TaskSet taskSet) throws InterruptedException {
    var rand = new SecureRandom();
    for (var i = 0; i < 5; i++) {
      var time = Math.abs(rand.nextInt(1000));
      taskSet.addTask(new Task(time));
    }
  }
}
