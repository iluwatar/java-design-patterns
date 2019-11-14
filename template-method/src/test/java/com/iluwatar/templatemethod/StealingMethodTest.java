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

package com.iluwatar.templatemethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
 * Date: 12/30/15 - 18:12 PM
 *
 * @param <M> Type of StealingMethod
 * @author Jeroen Meulemeester
 */
public abstract class StealingMethodTest<M extends StealingMethod> {

  private InMemoryAppender appender;

  @BeforeEach
  public void setUp() {
    appender = new InMemoryAppender();
  }

  @AfterEach
  public void tearDown() {
    appender.stop();
  }

  /**
   * The tested stealing method
   */
  private final M method;

  /**
   * The expected target
   */
  private final String expectedTarget;

  /**
   * The expected target picking result
   */
  private final String expectedTargetResult;

  /**
   * The expected confusion method
   */
  private final String expectedConfuseMethod;

  /**
   * The expected stealing method
   */
  private final String expectedStealMethod;

  /**
   * Create a new test for the given stealing method, together with the expected results
   *
   * @param method                The tested stealing method
   * @param expectedTarget        The expected target name
   * @param expectedTargetResult  The expected target picking result
   * @param expectedConfuseMethod The expected confusion method
   * @param expectedStealMethod   The expected stealing method
   */
  public StealingMethodTest(final M method, String expectedTarget, final String expectedTargetResult,
                            final String expectedConfuseMethod, final String expectedStealMethod) {

    this.method = method;
    this.expectedTarget = expectedTarget;
    this.expectedTargetResult = expectedTargetResult;
    this.expectedConfuseMethod = expectedConfuseMethod;
    this.expectedStealMethod = expectedStealMethod;
  }

  /**
   * Verify if the thief picks the correct target
   */
  @Test
  public void testPickTarget() {
    assertEquals(expectedTarget, this.method.pickTarget());
  }

  /**
   * Verify if the target confusing step goes as planned
   */
  @Test
  public void testConfuseTarget() {
    assertEquals(0, appender.getLogSize());

    this.method.confuseTarget(this.expectedTarget);
    assertEquals(this.expectedConfuseMethod, appender.getLastMessage());
    assertEquals(1, appender.getLogSize());
  }

  /**
   * Verify if the stealing step goes as planned
   */
  @Test
  public void testStealTheItem() {
    assertEquals(0, appender.getLogSize());

    this.method.stealTheItem(this.expectedTarget);
    assertEquals(this.expectedStealMethod, appender.getLastMessage());
    assertEquals(1, appender.getLogSize());
  }

  /**
   * Verify if the complete steal process goes as planned
   */
  @Test
  public void testSteal() {
    this.method.steal();

    assertTrue(appender.logContains(this.expectedTargetResult));
    assertTrue(appender.logContains(this.expectedConfuseMethod));
    assertTrue(appender.logContains(this.expectedStealMethod));
    assertEquals(3, appender.getLogSize());
  }

  private class InMemoryAppender extends AppenderBase<ILoggingEvent> {
    private List<ILoggingEvent> log = new LinkedList<>();

    public InMemoryAppender() {
      ((Logger) LoggerFactory.getLogger("root")).addAppender(this);
      start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
      log.add(eventObject);
    }

    public int getLogSize() {
      return log.size();
    }

    public String getLastMessage() {
      return log.get(log.size() - 1).getFormattedMessage();
    }

    public boolean logContains(String message) {
      return log.stream().anyMatch(event -> event.getFormattedMessage().equals(message));
    }
  }
}
