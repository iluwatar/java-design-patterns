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
package com.iluwatar.strategy;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Date: 12/29/15 - 10:58 PM
 *
 * @author Jeroen Meulemeester
 */
@RunWith(Parameterized.class)
public class DragonSlayingStrategyTest {

  /**
   * @return The test parameters for each cycle
   */
  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[]{
            new MeleeStrategy(),
            "With your Excalibur you sever the dragon's head!"
        },
        new Object[]{
            new ProjectileStrategy(),
            "You shoot the dragon with the magical crossbow and it falls dead on the ground!"
        },
        new Object[]{
            new SpellStrategy(),
            "You cast the spell of disintegration and the dragon vaporizes in a pile of dust!"
        }
    );
  }

  /**
   * The tested strategy
   */
  private final DragonSlayingStrategy strategy;

  /**
   * The expected action in the log
   */
  private final String expectedResult;

  private InMemoryAppender appender;

  @Before
  public void setUp() {
    appender = new InMemoryAppender();
  }

  @After
  public void tearDown() {
    appender.stop();
  }


  /**
   * Create a new test instance for the given strategy
   *
   * @param strategy       The tested strategy
   * @param expectedResult The expected result
   */
  public DragonSlayingStrategyTest(final DragonSlayingStrategy strategy, final String expectedResult) {
    this.strategy = strategy;
    this.expectedResult = expectedResult;
  }

  /**
   * Test if executing the strategy gives the correct response
   */
  @Test
  public void testExecute() {
    this.strategy.execute();
    assertEquals(this.expectedResult, appender.getLastMessage());
    assertEquals(1, appender.getLogSize());
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
  }
}
