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
package com.iluwatar.front.controller;

import com.iluwatar.front.controller.utils.InMemoryAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Date: 12/13/15 - 1:39 PM
 *
 * @author Jeroen Meulemeester
 */
@RunWith(Parameterized.class)
public class CommandTest {

  private InMemoryAppender appender;

  @Before
  public void setUp() {
    appender = new InMemoryAppender();
  }

  @After
  public void tearDown() {
    appender.stop();
  }

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
    assertEquals(0, appender.getLogSize());
    frontController.handleRequest(request);
    assertEquals(displayMessage, appender.getLastMessage());
    assertEquals(1, appender.getLogSize());
  }

}
