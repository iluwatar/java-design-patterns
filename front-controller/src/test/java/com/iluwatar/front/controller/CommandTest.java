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
public class CommandTest extends StdOutTest {

  @Parameters
  public static List<Object[]> data() {
    final List<Object[]> parameters = new ArrayList<>();
    parameters.add(new Object[]{"Archer", "Displaying archers"});
    parameters.add(new Object[]{"Catapult", "Displaying catapults"});
    parameters.add(new Object[]{"NonExistentCommand", "Error 500"});
    return parameters;
  }

  /**
   * The view that's been tested
   */
  private final String request;

  /**
   * The expected display message
   */
  private final String displayMessage;

  /**
   * Create a new instance of the {@link CommandTest} with the given view and expected message
   *
   * @param request        The request that's been tested
   * @param displayMessage The expected display message
   */
  public CommandTest(final String request, final String displayMessage) {
    this.displayMessage = displayMessage;
    this.request = request;
  }

  @Test
  public void testDisplay() {
    final FrontController frontController = new FrontController();
    verifyZeroInteractions(getStdOutMock());
    frontController.handleRequest(request);
    verify(getStdOutMock()).println(displayMessage);
    verifyNoMoreInteractions(getStdOutMock());
  }

}
