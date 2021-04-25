package com.iluwatar.servicetoworker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.iluwatar.model.view.controller.Fatigue;
import com.iluwatar.model.view.controller.Health;
import com.iluwatar.model.view.controller.Nourishment;
import org.junit.jupiter.api.Test;


/**
 * The type Giant controller test.
 */
class GiantControllerTest {

  /**
   * Test set command.
   */
  @Test
  void testSetCommand() {
    final var model = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    Action action = new Action(model);
    GiantView giantView = new GiantView();
    Dispatcher dispatcher = new Dispatcher(giantView);
    assertEquals(Nourishment.SATURATED, model.getNourishment());
    dispatcher.addAction(action);
    GiantController controller = new GiantController(dispatcher);
    controller.setCommand(new Command(Fatigue.ALERT, Health.HEALTHY, Nourishment.HUNGRY), 0);
    assertEquals(Fatigue.ALERT, model.getFatigue());
    assertEquals(Health.HEALTHY, model.getHealth());
    assertEquals(Nourishment.HUNGRY, model.getNourishment());
  }

  /**
   * Test update view.
   */
  @Test
  void testUpdateView() {
    final var model = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    GiantView giantView = new GiantView();
    Dispatcher dispatcher = new Dispatcher(giantView);
    GiantController giantController = new GiantController(dispatcher);
    assertDoesNotThrow(() -> giantController.updateView(model));
  }

}