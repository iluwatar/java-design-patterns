package com.iluwatar.leaderfollower;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Represents the Workstation for the leader Follower pattern. Contains a leader and a list of idle
 * workers. Contains a leader who is responsible for receiving work when it arrives. This class also provides a mechanism
 * to set the leader. A worker once he completes his task will add himself back to the station.
 * 
 * @author amit
 *
 */

public class WorkStation {
  private Worker leader;
  private List<Worker> workers = new CopyOnWriteArrayList<>();
  private ExecutorService executorService = Executors.newFixedThreadPool(4);

  public WorkStation(ExecutorService executorService) {
    this.executorService = executorService;
  }

  public void startWork() throws InterruptedException {

    HandleSet handleSet = new HandleSet();
    ConcreteEventHandler concreteEventHandler = new ConcreteEventHandler();
    Worker worker = new Worker(handleSet, workers, 1, this, concreteEventHandler);
    Worker worker2 = new Worker(handleSet, workers, 2, this, concreteEventHandler);
    Worker worker3 = new Worker(handleSet, workers, 3, this, concreteEventHandler);
    Worker worker4 = new Worker(handleSet, workers, 4, this, concreteEventHandler);
    workers.add(worker);
    workers.add(worker2);
    workers.add(worker3);
    workers.add(worker4);
    this.leader = workers.get(0);
    executorService.submit(worker);
    executorService.submit(worker2);
    executorService.submit(worker3);
    executorService.submit(worker4);
    Random rand = new Random(1000);
    handleSet.fireEvent(new Work(Math.abs(rand.nextInt())));
    handleSet.fireEvent(new Work(Math.abs(rand.nextInt())));
    handleSet.fireEvent(new Work(Math.abs(rand.nextInt())));
    handleSet.fireEvent(new Work(Math.abs(rand.nextInt())));
    handleSet.fireEvent(new Work(Math.abs(rand.nextInt())));
    // queue.
    Thread.sleep(1000);
  }

  public Worker getLeader() {
    return this.leader;
  }

  public void setLeader(Worker leader) {
    this.leader = leader;
  }

  public void addWorker(Worker worker) {
    if (this.workers.size() <= 0) {
      this.leader = worker;
      workers.add(worker);
    }
  }

  public List<Worker> getWorkers() {
    return workers;
  }
}
