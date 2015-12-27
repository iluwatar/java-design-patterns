package com.iluwatar.facade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Date: 12/9/15 - 9:40 PM
 *
 * @author Jeroen Meulemeester
 */
public class DwarvenGoldmineFacadeTest {

  /**
   * The mocked standard out {@link PrintStream}, required since the actions on the gold mine facade
   * don't have any influence on any other accessible objects, except for writing to std-out using
   * {@link System#out}
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
   * Test a complete day cycle in the gold mine by executing all three different steps: {@link
   * DwarvenGoldmineFacade#startNewDay()}, {@link DwarvenGoldmineFacade#digOutGold()} and {@link
   * DwarvenGoldmineFacade#endDay()}.
   *
   * See if the workers are doing what's expected from them on each step.
   */
  @Test
  public void testFullWorkDay() {
    final DwarvenGoldmineFacade goldMine = new DwarvenGoldmineFacade();
    goldMine.startNewDay();

    // On the start of a day, all workers should wake up ...
    verify(this.stdOutMock, times(1)).println(eq("Dwarf gold digger wakes up."));
    verify(this.stdOutMock, times(1)).println(eq("Dwarf cart operator wakes up."));
    verify(this.stdOutMock, times(1)).println(eq("Dwarven tunnel digger wakes up."));

    // ... and go to the mine
    verify(this.stdOutMock, times(1)).println(eq("Dwarf gold digger goes to the mine."));
    verify(this.stdOutMock, times(1)).println(eq("Dwarf cart operator goes to the mine."));
    verify(this.stdOutMock, times(1)).println(eq("Dwarven tunnel digger goes to the mine."));

    // No other actions were invoked, so the workers shouldn't have done (printed) anything else
    verifyNoMoreInteractions(this.stdOutMock);

    // Now do some actual work, start digging gold!
    goldMine.digOutGold();

    // Since we gave the dig command, every worker should be doing it's job ...
    verify(this.stdOutMock, times(1)).println(eq("Dwarf gold digger digs for gold."));
    verify(this.stdOutMock, times(1)).println(eq("Dwarf cart operator moves gold chunks out of the mine."));
    verify(this.stdOutMock, times(1)).println(eq("Dwarven tunnel digger creates another promising tunnel."));

    // Again, they shouldn't be doing anything else.
    verifyNoMoreInteractions(this.stdOutMock);

    // Enough gold, lets end the day.
    goldMine.endDay();

    // Check if the workers go home ...
    verify(this.stdOutMock, times(1)).println(eq("Dwarf gold digger goes home."));
    verify(this.stdOutMock, times(1)).println(eq("Dwarf cart operator goes home."));
    verify(this.stdOutMock, times(1)).println(eq("Dwarven tunnel digger goes home."));

    // ... and go to sleep. We need well rested workers the next day :)
    verify(this.stdOutMock, times(1)).println(eq("Dwarf gold digger goes to sleep."));
    verify(this.stdOutMock, times(1)).println(eq("Dwarf cart operator goes to sleep."));
    verify(this.stdOutMock, times(1)).println(eq("Dwarven tunnel digger goes to sleep."));

    // Every worker should be sleeping now, no other actions allowed
    verifyNoMoreInteractions(this.stdOutMock);
  }

}
