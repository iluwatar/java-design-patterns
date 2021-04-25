package com.iluwatar.servicetoworker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * The type Giant controller test.
 */
class GiantControllerTest {

  /**
   * Test set command.
   */
  @Test
  public void testSetCommand() {
    final var model = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    Action action = new Action(model);
    GiantView giantView = new GiantView();
    Dispatcher dispatcher = new Dispatcher(giantView);
    assertEquals(Nourishment.SATURATED, model.getNourishment());
    dispatcher.addAction(action);
    GiantController controller = new GiantController(dispatcher);
    controller.setCommand(new Command(Fatigue.ALERT, Health.HEALTHY, Nourishment.HUNGRY), 0);
    assertEquals(model.getFatigue(), Fatigue.ALERT);
    assertEquals(model.getHealth(), Health.HEALTHY);
    assertEquals(model.getNourishment(), Nourishment.HUNGRY);
  }

  /**
   * Test update view.
   */
  @Test
  public void testUpdateView() {
    final var model = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    GiantView giantView = new GiantView();
    Dispatcher dispatcher = new Dispatcher(giantView);
    GiantController giantController = new GiantController(dispatcher);
    assertDoesNotThrow(() -> giantController.updateView(model));
  }

}