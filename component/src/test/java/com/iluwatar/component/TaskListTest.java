package com.iluwatar.component;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TaskListTest
{

  @Test
  public void testCreateFirstTask()
  {
    // Test creating the first task.
    Task task = new FirstTask("Study for the exam");
    assertNotNull(task, "Task should not be exit");
    assertEquals("Study for the exam", task.getTitle(), "Task title should match");
  }

  @Test
  public void testSetTaskTitle()
  {
    // Test updating the title for the implemented task
    Task task = new FirstTask("Old Title");
    task.setTitle("New Title");
    assertEquals("New Title", task.getTitle(), "Task title should update correctly");
  }

  @Test
  public void testAddTaskToTaskList()
  {
    // Test adding tasks to a task list
    TaskList taskList = new TaskList("My Task List");
    Task task = new FirstTask("Complete homework");
    taskList.addTask(task);

    assertEquals(1, taskList.getTasks().size(), "Task list should contain one task");
    assertEquals("Complete homework", taskList.getTasks().get(0).getTitle(), "Task title should match");
  }

  @Test
  public void testRemoveTaskFromTaskList()
  {

    TaskList taskList = new TaskList("My Task List");
    Task task = new FirstTask("Complete homework");
    taskList.addTask(task);
    taskList.removeTask(task);

    assertEquals(0, taskList.getTasks().size(), "Task list should be empty after removing the task");
  }

  @Test
  public void testDisplayTaskList()
  {
    // Test displaying the task list and tasks
    TaskList taskList = new TaskList("My Task List");
    Task task1 = new FirstTask("Complete Task");
    Task task2 = new FirstTask("Study for the quiz");

    taskList.addTask(task1);
    taskList.addTask(task2);

    // Test that the tasks are displayed (this doesn't assert, just for practice)
    System.out.println("Testing display:");
    taskList.display();
  }
}
