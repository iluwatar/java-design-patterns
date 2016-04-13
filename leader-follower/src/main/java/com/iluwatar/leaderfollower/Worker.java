/**
 * The MIT License Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.leaderfollower;

import java.util.List;

public class Worker implements Runnable {

  private final HandleSet handleSet;
  private List<Worker> workers;
  private final long id;
  private final WorkStation workstation;
  private final ConcreteEventHandler concreteEventHandler;

  /**
   * 
   * Constructor to create a worker which will take work from the work station.
   */
  public Worker(HandleSet queue, List<Worker> workers, long id, WorkStation workstation,
      ConcreteEventHandler concreteEventHandler) {
    super();
    this.handleSet = queue;
    this.workers = workers;
    this.id = id;
    this.workstation = workstation;
    this.concreteEventHandler = concreteEventHandler;
  }

  /**
   * Become the leader, and notify others
   */
  public void becomeLeader() {
    synchronized (workstation) {
      workstation.notifyAll();
    }
  }



  @Override
  public void run() {
    while (!Thread.interrupted()) {
      try {
        if (workstation.getLeader() != null && !workstation.getLeader().equals(this)) {
          // System.out.println("ID " +id + " is follower");
          synchronized (workstation) {
            workstation.wait();
          }

        }
        //
        workers.remove(this);
        System.out.println("Leader: " + id);
        Work work = handleSet.getPayLoad();
        if (workers.size() > 0) {
          workstation.getWorkers().get(0).becomeLeader();
          workstation.setLeader(workstation.getWorkers().get(0));
        } else {
          workstation.setLeader(null);
        }
        synchronized (workstation) {
          workstation.notifyAll();
        }
        concreteEventHandler.handleEvent(work);
        Thread.sleep(100);
        System.out.println("The Worker with the ID " + id + " completed the task");
        workstation.addWorker(this);
      } catch (InterruptedException e) {
        System.out.println("Worker intreuppted");
        return;
      }
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (id ^ (id >>> 32));
    return result;
  }

  /**
   * Overridden equals method
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Worker other = (Worker) obj;
    if (id != other.id) {
      return false;
    }
    return true;
  }

}
