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
package com.iluwatar.twin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.slf4j.LoggerFactory;

/**
 * Date: 12/30/15 - 18:44 PM
 *
 * @author Jeroen Meulemeester
 */
public class BallItemTest {

  private InMemoryAppender appender;

  @Before
  public void setUp() {
    appender = new InMemoryAppender();
  }

  @After
  public void tearDown() {
    appender.stop();
  }

  @Test
  public void testClick() {
    final BallThread ballThread = mock(BallThread.class);
    final BallItem ballItem = new BallItem();
    ballItem.setTwin(ballThread);

    final InOrder inOrder = inOrder(ballThread);

    for (int i = 0; i < 10; i++) {
      ballItem.click();
      inOrder.verify(ballThread).suspendMe();

      ballItem.click();
      inOrder.verify(ballThread).resumeMe();
    }

    inOrder.verifyNoMoreInteractions();
  }

  @Test
  public void testDoDraw() {
    final BallItem ballItem = new BallItem();
    final BallThread ballThread = mock(BallThread.class);
    ballItem.setTwin(ballThread);

    ballItem.draw();
    assertTrue(appender.logContains("draw"));
    assertTrue(appender.logContains("doDraw"));

    verifyNoMoreInteractions(ballThread);
    assertEquals(2, appender.getLogSize());
  }

  @Test
  public void testMove() {
    final BallItem ballItem = new BallItem();
    final BallThread ballThread = mock(BallThread.class);
    ballItem.setTwin(ballThread);

    ballItem.move();
    assertTrue(appender.logContains("move"));

    verifyNoMoreInteractions(ballThread);
    assertEquals(1, appender.getLogSize());
  }

  /**
   * Logging Appender Implementation
   */
  public class InMemoryAppender extends AppenderBase<ILoggingEvent> {
    private List<ILoggingEvent> log = new LinkedList<>();

    public InMemoryAppender() {
      ((Logger) LoggerFactory.getLogger("root")).addAppender(this);
      start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
      log.add(eventObject);
    }

    public boolean logContains(String message) {
      return log.stream().anyMatch(event -> event.getMessage().equals(message));
    }

    public int getLogSize() {
      return log.size();
    }
  }

}
