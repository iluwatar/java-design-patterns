package com.iluwatar.servicetoworker;

import com.iluwatar.model.view.controller.Fatigue;
import com.iluwatar.model.view.controller.Health;
import com.iluwatar.model.view.controller.Nourishment;

/**
 * GiantModel contains the giant data.
 */
public class GiantModel {

  private final com.iluwatar.model.view.controller.GiantModel giantModel;
  private final String name;

  /**
   * Instantiates a new Giant model.
   *
   * @param name        the name
   * @param health      the health
   * @param fatigue     the fatigue
   * @param nourishment the nourishment
   */
  GiantModel(String name, Health health, Fatigue fatigue, Nourishment nourishment) {
    this.name = name;
    this.giantModel = new com.iluwatar.model.view.controller.GiantModel(health, fatigue,
        nourishment);
  }

  /**
   * Gets health.
   *
   * @return the health
   */
  Health getHealth() {
    return giantModel.getHealth();
  }

  /**
   * Sets health.
   *
   * @param health the health
   */
  void setHealth(Health health) {
    giantModel.setHealth(health);
  }

  /**
   * Gets fatigue.
   *
   * @return the fatigue
   */
  Fatigue getFatigue() {
    return giantModel.getFatigue();
  }

  void setFatigue(Fatigue fatigue) {
    giantModel.setFatigue(fatigue);
  }

  /**
   * Gets nourishment.
   *
   * @return the nourishment
   */
  Nourishment getNourishment() {
    return giantModel.getNourishment();
  }

  /**
   * Sets nourishment.
   *
   * @param nourishment the nourishment
   */
  void setNourishment(Nourishment nourishment) {
    giantModel.setNourishment(nourishment);
  }

  @Override
  public String toString() {
    return String
        .format("Giant %s, The giant looks %s, %s and %s.", name,
            giantModel.getHealth(), giantModel.getFatigue(), giantModel.getNourishment());
  }
}
