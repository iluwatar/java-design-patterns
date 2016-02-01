/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.state;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Date: 12/29/15 - 8:27 PM
 *
 * @author Jeroen Meulemeester
 */
public class MammothTest {

  /**
   * The mocked standard out {@link PrintStream}, required since some actions don't have any
   * influence on accessible objects, except for writing to std-out using {@link System#out}
   */
  private final PrintStream stdOutMock = mock(PrintStream.class);

  /**
   * Keep the original std-out so it can be restored after the test
   */
  private final PrintStream stdOutOrig = System.out;

  /**
   * Inject the mocked std-out {@link PrintStream} into the {@link System} class before each test
   */
  @Before
  public void setUp() {
    System.setOut(this.stdOutMock);
  }

  /**
   * Removed the mocked std-out {@link PrintStream} again from the {@link System} class
   */
  @After
  public void tearDown() {
    System.setOut(this.stdOutOrig);
  }

  /**
   * Switch to a complete mammoth 'mood'-cycle and verify if the observed mood matches the expected
   * value.
   */
  @Test
  public void testTimePasses() {
    final InOrder inOrder = Mockito.inOrder(this.stdOutMock);
    final Mammoth mammoth = new Mammoth();

    mammoth.observe();
    inOrder.verify(this.stdOutMock).println("The mammoth is calm and peaceful.");
    inOrder.verifyNoMoreInteractions();

    mammoth.timePasses();
    inOrder.verify(this.stdOutMock).println("The mammoth gets angry!");
    inOrder.verifyNoMoreInteractions();

    mammoth.observe();
    inOrder.verify(this.stdOutMock).println("The mammoth is furious!");
    inOrder.verifyNoMoreInteractions();

    mammoth.timePasses();
    inOrder.verify(this.stdOutMock).println("The mammoth calms down.");
    inOrder.verifyNoMoreInteractions();

    mammoth.observe();
    inOrder.verify(this.stdOutMock).println("The mammoth is calm and peaceful.");
    inOrder.verifyNoMoreInteractions();

  }

  /**
   * Verify if {@link Mammoth#toString()} gives the expected value
   */
  @Test
  public void testToString() {
    final String toString = new Mammoth().toString();
    assertNotNull(toString);
    assertEquals("The mammoth", toString);
  }

}