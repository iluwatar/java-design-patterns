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
package com.iluwatar.visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
 * Test case for Visitor Pattern
 *
 * @param <V> Type of UnitVisitor
 */
public abstract class VisitorTest<V extends UnitVisitor> {

  private InMemoryAppender appender;

  @BeforeEach
  void setUp() {
    appender = new InMemoryAppender();
  }

  @AfterEach
  void tearDown() {
    appender.stop();
  }

  /**
   * The tested visitor instance.
   */
  private final V visitor;

  /**
   * The expected response when being visited by a commander.
   */
  private final String commanderResponse;

  /**
   * The expected response when being visited by a sergeant.
   */
  private final String sergeantResponse;

  /**
   * The expected response when being visited by a soldier.
   */
  private final String soldierResponse;

  /**
   * Create a new test instance for the given visitor.
   *
   * @param commanderResponse The expected response when being visited by a commander
   * @param sergeantResponse  The expected response when being visited by a sergeant
   * @param soldierResponse   The expected response when being visited by a soldier
   */
  public VisitorTest(
      final V visitor,
      final String commanderResponse,
      final String sergeantResponse,
      final String soldierResponse
  ) {
    this.visitor = visitor;
    this.commanderResponse = commanderResponse;
    this.sergeantResponse = sergeantResponse;
    this.soldierResponse = soldierResponse;
  }

  @Test
  void testVisitCommander() {
    this.visitor.visit(new Commander());
    if (this.commanderResponse != null) {
      assertEquals(this.commanderResponse, appender.getLastMessage());
      assertEquals(1, appender.getLogSize());
    }
  }

  @Test
  void testVisitSergeant() {
    this.visitor.visit(new Sergeant());
    if (this.sergeantResponse != null) {
      assertEquals(this.sergeantResponse, appender.getLastMessage());
      assertEquals(1, appender.getLogSize());
    }
  }

  @Test
  void testVisitSoldier() {
    this.visitor.visit(new Soldier());
    if (this.soldierResponse != null) {
      assertEquals(this.soldierResponse, appender.getLastMessage());
      assertEquals(1, appender.getLogSize());
    }
  }

  private static class InMemoryAppender extends AppenderBase<ILoggingEvent> {
    private final List<ILoggingEvent> log = new LinkedList<>();

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
