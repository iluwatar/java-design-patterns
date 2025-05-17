package com.iluwatar.onionarchitecture.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TodoItemTest {

  @Test
  public void testConstructor() {
    int expectedId = 1;
    String expectedTitle = "Test Todo";
    boolean expectedCompletionStatus = false;

    TodoItem todoItem = new TodoItem(expectedId, expectedTitle);

    assertEquals(expectedId, todoItem.getId());
    assertEquals(expectedTitle, todoItem.getTitle());
    assertEquals(expectedCompletionStatus, todoItem.isCompleted());
  }

  @Test
  public void testMarkCompleted() {
    TodoItem todoItem = new TodoItem(1, "Test Todo");

    todoItem.markCompleted();

    assertTrue(todoItem.isCompleted()); 
  }

  @Test
  public void testGetters() {
    int expectedId = 1;
    String expectedTitle = "Test Todo";
    TodoItem todoItem = new TodoItem(expectedId, expectedTitle);

    assertEquals(expectedId, todoItem.getId()); 
    assertEquals(expectedTitle, todoItem.getTitle()); 
  }

  @Test
  public void testDefaultIsCompleted() {
    TodoItem todoItem = new TodoItem(1, "Test Todo");

    assertFalse(todoItem.isCompleted()); 
  }
}
