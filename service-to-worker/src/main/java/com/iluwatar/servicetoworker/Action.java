package com.iluwatar.servicetoworker;

import com.iluwatar.model.view.controller.Fatigue;
import com.iluwatar.model.view.controller.Health;
import com.iluwatar.model.view.controller.Nourishment;

/**
 * The type Action (Worker), which can process user input and perform a specific update on the
 * model.
 */
public class Action {

  public GiantModel giant;

  /**
   * Instantiates a new Action.
   *
   * @param giant the giant
   */
  public Action(GiantModel giant) {
    this.giant = giant;
  }

  /**
   * Update model based on command.
   *
   * @param command the command
   */
  public void updateModel(Command command) {
    setFatigue(command.getFatigue());
    setHealth(command.getHealth());
    setNourishment(command.getNourishment());
  }

  /**
   * Sets health.
   *
   * @param health the health
   */
  public void setHealth(Health health) {
    giant.setHealth(health);
  }

  /**
   * Sets fatigue.
   *
   * @param fatigue the fatigue
   */
  public void setFatigue(Fatigue fatigue) {
    giant.setFatigue(fatigue);
  }

  /**
   * Sets nourishment.
   *
   * @param nourishment the nourishment
   */
  public void setNourishment(Nourishment nourishment) {
    giant.setNourishment(nourishment);
  }
}
