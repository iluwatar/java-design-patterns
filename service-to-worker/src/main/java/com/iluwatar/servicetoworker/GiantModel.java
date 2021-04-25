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

  public Health getHealth() {
    return giantModel.getHealth();
  }

  public void setHealth(Health health) {
    giantModel.setHealth(health);
  }

  public Fatigue getFatigue() {
    return giantModel.getFatigue();
  }

  public void setFatigue(Fatigue fatigue) {
    giantModel.setFatigue(fatigue);
  }

  public Nourishment getNourishment() {
    return giantModel.getNourishment();
  }

  public void setNourishment(Nourishment nourishment) {
    giantModel.setNourishment(nourishment);
  }

  @Override
  public String toString() {
    return String
        .format("Giant %s, The giant looks %s, %s and %s.", name,
            giantModel.getHealth(), giantModel.getFatigue(), giantModel.getNourishment());
  }
}
