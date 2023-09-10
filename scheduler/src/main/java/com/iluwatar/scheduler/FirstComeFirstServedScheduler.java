package com.iluwatar.scheduler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.Queue;

public class FirstComeFirstServedScheduler implements TaskScheduler, PropertyChangeListener {
  private final Queue<Task> taskQueue = new LinkedList<>();

  @Override
  public void scheduleTask(Task task) {
    task.getSupport().addPropertyChangeListener(this);
    taskQueue.add(task);
  }

  @Override
  public void update(int deltaTime) {
    Task task = taskQueue.peek();
    if (task == null) return;
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
