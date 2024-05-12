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

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import java.time.Duration;

/**
 * Application test
 */
class EventAsynchronousTest {

  @Test
  @SneakyThrows
  void testAsynchronousEvent() {
    var eventManager = new EventManager();
    var aEventId = eventManager.createAsync(Duration.ofSeconds(60));

    assertDoesNotThrow(() ->eventManager.start(aEventId));

    assertEquals(1, eventManager.getEventPool().size());
    assertTrue(eventManager.getEventPool().size() < EventManager.MAX_RUNNING_EVENTS);
    assertEquals(-1, eventManager.numOfCurrentlyRunningSyncEvent());

    assertDoesNotThrow(() -> eventManager.cancel(aEventId));
    assertTrue(eventManager.getEventPool().isEmpty());

  }

  @Test
  @SneakyThrows
  void testSynchronousEvent() {
    var eventManager = new EventManager();
    var sEventId = eventManager.create(Duration.ofSeconds(60));

    assertDoesNotThrow(() -> eventManager.start(sEventId));
    assertEquals(1, eventManager.getEventPool().size());
    assertTrue(eventManager.getEventPool().size() < EventManager.MAX_RUNNING_EVENTS);
    assertNotEquals(-1, eventManager.numOfCurrentlyRunningSyncEvent());

    assertDoesNotThrow(() -> eventManager.cancel(sEventId));
    assertTrue(eventManager.getEventPool().isEmpty());

  }

  @Test
  @SneakyThrows
  void testFullSynchronousEvent() {
    var eventManager = new EventManager();

    var eventTime = Duration.ofSeconds(1);

    var sEventId = eventManager.create(eventTime);
    assertEquals(1, eventManager.getEventPool().size());

    eventManager.start(sEventId);

    await().until(() -> eventManager.getEventPool().isEmpty());

  }

  @Test
  @SneakyThrows
  void testUnsuccessfulSynchronousEvent() {
    assertThrows(InvalidOperationException.class, () -> {
      var eventManager = new EventManager();

      var sEventId = assertDoesNotThrow(() -> eventManager.create(Duration.ofSeconds(60)));
      eventManager.start(sEventId);
      sEventId = eventManager.create(Duration.ofSeconds(60));
      eventManager.start(sEventId);

    });
  }

  @Test
  @SneakyThrows
  void testFullAsynchronousEvent() {
    var eventManager = new EventManager();
    var eventTime = Duration.ofSeconds(1);

    var aEventId1 = assertDoesNotThrow(() -> eventManager.createAsync(eventTime));
    var aEventId2 = assertDoesNotThrow(() -> eventManager.createAsync(eventTime));
    var aEventId3 = assertDoesNotThrow(() -> eventManager.createAsync(eventTime));
    assertEquals(3, eventManager.getEventPool().size());

    eventManager.start(aEventId1);
    eventManager.start(aEventId2);
    eventManager.start(aEventId3);

    await().until(() -> eventManager.getEventPool().isEmpty());

  }

  @Test
  void testLongRunningEventException(){
    assertThrows(LongRunningEventException.class, () -> {
      var eventManager = new EventManager();
      eventManager.createAsync(Duration.ofMinutes(31));
    });
  }


  @Test
  void testMaxNumOfEventsAllowedException(){
    assertThrows(MaxNumOfEventsAllowedException.class, () -> {
      final var eventManager = new EventManager();
      for(int i=0;i<1100;i++){
        eventManager.createAsync(Duration.ofSeconds(i));
      }
    });
  }



}