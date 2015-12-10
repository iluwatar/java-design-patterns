package com.iluwatar.dependency.injection;

import org.junit.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Date: 12/10/15 - 8:40 PM
 *
 * @author Jeroen Meulemeester
 */
public class AdvancedWizardTest extends StdOutTest {

  /**
   * Test if the {@link AdvancedWizard} smokes whatever instance of {@link Tobacco} is passed to him
   * through the constructor parameter
   */
  @Test
  public void testSmokeEveryThing() throws Exception {

    final Tobacco[] tobaccos = {
        new OldTobyTobacco(), new RivendellTobacco(), new SecondBreakfastTobacco()
    };

    for (final Tobacco tobacco : tobaccos) {
      final AdvancedWizard advancedWizard = new AdvancedWizard(tobacco);
      advancedWizard.smoke();

      // Verify if the wizard is smoking the correct tobacco ...
      verify(getStdOutMock(), times(1)).println("AdvancedWizard smoking " + tobacco.getClass().getSimpleName());

      // ... and nothing else is happening.
      verifyNoMoreInteractions(getStdOutMock());
    }

  }

}