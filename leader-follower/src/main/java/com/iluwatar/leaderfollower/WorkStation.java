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
    int i =1;
    
    while (i <= 4) {
      Worker worker = new Worker(queue, workers, i, this);
      workers.add(worker);
    }
    this.leader = workers.get(0);
    executorService.submit( workers.get(0));
    executorService.submit( workers.get(1));
    executorService.submit( workers.get(2));
    executorService.submit( workers.get(3));
    Random rand = new Random(1000);
    int j =0;
    while (j < 4) {
      queue.put(new Work(rand.nextInt()));
      queue.put(new Work(rand.nextInt()));
      queue.put(new Work(rand.nextInt()));
      queue.put(new Work(rand.nextInt()));
      queue.put(new Work(rand.nextInt()));
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
