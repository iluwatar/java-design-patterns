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
package com.iluwatar.templatemethod;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Date: 12/30/15 - 18:12 PM
 *
 * @author Jeroen Meulemeester
 */
public abstract class StealingMethodTest<M extends StealingMethod> {

  /**
   * The tested stealing method
   */
  private final M method;

  /**
   * The expected target
   */
  private final String expectedTarget;

  /**
   * The expected target picking result
   */
  private final String expectedTargetResult;

  /**
   * The expected confusion method
   */
  private final String expectedConfuseMethod;

  /**
   * The expected stealing method
   */
  private final String expectedStealMethod;

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
   * Create a new test for the given stealing method, together with the expected results
   *
   * @param method                The tested stealing method
   * @param expectedTarget        The expected target name
   * @param expectedTargetResult  The expected target picking result
   * @param expectedConfuseMethod The expected confusion method
   * @param expectedStealMethod   The expected stealing method
   */
  public StealingMethodTest(final M method, String expectedTarget, final String expectedTargetResult,
                            final String expectedConfuseMethod, final String expectedStealMethod) {

    this.method = method;
    this.expectedTarget = expectedTarget;
    this.expectedTargetResult = expectedTargetResult;
    this.expectedConfuseMethod = expectedConfuseMethod;
    this.expectedStealMethod = expectedStealMethod;
  }

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
   * Verify if the thief picks the correct target
   */
  @Test
  public void testPickTarget() {
    assertEquals(expectedTarget, this.method.pickTarget());
  }

  /**
   * Verify if the target confusing step goes as planned
   */
  @Test
  public void testConfuseTarget() {
    verifyZeroInteractions(this.stdOutMock);

    this.method.confuseTarget(this.expectedTarget);
    verify(this.stdOutMock).println(this.expectedConfuseMethod);
    verifyNoMoreInteractions(this.stdOutMock);
  }

  /**
   * Verify if the stealing step goes as planned
   */
  @Test
  public void testStealTheItem() {
    verifyZeroInteractions(this.stdOutMock);

    this.method.stealTheItem(this.expectedTarget);
    verify(this.stdOutMock).println(this.expectedStealMethod);
    verifyNoMoreInteractions(this.stdOutMock);
  }

  /**
   * Verify if the complete steal process goes as planned
   */
  @Test
  public void testSteal() {
    final InOrder inOrder = inOrder(this.stdOutMock);

    this.method.steal();

    inOrder.verify(this.stdOutMock).println(this.expectedTargetResult);
    inOrder.verify(this.stdOutMock).println(this.expectedConfuseMethod);
    inOrder.verify(this.stdOutMock).println(this.expectedStealMethod);
    inOrder.verifyNoMoreInteractions();
  }

}