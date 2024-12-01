package com.iluwatar.component;

public class TaskManager {

  // Method to create a task
  public Task createTask(String title) {
    return TaskFactory.createFirstTask(title);
  }

  // Method to create a task list
  public TaskList createTaskList(String title) {
    return TaskFactory.createTaskList(title);
  }

  // Method to add a task to a task list
  public void addTaskToList(TaskList taskList, Task task) {
    taskList.addTask(task);
  }

  // Method to remove a task from a task list
  public void removeTaskFromList(TaskList taskList, Task task) {
    taskList.removeTask(task);
  }

  // Method to display a single task
  public void displayTask(Task task) {
    task.display();
  }

  // Method to display a task list and its tasks
  public void displayTaskList(TaskList taskList) {
    taskList.display();
  }
}


