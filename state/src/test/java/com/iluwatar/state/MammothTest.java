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