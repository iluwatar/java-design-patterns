package com.iluwatar.scheduler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

/** Simulate scheduler schedule tasks. */
@RequiredArgsConstructor
public class Simulator implements PropertyChangeListener {
  private final TaskScheduler scheduler;

  /** Map time to tasks that need to be scheduled at that time. */
  private final Map<Integer, List<Task>> tasks;

  private final int deltaTime;
  private final int simulateTime;
  private final LinkedHashMap<Integer, Integer> taskCompletedOrder = new LinkedHashMap<>();
  private int elapsedTime = 0;

  public LinkedHashMap<Integer, Integer> simulate() {
    while (elapsedTime < simulateTime) {
      if (tasks.containsKey(elapsedTime)) {
        for (Task task : tasks.get(elapsedTime)) {
          task.getSupport().addPropertyChangeListener(this);
          scheduler.scheduleTask(task);
        }
      }
      scheduler.update(deltaTime);
      elapsedTime += deltaTime;
    }
    return taskCompletedOrder;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
    if (Task.COMPLETE_PROPERTY.equals(evt.getPropertyName())) {
      onTaskComplete(evt);
    }
  }

  private void onTaskComplete(PropertyChangeEvent evt) {
    Task task = (Task) evt.getSource();
    /*
      elapsedTime is updated after task dispatch complete event to simulator,
      so we need to add deltaTime
    */
    taskCompletedOrder.put(task.getId(), elapsedTime + deltaTime);
  }
}
