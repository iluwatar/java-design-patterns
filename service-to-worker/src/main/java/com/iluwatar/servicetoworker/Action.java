package com.iluwatar.servicetoworker;

/**
 * The type Action (Worker), which can processed user input and perform a specific update on the
 * model.
 */
public class Action {

  private final GiantModel giant;

  /**
   * Instantiates a new Action.
   *
   * @param giant the giant
   */
  public Action(GiantModel giant) {
    this.giant = giant;
  }

  /**
   * Update model.
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
