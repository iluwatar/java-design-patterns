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
package com.iluwatar.dependency.injection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.iluwatar.dependency.injection.utils.InMemoryAppender;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** AdvancedWizardTest */
class AdvancedWizardTest {

  private InMemoryAppender appender;

  @BeforeEach
  void setUp() {
    appender = new InMemoryAppender(Tobacco.class);
  }

  @AfterEach
  void tearDown() {
    appender.stop();
  }

  /**
   * Test if the {@link AdvancedWizard} smokes whatever instance of {@link Tobacco} is passed to him
   * through the constructor parameter
   */
  @Test
  void testSmokeEveryThing() {

    List<Tobacco> tobaccos =
        List.of(new OldTobyTobacco(), new RivendellTobacco(), new SecondBreakfastTobacco());

    // Verify if the wizard is smoking the correct tobacco ...
    tobaccos.forEach(
        tobacco -> {
          final AdvancedWizard advancedWizard = new AdvancedWizard(tobacco);
          advancedWizard.smoke();
          String lastMessage = appender.getLastMessage();
          assertEquals("AdvancedWizard smoking " + tobacco.getClass().getSimpleName(), lastMessage);
        });

    // ... and nothing else is happening.
    assertEquals(tobaccos.size(), appender.getLogSize());
  }
}
