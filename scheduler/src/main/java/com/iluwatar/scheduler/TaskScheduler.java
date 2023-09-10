package com.iluwatar.scheduler;


public interface TaskScheduler {
  /** Add task to the scheduler */
  void scheduleTask(Task task);

  /** Update to execute scheduled tasks */
  void update(int deltaTime);
}
