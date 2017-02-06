/**
 * The MIT License Copyright (c) 2014-2016 Ilkka Seppälä
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

/**
 *
 * Application test
 *
 */
public class EventAsynchronousTest {
  App app;

  private static final Logger LOGGER = LoggerFactory.getLogger(EventAsynchronousTest.class);

  @Before
  public void setUp() {
    app = new App();
  }

  @Test
  public void testAsynchronousEvent() {
    EventManager eventManager = new EventManager();
    try {
      int aEventId = eventManager.createAsync(60);
      eventManager.start(aEventId);
      assertTrue(eventManager.getEventPool().size() == 1);
      assertTrue(eventManager.getEventPool().size() < EventManager.MAX_RUNNING_EVENTS);
      assertTrue(eventManager.numOfCurrentlyRunningSyncEvent() == -1);
      eventManager.cancel(aEventId);
      assertTrue(eventManager.getEventPool().size() == 0);
    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException e) {
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void testSynchronousEvent() {
    EventManager eventManager = new EventManager();
    try {
      int sEventId = eventManager.create(60);
      eventManager.start(sEventId);
      assertTrue(eventManager.getEventPool().size() == 1);
      assertTrue(eventManager.getEventPool().size() < EventManager.MAX_RUNNING_EVENTS);
      assertTrue(eventManager.numOfCurrentlyRunningSyncEvent() != -1);
      eventManager.cancel(sEventId);
      assertTrue(eventManager.getEventPool().size() == 0);
    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException
        | InvalidOperationException e) {
      LOGGER.error(e.getMessage());
    }
  }

  @Test(expected = InvalidOperationException.class)
  public void testUnsuccessfulSynchronousEvent() throws InvalidOperationException {
    EventManager eventManager = new EventManager();
    try {
      int sEventId = eventManager.create(60);
      eventManager.start(sEventId);
      sEventId = eventManager.create(60);
      eventManager.start(sEventId);
    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException e) {
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void testFullSynchronousEvent() {
    EventManager eventManager = new EventManager();
    try {
      int eventTime = 1;

      int sEventId = eventManager.create(eventTime);
      assertTrue(eventManager.getEventPool().size() == 1);
      eventManager.start(sEventId);

      long currentTime = System.currentTimeMillis();
      long endTime = currentTime + (eventTime + 2 * 1000); // +2 to give a bit of buffer time for event to
                                                           // complete
      // properly.
      while (System.currentTimeMillis() < endTime) {
      }

      assertTrue(eventManager.getEventPool().size() == 0);

    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException
        | InvalidOperationException e) {
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void testFullAsynchronousEvent() {
    EventManager eventManager = new EventManager();
    try {
      int eventTime = 1;

      int aEventId1 = eventManager.createAsync(eventTime);
      int aEventId2 = eventManager.createAsync(eventTime);
      int aEventId3 = eventManager.createAsync(eventTime);
      assertTrue(eventManager.getEventPool().size() == 3);

      eventManager.start(aEventId1);
      eventManager.start(aEventId2);
      eventManager.start(aEventId3);

      long currentTime = System.currentTimeMillis();
      long endTime = currentTime + (eventTime + 2 * 1000); // +2 to give a bit of buffer time for event to complete
                                                           // properly.
      while (System.currentTimeMillis() < endTime) {
      }

      assertTrue(eventManager.getEventPool().size() == 0);

    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
