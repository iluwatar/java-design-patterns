package com.iluwatar.reader.writer.lock;

import org.junit.After;
import org.junit.Before;

import java.io.PrintStream;

import static org.mockito.Mockito.mock;

/**
 * Date: 12/10/15 - 8:37 PM
 *
 * @author Jeroen Meulemeester
 */
public abstract class StdOutTest {

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
   * Get the mocked stdOut {@link PrintStream}
   *
   * @return The stdOut print stream mock, renewed before each test
   */
  final PrintStream getStdOutMock() {
    return this.stdOutMock;
  }

}
