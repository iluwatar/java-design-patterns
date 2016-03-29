/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.leaderfollower;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkStation {
  private Worker leader;
  private List<Worker> workers = new CopyOnWriteArrayList<>();
  private ExecutorService executorService = Executors.newFixedThreadPool(4);

  public WorkStation(ExecutorService executorService) {
    this.executorService = executorService;
  }

  /**
   * Start the work. Add workers and then dispatch new work to be processed by the set of workers
   */
  public void startWork() throws InterruptedException {
    int i = 1;
    HandleSet handleSet = new HandleSet();
    ConcreteEventHandler concreteEventHandler = new ConcreteEventHandler();
    while (i <= 5) {
      Worker worker = new Worker(handleSet, workers, i, this, concreteEventHandler);
      workers.add(worker);
      i++;
    }
    this.leader = workers.get(0);
    executorService.submit(workers.get(0));
    executorService.submit(workers.get(1));
    executorService.submit(workers.get(2));
    executorService.submit(workers.get(3));
    Random rand = new Random(1000);
    int j = 0;
    while (j < 4) {
      handleSet.fireEvent(new Work(Math.abs(rand.nextInt())));
      j++;
    }

    // queue.
    Thread.sleep(1000);
  }

  public Worker getLeader() {
    return this.leader;
  }

  public void setLeader(Worker leader) {
    this.leader = leader;
  }

  /**
   * Add a worker to the work station.
   */
  public void addWorker(Worker worker) {
    if (this.workers.size() <= 0) {
      workers.add(worker);
    }
  }

  public List<Worker> getWorkers() {
    return workers;
  }
}
