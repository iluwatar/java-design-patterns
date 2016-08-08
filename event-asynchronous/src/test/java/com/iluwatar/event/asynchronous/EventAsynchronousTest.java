/**
 * The MIT License Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.event.asynchronous;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * Application test
 *
 */
public class EventAsynchronousTest {
  App app;

  @Before
  public void setUp() {
    app = new App();
  }

  @Test
  public void testAsynchronousEvent() {
    EventManager eventManager = new EventManager();
    try {
      int aEventId = eventManager.createAsyncEvent(60);
      eventManager.startEvent(aEventId);
      eventManager.stopEvent(aEventId);
    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testSynchronousEvent() {
    EventManager eventManager = new EventManager();
    try {
      int sEventId = eventManager.createSyncEvent(60);
      eventManager.startEvent(sEventId);
      eventManager.stopEvent(sEventId);
    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException
        | InvalidOperationException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testUnsuccessfulSynchronousEvent() {
    EventManager eventManager = new EventManager();
    try {
      int sEventId = eventManager.createSyncEvent(60);
      eventManager.startEvent(sEventId);
      sEventId = eventManager.createSyncEvent(60);
      eventManager.startEvent(sEventId);
    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException
        | InvalidOperationException e) {
      System.out.println(e.getMessage());
    }
  }
}
