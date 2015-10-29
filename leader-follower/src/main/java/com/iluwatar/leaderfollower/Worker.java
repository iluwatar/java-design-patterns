package com.iluwatar.leaderfollower;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public  class Worker implements Runnable {

  private Object leader = new Object();
  private BlockingQueue<Work> queue;
  private int totalDistanceCovered = 0;
  private List<Worker> workers;
  private long id;
  private WorkStation workstation;

  public Worker(BlockingQueue<Work> queue, List<Worker> workers, long id,
      WorkStation workstation) {
    super();
    this.queue = queue;
    this.workers = workers;
    this.id = id;
    this.workstation = workstation;
  }

  public void becomeLeader() {
    synchronized (leader) {
      leader.notifyAll();
    }
  }



  @Override
  public void run() {
    while (!Thread.interrupted()) {
      try {
        if (workstation.getLeader() != null && !workstation.getLeader().equals(this)) {
          // System.out.println("ID " +id + " is follower");
          synchronized (leader) {
            leader.wait();
          }

        }
      //  
        workers.remove(this);
        System.out.println("Leader: " +id);
        Work work = queue.poll(3, TimeUnit.SECONDS);
         // System.out.println("Size " + workers.size());
          if (workers.size() > 0) {
            workstation.getWorkers().get(0).becomeLeader();
            workstation.setLeader(workstation.getWorkers().get(0));
          }
          else {
            workstation.setLeader(null);
          }
        
        Thread.sleep(100);
        totalDistanceCovered += work.distance;
        System.out.println("The Worker with the ID " + id + " completed the task");
        workstation.addWorker(this);
      } catch (InterruptedException e) {
        System.out.println("Thread intreuppted");
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Worker other = (Worker) obj;
    if (id != other.id)
      return false;
    return true;
  }

}
