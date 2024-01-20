package com.iluwatar.scheduler;

import java.beans.PropertyChangeSupport;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** Task that need to be scheduled. */
@Getter
@RequiredArgsConstructor
@Slf4j
public class Task {
  public static final String COMPLETE_PROPERTY = "complete";
  private final PropertyChangeSupport support = new PropertyChangeSupport(this);
  private final int id;
  private final int totalExecutionTime;

  /** The priority of the task. The higher the number, the higher the priority. */
  private int priority = 0;

  /** The time that the task run. */
  private int elapsedTime = 0;

  /** Whether the task is completed. */
  private boolean complete = false;

  /** Create a task with id, total execution time and priority. */
  public Task(int id, int totalExecutionTime, int priority) {
    this.id = id;
    this.totalExecutionTime = totalExecutionTime;
    this.priority = priority;
  }

  /** Execute the task for a given number of seconds. */
  public void execute(int seconds) {
    if (complete) {
      throw new IllegalStateException("Task already completed");
    }

    elapsedTime += seconds;

    logTaskProgress();

    if (elapsedTime >= totalExecutionTime) {
      complete = true;
      support.firePropertyChange(COMPLETE_PROPERTY, false, true);
    }
  }

  private void logTaskProgress() {
    LOGGER.info("Task {} is {}/{} complete\n", id, elapsedTime, totalExecutionTime);
  }

  public int getRemainingTime() {
    return totalExecutionTime - elapsedTime;
  }
}
