package com.iluwatar.servicetoworker;

/**
 * GiantModel contains the giant data.
 */
public class GiantModel {

  private String name;
  private Health health;
  private Fatigue fatigue;
  private Nourishment nourishment;

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
    this.health = health;
    this.fatigue = fatigue;
    this.nourishment = nourishment;
  }

  public Health getHealth() {
    return health;
  }

  public void setHealth(Health health) {
    this.health = health;
  }

  public Fatigue getFatigue() {
    return fatigue;
  }

  public void setFatigue(Fatigue fatigue) {
    this.fatigue = fatigue;
  }

  public Nourishment getNourishment() {
    return nourishment;
  }

  public void setNourishment(Nourishment nourishment) {
    this.nourishment = nourishment;
  }

  @Override
  public String toString() {
    return String
        .format("Giant %s, The giant looks %s, %s and %s.", name, health, fatigue, nourishment);
  }
}
