package com.iluwatar.dependency.injection;

import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Date: 12/10/15 - 8:26 PM
 *
 * @author Jeroen Meulemeester
 */
public class SimpleWizardTest extends StdOutTest {

  /**
   * Test if the {@link SimpleWizard} does the only thing it can do: Smoke it's {@link
   * OldTobyTobacco}
   */
  @Test
  public void testSmoke() {
    final SimpleWizard simpleWizard = new SimpleWizard();
    simpleWizard.smoke();
    verify(getStdOutMock(), times(1)).println("SimpleWizard smoking OldTobyTobacco");
    verifyNoMoreInteractions(getStdOutMock());
  }

}