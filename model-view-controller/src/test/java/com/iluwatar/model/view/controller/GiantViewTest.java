/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.model.view.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

/**
 * Date: 12/20/15 - 2:04 PM
 *
 * @author Jeroen Meulemeester
 */
public class GiantViewTest {

  private InMemoryAppender appender;

  @BeforeEach
  public void setUp() {
    appender = new InMemoryAppender(GiantView.class);
  }

  @AfterEach
  public void tearDown() {
    appender.stop();
  }

  /**
   * Verify if the {@link GiantView} does what it has to do: Print the {@link GiantModel} to the
   * standard out stream, nothing more, nothing less.
   */
  @Test
  public void testDisplayGiant() {
    final GiantView view = new GiantView();

    final GiantModel model = mock(GiantModel.class);
    view.displayGiant(model);

    assertEquals(model.toString(), appender.getLastMessage());
    assertEquals(1, appender.getLogSize());
  }

  /**
   * Logging Appender Implementation
   */
  public class InMemoryAppender extends AppenderBase<ILoggingEvent> {
    private List<ILoggingEvent> log = new LinkedList<>();

    public InMemoryAppender(Class clazz) {
      ((Logger) LoggerFactory.getLogger(clazz)).addAppender(this);
      start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
      log.add(eventObject);
    }

    public String getLastMessage() {
      return log.get(log.size() - 1).getMessage();
    }

    public int getLogSize() {
      return log.size();
    }
  }
}
