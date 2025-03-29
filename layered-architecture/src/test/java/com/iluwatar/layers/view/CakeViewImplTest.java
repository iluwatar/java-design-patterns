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

package com.iluwatar.layers.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import dto.CakeInfo;
import dto.CakeLayerInfo;
import dto.CakeToppingInfo;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import service.CakeBakingService;
import view.CakeViewImpl;

/**
 * This class contains unit tests for the CakeViewImpl class. It tests the functionality of
 * rendering cakes using the CakeViewImpl class. It also tests the logging functionality of the
 * CakeViewImpl class.
 */
class CakeViewImplTest {

  private InMemoryAppender appender;

  @BeforeEach
  void setUp() {
    appender = new InMemoryAppender(CakeViewImpl.class);
  }

  @AfterEach
  void tearDown() {
    appender.stop();
  }

  /** Verify if the cake view renders the expected result. */
  @Test
  void testRender() {

    final var layers =
        List.of(
            new CakeLayerInfo("layer1", 1000),
            new CakeLayerInfo("layer2", 2000),
            new CakeLayerInfo("layer3", 3000));

    final var cake = new CakeInfo(new CakeToppingInfo("topping", 1000), layers);
    final var cakes = List.of(cake);

    final var bakingService = mock(CakeBakingService.class);
    when(bakingService.getAllCakes()).thenReturn(cakes);

    final var cakeView = new CakeViewImpl(bakingService);

    assertEquals(0, appender.getLogSize());

    cakeView.render();
    assertEquals(cake.toString(), appender.getLastMessage());
  }

  private static class InMemoryAppender extends AppenderBase<ILoggingEvent> {

    private final List<ILoggingEvent> log = new LinkedList<>();

    public InMemoryAppender(Class clazz) {
      ((Logger) LoggerFactory.getLogger(clazz)).addAppender(this);
      start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
      log.add(eventObject);
    }

    public String getLastMessage() {
      return log.get(log.size() - 1).getFormattedMessage();
    }

    public int getLogSize() {
      return log.size();
    }
  }
}
