package com.iluwatar.leaderfollower;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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

  public void startWork() throws InterruptedException {

    BlockingQueue<Work> queue = new ArrayBlockingQueue<>(100);
    Worker worker = new Worker(queue, workers, 1, this);
    Worker worker2 = new Worker(queue, workers, 2, this);
    Worker worker3 = new Worker(queue, workers, 3, this);
    Worker worker4 = new Worker(queue, workers, 4, this);
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
    queue.put(new Work(rand.nextInt()));
    queue.put(new Work(rand.nextInt()));
    queue.put(new Work(rand.nextInt()));
    queue.put(new Work(rand.nextInt()));
    queue.put(new Work(rand.nextInt()));
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
