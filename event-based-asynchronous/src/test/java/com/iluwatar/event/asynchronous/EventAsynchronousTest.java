/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.event.asynchronous;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application test
 */
class EventAsynchronousTest {
  private static final Logger LOGGER = LoggerFactory.getLogger(EventAsynchronousTest.class);

  @Test
  void testAsynchronousEvent() {
    var eventManager = new EventManager();
    try {
      var aEventId = eventManager.createAsync(60);
      eventManager.start(aEventId);
      assertEquals(1, eventManager.getEventPool().size());
      assertTrue(eventManager.getEventPool().size() < EventManager.MAX_RUNNING_EVENTS);
      assertEquals(-1, eventManager.numOfCurrentlyRunningSyncEvent());
      eventManager.cancel(aEventId);
      assertTrue(eventManager.getEventPool().isEmpty());
    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException e) {
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  void testSynchronousEvent() {
    var eventManager = new EventManager();
    try {
      var sEventId = eventManager.create(60);
      eventManager.start(sEventId);
      assertEquals(1, eventManager.getEventPool().size());
      assertTrue(eventManager.getEventPool().size() < EventManager.MAX_RUNNING_EVENTS);
      assertNotEquals(-1, eventManager.numOfCurrentlyRunningSyncEvent());
      eventManager.cancel(sEventId);
      assertTrue(eventManager.getEventPool().isEmpty());
    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException
        | InvalidOperationException e) {
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  void testUnsuccessfulSynchronousEvent() {
    assertThrows(InvalidOperationException.class, () -> {
      var eventManager = new EventManager();
      try {
        var sEventId = eventManager.create(60);
        eventManager.start(sEventId);
        sEventId = eventManager.create(60);
        eventManager.start(sEventId);
      } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException e) {
        LOGGER.error(e.getMessage());
      }
    });
  }

  @Test
  void testFullSynchronousEvent() {
    var eventManager = new EventManager();
    try {
      var eventTime = 1;

      var sEventId = eventManager.create(eventTime);
      assertEquals(1, eventManager.getEventPool().size());
      eventManager.start(sEventId);

      var currentTime = System.currentTimeMillis();
      // +2 to give a bit of buffer time for event to complete properly.
      var endTime = currentTime + (eventTime + 2 * 1000);

      long sleepTime = endTime - System.currentTimeMillis();
      if (sleepTime > 0) {
        try {
          Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
          LOGGER.error("Thread interrupted: ", e);
          Thread.currentThread().interrupt();
        }
      }

      assertTrue(eventManager.getEventPool().isEmpty());

    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException
        | InvalidOperationException e) {
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  void testFullAsynchronousEvent() {
    var eventManager = new EventManager();
    try {
      var eventTime = 1;

      var aEventId1 = eventManager.createAsync(eventTime);
      var aEventId2 = eventManager.createAsync(eventTime);
      var aEventId3 = eventManager.createAsync(eventTime);
      assertEquals(3, eventManager.getEventPool().size());

      eventManager.start(aEventId1);
      eventManager.start(aEventId2);
      eventManager.start(aEventId3);

      var currentTime = System.currentTimeMillis();
      // +2 to give a bit of buffer time for event to complete properly.
      var endTime = currentTime + (eventTime + 2 * 1000);

      long sleepTime = endTime - System.currentTimeMillis();
      if (sleepTime > 0) {
        try {
          Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
          LOGGER.error("Thread interrupted: ", e);
          Thread.currentThread().interrupt();
        }
      }

      assertTrue(eventManager.getEventPool().isEmpty());

    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException e) {
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  void testLongRunningEventException(){
    assertThrows(LongRunningEventException.class, () -> {
      var eventManager = new EventManager();
      eventManager.createAsync(2000);
    });
  }


  @Test
  void testMaxNumOfEventsAllowedException(){
    assertThrows(MaxNumOfEventsAllowedException.class, () -> {
      final var eventManager = new EventManager();
      for(int i=0;i<1100;i++){
        eventManager.createAsync(i);
      }
    });
  }



}