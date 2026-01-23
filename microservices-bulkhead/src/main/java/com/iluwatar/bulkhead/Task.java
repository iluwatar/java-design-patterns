package com.iluwatar.bulkhead;

/**
 * Represents a task to be executed by a bulkhead service.
 *
 * <p>Tasks are characterized by their name, type, and expected duration.
 * This information is useful for logging, monitoring, and demonstrating
 * the bulkhead pattern's behavior under different loads.
 */
public class Task {

  private final String name;
  private final TaskType type;
  private final long durationMs;

  /**
   * Creates a new Task.
   *
   * @param name unique identifier for the task
   * @param type the type of task (user request or background processing)
   * @param durationMs expected duration in milliseconds
   */
  public Task(String name, TaskType type, long durationMs) {
    this.name = name;
    this.type = type;
    this.durationMs = durationMs;
  }

  /**
   * Gets the task name.
   *
   * @return task name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the task type.
   *
   * @return task type
   */
  public TaskType getType() {
    return type;
  }

  /**
   * Gets the expected task duration.
   *
   * @return duration in milliseconds
   */
  public long getDurationMs() {
    return durationMs;
  }

  @Override
  public String toString() {
    return "Task{name='" + name + "', type=" + type + ", duration=" + durationMs + "ms}";
  }
}
