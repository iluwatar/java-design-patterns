package com.iluwatar.model.view.controller;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Date: 12/20/15 - 2:10 PM
 *
 * @author Jeroen Meulemeester
 */
public class GiantModelTest {

  /**
   * Verify if the health value is set properly though the constructor and setter
   */
  @Test
  public void testSetHealth() {
    final GiantModel model = new GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
    assertEquals(Health.HEALTHY, model.getHealth());
    for (final Health health : Health.values()) {
      model.setHealth(health);
      assertEquals(health, model.getHealth());
      assertEquals("The giant looks " + health.toString() + ", alert and saturated.", model.toString());
    }
  }

  /**
   * Verify if the fatigue level is set properly though the constructor and setter
   */
  @Test
  public void testSetFatigue() {
    final GiantModel model = new GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
    assertEquals(Fatigue.ALERT, model.getFatigue());
    for (final Fatigue fatigue : Fatigue.values()) {
      model.setFatigue(fatigue);
      assertEquals(fatigue, model.getFatigue());
      assertEquals("The giant looks healthy, " + fatigue.toString() + " and saturated.", model.toString());
    }
  }

  /**
   * Verify if the nourishment level is set properly though the constructor and setter
   */
  @Test
  public void testSetNourishment() {
    final GiantModel model = new GiantModel(Health.HEALTHY, Fatigue.ALERT, Nourishment.SATURATED);
    assertEquals(Nourishment.SATURATED, model.getNourishment());
    for (final Nourishment nourishment : Nourishment.values()) {
      model.setNourishment(nourishment);
      assertEquals(nourishment, model.getNourishment());
      assertEquals("The giant looks healthy, alert and " + nourishment.toString() + ".", model.toString());
    }
  }

}
