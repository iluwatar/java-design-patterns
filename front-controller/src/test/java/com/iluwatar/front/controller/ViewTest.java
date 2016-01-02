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
public class ViewTest extends StdOutTest {

  @Parameters
  public static List<Object[]> data() {
    final List<Object[]> parameters = new ArrayList<>();
    parameters.add(new Object[]{new ArcherView(), "Displaying archers"});
    parameters.add(new Object[]{new CatapultView(), "Displaying catapults"});
    parameters.add(new Object[]{new ErrorView(), "Error 500"});
    return parameters;
  }

  /**
   * The view that's been tested
   */
  private final View view;

  /**
   * The expected display message
   */
  private final String displayMessage;

  /**
   * Create a new instance of the {@link ViewTest} with the given view and expected message
   *
   * @param view           The view that's been tested
   * @param displayMessage The expected display message
   */
  public ViewTest(final View view, final String displayMessage) {
    this.displayMessage = displayMessage;
    this.view = view;
  }

  @Test
  public void testDisplay() {
    verifyZeroInteractions(getStdOutMock());
    this.view.display();
    verify(getStdOutMock()).println(displayMessage);
    verifyNoMoreInteractions(getStdOutMock());
  }

}