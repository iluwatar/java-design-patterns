package com.iluwatar.component;

public class TaskFactory {

  // Creates and returns a simple task
  public static Task createFirstTask(String title) {
    return new FirstTask(title);
  }

  // Creates and returns a task list
  public static TaskList createTaskList(String title) {
    return new TaskList(title);
  }
}
