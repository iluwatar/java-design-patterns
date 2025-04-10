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
