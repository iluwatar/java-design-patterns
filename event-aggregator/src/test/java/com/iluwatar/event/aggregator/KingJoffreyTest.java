package com.iluwatar.event.aggregator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Date: 12/12/15 - 3:04 PM
 *
 * @author Jeroen Meulemeester
 */
public class KingJoffreyTest {

  /**
   * The mocked standard out {@link PrintStream}, required since {@link KingJoffrey} does nothing
   * except for writing to std-out using {@link System#out}
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
   * Test if {@link KingJoffrey} tells us what event he received
   */
  @Test
  public void testOnEvent() {
    final KingJoffrey kingJoffrey = new KingJoffrey();

    for (final Event event : Event.values()) {
      verifyZeroInteractions(this.stdOutMock);
      kingJoffrey.onEvent(event);

      final String expectedMessage = "Received event from the King's Hand: " + event.toString();
      verify(this.stdOutMock, times(1)).println(expectedMessage);
      verifyNoMoreInteractions(this.stdOutMock);
    }

  }

}