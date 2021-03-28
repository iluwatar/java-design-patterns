/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.acyclicvisitor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static uk.org.lidalia.slf4jext.Level.INFO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

/**
 * ConfigureForDosVisitor test class
 */
class ConfigureForDosVisitorTest {

  private final TestLogger logger = TestLoggerFactory.getTestLogger(ConfigureForDosVisitor.class);

  @Test
  void testVisitForZoom() {
    var conDos = new ConfigureForDosVisitor();
    var zoom = new Zoom();

    conDos.visit(zoom);

    assertThat(logger.getLoggingEvents())
        .extracting("level", "message")
        .contains(tuple(INFO, zoom + " used with Dos configurator."));
  }

  @Test
  void testVisitForHayes() {
    var conDos = new ConfigureForDosVisitor();
    var hayes = new Hayes();

    conDos.visit(hayes);

    assertThat(logger.getLoggingEvents())
        .extracting("level", "message")
        .contains(tuple(INFO, hayes + " used with Dos configurator."));
  }

  @AfterEach
  public void clearLoggers() {
    TestLoggerFactory.clear();
  }
}
