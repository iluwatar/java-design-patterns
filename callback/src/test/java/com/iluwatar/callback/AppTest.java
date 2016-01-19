package com.iluwatar.callback;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Add a field as a counter. Every time the callback method is called increment this field. Unit
 * test checks that the field is being incremented.
 *
 * Could be done with mock objects as well where the call method call is verified.
 */
public class AppTest {

  private Integer callingCount = 0;

  @Test
  public void test() {
    Callback callback = new Callback() {
      @Override
      public void call() {
        callingCount++;
      }
    };

    Task task = new SimpleTask();

    assertEquals("Initial calling count of 0", new Integer(0), callingCount);

    task.executeWith(callback);

    assertEquals("Callback called once", new Integer(1), callingCount);

    task.executeWith(callback);

    assertEquals("Callback called twice", new Integer(2), callingCount);

  }

  @Test
  public void testWithLambdasExample() {
    Callback callback = () -> callingCount++;

    Task task = new SimpleTask();

    assertEquals("Initial calling count of 0", new Integer(0), callingCount);

    task.executeWith(callback);

    assertEquals("Callback called once", new Integer(1), callingCount);

    task.executeWith(callback);

    assertEquals("Callback called twice", new Integer(2), callingCount);

  }
}
