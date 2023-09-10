package com.iluwatar.scheduler;

import java.beans.PropertyChangeSupport;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Task {
  public static final String COMPLETE_PROPERTY = "complete";
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);
  private final int id;
  private final int totalExecutionTime;

  /** The priority of the task. The higher the number, the higher the priority. */
  private int priority = 0;

  private int elapsedTime = 0;
  private boolean complete = false;

  public Task(int id, int totalExecutionTime, int priority) {
    this.id = id;
    this.totalExecutionTime = totalExecutionTime;
    this.priority = priority;
  }

  public void execute(int seconds) {
    if (complete) throw new IllegalStateException("Task already completed");

    elapsedTime += seconds;

    // Uncomment the following line to see the execution of tasks
    // System.out.printf("%d, %d/%d\n", id, elapsedTime, totalExecutionTime);

    if (elapsedTime >= totalExecutionTime) {
      complete = true;
      support.firePropertyChange(COMPLETE_PROPERTY, false, true);
    }
  }

  public int getRemainingTime() {
    return totalExecutionTime - elapsedTime;
  }
}
