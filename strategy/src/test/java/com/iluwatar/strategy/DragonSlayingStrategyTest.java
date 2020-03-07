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

package com.iluwatar.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.LoggerFactory;

/**
 * Date: 12/29/15 - 10:58 PM.
 *
 * @author Jeroen Meulemeester
 */
public class DragonSlayingStrategyTest {

  /**
   * Assembles test parameters.
   *
   * @return The test parameters for each cycle
   */
  static Collection<Object[]> dataProvider() {
    return List.of(
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
   * Test if executing the strategy gives the correct response.
   */
  @ParameterizedTest
  @MethodSource("dataProvider")
  public void testExecute(DragonSlayingStrategy strategy, String expectedResult) {
    strategy.execute();
    assertEquals(expectedResult, appender.getLastMessage());
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
