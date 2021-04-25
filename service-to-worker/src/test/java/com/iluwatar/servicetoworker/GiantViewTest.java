package com.iluwatar.servicetoworker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * The type Giant view test.
 */
class GiantViewTest {

  /**
   * Test dispaly giant.
   */
  @Test
  public void testDispalyGiant() {
    GiantModel giantModel = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    GiantView giantView = new GiantView();
    assertDoesNotThrow(() -> giantView.displayGiant(giantModel));
  }
}
