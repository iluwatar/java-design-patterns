package com.iluwatar.front.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Date: 12/13/15 - 1:39 PM
 *
 * @author Jeroen Meulemeester
 */
@RunWith(Parameterized.class)
public class FrontControllerTest extends StdOutTest {

  @Parameters
  public static List<Object[]> data() {
    final List<Object[]> parameters = new ArrayList<>();
    parameters.add(new Object[]{new ArcherCommand(), "Displaying archers"});
    parameters.add(new Object[]{new CatapultCommand(), "Displaying catapults"});
    parameters.add(new Object[]{new UnknownCommand(), "Error 500"});
    return parameters;
  }

  /**
   * The view that's been tested
   */
  private final Command command;

  /**
   * The expected display message
   */
  private final String displayMessage;

  /**
   * Create a new instance of the {@link FrontControllerTest} with the given view and expected message
   *
   * @param command        The command that's been tested
   * @param displayMessage The expected display message
   */
  public FrontControllerTest(final Command command, final String displayMessage) {
    this.displayMessage = displayMessage;
    this.command = command;
  }

  @Test
  public void testDisplay() {
    verifyZeroInteractions(getStdOutMock());
    this.command.process();
    verify(getStdOutMock()).println(displayMessage);
    verifyNoMoreInteractions(getStdOutMock());
  }

}
