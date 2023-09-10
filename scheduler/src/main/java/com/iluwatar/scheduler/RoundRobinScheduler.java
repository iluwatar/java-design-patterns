package com.iluwatar.scheduler;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Round Robin technique. Tasks are executed in a cyclic order, with each task getting a fixed time
 * quantum for execution.
 */
public class RoundRobinScheduler implements TaskScheduler {
  private final Queue<Task> taskQueue = new LinkedList<>();

  @Override
  public void scheduleTask(Task task) {
    taskQueue.add(task);
  }

  @Override
  public void update(int deltaTime) {
    Task task = taskQueue.poll();
    if (task == null) {
      return;
    }
    task.execute(deltaTime);
    if (!task.isComplete()) {
      taskQueue.add(task);
    }
  }
}
