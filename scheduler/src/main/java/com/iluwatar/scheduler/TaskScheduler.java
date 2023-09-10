package com.iluwatar.scheduler;

/** The scheduler is responsible for scheduling tasks. */
public interface TaskScheduler {
  /** Add task to the scheduler. */
  void scheduleTask(Task task);

  /** Update to execute scheduled tasks. */
  void update(int deltaTime);
}
