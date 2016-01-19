package com.iluwatar.dependency.injection;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Date: 12/10/15 - 8:57 PM
 *
 * @author Jeroen Meulemeester
 */
public class GuiceWizardTest extends StdOutTest {

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
      verify(getStdOutMock(), times(1)).println("GuiceWizard smoking " + tobacco.getClass().getSimpleName());

      // ... and nothing else is happening.
      verifyNoMoreInteractions(getStdOutMock());
    }

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
      verify(getStdOutMock(), times(1)).println("GuiceWizard smoking " + tobaccoClass.getSimpleName());

      // ... and nothing else is happening.
      verifyNoMoreInteractions(getStdOutMock());
    }

  }

}