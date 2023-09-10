package com.iluwatar.scheduler;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class ShortestRemainingTimeFirstScheduler implements TaskScheduler {
  private final Queue<Task> taskQueue =
      new PriorityQueue<>(
          (task1, task2) -> {
            if (task2.getRemainingTime() != task1.getRemainingTime())
              return task1.getRemainingTime() - task2.getRemainingTime();
            return task1.getId() - task2.getId(); // lower id (earlier task) has higher priority
          });

  @Override
  public void scheduleTask(Task task) {
    taskQueue.add(task);
  }

  @Override
  public void update(int deltaTime) {
    Task task = taskQueue.poll();
    if (task == null) return;
    task.execute(deltaTime);
    if (!task.isComplete()) taskQueue.add(task);
  }
}
