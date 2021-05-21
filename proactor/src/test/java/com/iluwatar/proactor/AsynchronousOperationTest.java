package com.iluwatar.proactor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AsynchronousOperationTest {
  @Test
  void testTask1() throws Exception {
    AsynchronousOperation op = new AsynchronousOperation();
    assertEquals("short", op.execute("task1").handleName);
  }

  @Test
  void testTask2() throws Exception {
    AsynchronousOperation op = new AsynchronousOperation();
    assertEquals("long", op.execute("task2").handleName);
  }
}
