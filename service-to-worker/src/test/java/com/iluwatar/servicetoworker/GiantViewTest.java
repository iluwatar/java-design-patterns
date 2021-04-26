package com.iluwatar.servicetoworker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.iluwatar.model.view.controller.Fatigue;
import com.iluwatar.model.view.controller.Health;
import com.iluwatar.model.view.controller.Nourishment;
import org.junit.jupiter.api.Test;

/**
 * The type Giant view test.
 */
class GiantViewTest {

  /**
   * Test dispaly giant.
   */
  @Test
  void testDispalyGiant() {
    GiantModel giantModel = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    GiantView giantView = new GiantView();
    assertDoesNotThrow(() -> giantView.displayGiant(giantModel));
  }
}
