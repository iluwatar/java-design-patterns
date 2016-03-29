package com.iluwatar.leaderfollower;

import java.util.List;

public class Worker implements Runnable {

  private Object leader = new Object();
  private final HandleSet handleSet;
  private List<Worker> workers;
  private final long id;
  private final WorkStation workstation;
  private final ConcreteEventHandler concreteEventHandler;

  public Worker(HandleSet queue, List<Worker> workers, long id, WorkStation workstation,
      ConcreteEventHandler concreteEventHandler) {
    super();
    this.handleSet = queue;
    this.workers = workers;
    this.id = id;
    this.workstation = workstation;
    this.concreteEventHandler = concreteEventHandler;
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
        System.out.println("Leader: " + id);
        Work work = handleSet.getPayLoad();
        if (workers.size() > 0) {
          workstation.getWorkers().get(0).becomeLeader();
          workstation.setLeader(workstation.getWorkers().get(0));
        } else {
          workstation.setLeader(null);
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
