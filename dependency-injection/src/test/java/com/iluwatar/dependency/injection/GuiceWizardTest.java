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

package com.iluwatar.dependency.injection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.iluwatar.dependency.injection.utils.InMemoryAppender;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Date: 12/10/15 - 8:57 PM
 *
 * @author Jeroen Meulemeester
 */
class GuiceWizardTest {

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
   * Test if the {@link GuiceWizard} smokes whatever instance of {@link Tobacco} is passed to him
   * through the constructor parameter
   */
  @Test
  void testSmokeEveryThingThroughConstructor() throws Exception {

    List<Tobacco> tobaccos = List.of(
        new OldTobyTobacco(),
        new RivendellTobacco(),
        new SecondBreakfastTobacco()
    );

    // Verify if the wizard is smoking the correct tobacco ...
    tobaccos.forEach(tobacco -> {
      final GuiceWizard guiceWizard = new GuiceWizard(tobacco);
      guiceWizard.smoke();
      String lastMessage = appender.getLastMessage();
      assertEquals("GuiceWizard smoking " + tobacco.getClass().getSimpleName(), lastMessage);
    });

    // ... and nothing else is happening.
    assertEquals(tobaccos.size(), appender.getLogSize());
  }

  /**
   * Test if the {@link GuiceWizard} smokes whatever instance of {@link Tobacco} is passed to him
   * through the Guice google inject framework
   */
  @Test
  void testSmokeEveryThingThroughInjectionFramework() throws Exception {

    List<Class<? extends Tobacco>> tobaccos = List.of(
        OldTobyTobacco.class,
        RivendellTobacco.class,
        SecondBreakfastTobacco.class
    );

    // Configure the tobacco in the injection framework ...
    // ... and create a new wizard with it
    // Verify if the wizard is smoking the correct tobacco ...
    tobaccos.forEach(tobaccoClass -> {
      final var injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
          bind(Tobacco.class).to(tobaccoClass);
        }
      });
      final var guiceWizard = injector.getInstance(GuiceWizard.class);
      guiceWizard.smoke();
      String lastMessage = appender.getLastMessage();
      assertEquals("GuiceWizard smoking " + tobaccoClass.getSimpleName(), lastMessage);
    });

    // ... and nothing else is happening.
    assertEquals(tobaccos.size(), appender.getLogSize());
  }

}
