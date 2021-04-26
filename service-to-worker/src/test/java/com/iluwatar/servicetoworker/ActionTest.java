package com.iluwatar.servicetoworker;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.iluwatar.model.view.controller.Fatigue;
import com.iluwatar.model.view.controller.Health;
import com.iluwatar.model.view.controller.Nourishment;
import org.junit.jupiter.api.Test;

/**
 * The type Action test.
 */
class ActionTest {

  /**
   * Verify if the health value is set properly though the constructor and setter
   */
  @Test
  void testSetHealth() {
    final var model = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    Action action = new Action(model);
    assertEquals(Health.HEALTHY, model.getHealth());
    var messageFormat = "Giant giant1, The giant looks %s, alert and saturated.";
    for (final var health : Health.values()) {
      action.setHealth(health);
      assertEquals(health, model.getHealth());
      assertEquals(String.format(messageFormat, health), model.toString());
    }
  }

  /**
   * Verify if the fatigue level is set properly though the constructor and setter
   */
  @Test
  void testSetFatigue() {
    final var model = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    Action action = new Action(model);
    assertEquals(Fatigue.ALERT, model.getFatigue());
    var messageFormat = "Giant giant1, The giant looks healthy, %s and saturated.";
    for (final var fatigue : Fatigue.values()) {
      action.setFatigue(fatigue);
      assertEquals(fatigue, model.getFatigue());
      assertEquals(String.format(messageFormat, fatigue), model.toString());
    }
  }

  /**
   * Verify if the nourishment level is set properly though the constructor and setter
   */
  @Test
  void testSetNourishment() {
    final var model = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    Action action = new Action(model);
    assertEquals(Nourishment.SATURATED, model.getNourishment());
    var messageFormat = "Giant giant1, The giant looks healthy, alert and %s.";
    for (final var nourishment : Nourishment.values()) {
      action.setNourishment(nourishment);
      assertEquals(nourishment, model.getNourishment());
      assertEquals(String.format(messageFormat, nourishment), model.toString());
    }
  }

  /**
   * Test update model.
   */
  @Test
  void testUpdateModel() {
    final var model = new GiantModel("giant1", Health.HEALTHY, Fatigue.ALERT,
        Nourishment.SATURATED);
    Action action = new Action(model);
    assertEquals(Nourishment.SATURATED, model.getNourishment());
    for (final var nourishment : Nourishment.values()) {
      for (final var fatigue : Fatigue.values()) {
        for (final var health : Health.values()) {
          Command cmd = new Command(fatigue, health, nourishment);
          action.updateModel(cmd);
          assertEquals(nourishment, model.getNourishment());
          assertEquals(fatigue, model.getFatigue());
          assertEquals(health, model.getHealth());
        }
      }
    }
  }
}
