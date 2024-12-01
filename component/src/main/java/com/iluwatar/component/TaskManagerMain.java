package com.iluwatar.component;
public class TaskManagerMain {
  public static void main(String[] args) {
    // Create an instance of TaskManager
    TaskManager taskManager = new TaskManager();

    // Create two simple tasks
    Task task1 = taskManager.createTask("Study for the quiz");
    Task task2 = taskManager.createTask("Complete homework");

    // Create a task list to hold the tasks
    TaskList taskList = taskManager.createTaskList("My Project Tasks");

    // Add tasks to the task list
    taskManager.addTaskToList(taskList, task1);
    taskManager.addTaskToList(taskList, task2);

    // Display individual tasks
    System.out.println("Displaying Task 1:");
    taskManager.displayTask(task1);

    System.out.println("\nDisplaying Task 2:");
    taskManager.displayTask(task2);

    // Display the task list
    System.out.println("\nDisplaying Task List:");
    taskManager.displayTaskList(taskList);
  }
}





