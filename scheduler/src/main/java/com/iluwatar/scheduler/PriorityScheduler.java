package com.iluwatar.scheduler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.PriorityQueue;
import java.util.Queue;

/** Tasks with higher priority values are executed before tasks with lower priority values. */
public class PriorityScheduler implements TaskScheduler, PropertyChangeListener {
  private final Queue<Task> taskQueue =
      new PriorityQueue<>(
          (task1, task2) -> {
            if (task2.getPriority() != task1.getPriority()) {
              return task2.getPriority() - task1.getPriority();
            }
            return task1.getId() - task2.getId(); // lower id (earlier task) has higher priority
          });

  @Override
  public void scheduleTask(Task task) {
    task.getSupport().addPropertyChangeListener(this);
    taskQueue.add(task);
  }

  @Override
  public void update(int deltaTime) {
    Task task = taskQueue.peek();
    if (task == null) {
      return;
    }
    task.execute(deltaTime);
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (Task.COMPLETE_PROPERTY.equals(evt.getPropertyName())) {
      onTaskComplete(evt);
    }
  }

  private void onTaskComplete(PropertyChangeEvent evt) {
    Task task = (Task) evt.getSource();
    taskQueue.remove(task);
  }
}
