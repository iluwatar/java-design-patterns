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
package com.iluwatar.front.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.iluwatar.front.controller.utils.InMemoryAppender;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/** ViewTest */
class ViewTest {

  private InMemoryAppender appender;

  @BeforeEach
  void setUp() {
    appender = new InMemoryAppender();
  }

  @AfterEach
  void tearDown() {
    appender.stop();
  }

  static List<Object[]> dataProvider() {
    return List.of(
        new Object[] {new ArcherView(), "Displaying archers"},
        new Object[] {new CatapultView(), "Displaying catapults"},
        new Object[] {new ErrorView(), "Error 500"});
  }

  /**
   * @param view The view that's been tested
   * @param displayMessage The expected display message
   */
  @ParameterizedTest
  @MethodSource("dataProvider")
  void testDisplay(View view, String displayMessage) {
    assertEquals(0, appender.getLogSize());
    view.display();
    assertEquals(displayMessage, appender.getLastMessage());
    assertEquals(1, appender.getLogSize());
  }
}
