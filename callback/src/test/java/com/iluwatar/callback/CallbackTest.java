/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.callback;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Add a field as a counter. Every time the callback method is called increment this field. Unit
 * test checks that the field is being incremented.
 *
 * Could be done with mock objects as well where the call method call is verified.
 */
public class CallbackTest {

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
