package com.iluwatar.proactor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsynchronousOperationTest {
  @Test
  public void testTask1() throws Exception {
    AsynchronousOperation op = new AsynchronousOperation();
    assertEquals("short", op.execute("task1").handleName);
  }

  @Test
  public void testTask2() throws Exception {
    AsynchronousOperation op = new AsynchronousOperation();
    assertEquals("long", op.execute("task2").handleName);
  }
}
