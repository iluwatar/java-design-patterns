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
package com.iluwatar.dependency.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.iluwatar.dependency.injection.utils.InMemoryAppender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Date: 12/10/15 - 8:57 PM
 *
 * @author Jeroen Meulemeester
 */
public class GuiceWizardTest {

  private InMemoryAppender appender;

  @Before
  public void setUp() {
    appender = new InMemoryAppender(Tobacco.class);
  }

  @After
  public void tearDown() {
    appender.stop();
  }

  /**
   * Test if the {@link GuiceWizard} smokes whatever instance of {@link Tobacco} is passed to him
   * through the constructor parameter
   */
  @Test
  public void testSmokeEveryThingThroughConstructor() throws Exception {

    final Tobacco[] tobaccos = {
        new OldTobyTobacco(), new RivendellTobacco(), new SecondBreakfastTobacco()
    };

    for (final Tobacco tobacco : tobaccos) {
      final GuiceWizard guiceWizard = new GuiceWizard(tobacco);
      guiceWizard.smoke();

      // Verify if the wizard is smoking the correct tobacco ...
      assertEquals("GuiceWizard smoking " + tobacco.getClass().getSimpleName(), appender.getLastMessage());
    }

    // ... and nothing else is happening.
    assertEquals(tobaccos.length, appender.getLogSize());
  }

  /**
   * Test if the {@link GuiceWizard} smokes whatever instance of {@link Tobacco} is passed to him
   * through the Guice google inject framework
   */
  @Test
  public void testSmokeEveryThingThroughInjectionFramework() throws Exception {

    @SuppressWarnings("unchecked")
    final Class<? extends Tobacco>[] tobaccos = new Class[]{
        OldTobyTobacco.class, RivendellTobacco.class, SecondBreakfastTobacco.class
    };

    for (final Class<? extends Tobacco> tobaccoClass : tobaccos) {
      // Configure the tobacco in the injection framework ...
      final Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {
          bind(Tobacco.class).to(tobaccoClass);
        }
      });

      // ... and create a new wizard with it
      final GuiceWizard guiceWizard = injector.getInstance(GuiceWizard.class);
      guiceWizard.smoke();

      // Verify if the wizard is smoking the correct tobacco ...
      assertEquals("GuiceWizard smoking " + tobaccoClass.getSimpleName(), appender.getLastMessage());
    }

    // ... and nothing else is happening.
    assertEquals(tobaccos.length, appender.getLogSize());
  }

}
