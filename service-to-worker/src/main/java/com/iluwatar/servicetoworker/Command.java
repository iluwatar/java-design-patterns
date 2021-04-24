package com.iluwatar.servicetoworker;

/**
 * The type Command.
 */
public class Command {

  private final Fatigue fatigue;
  private final Health health;
  private final Nourishment nourishment;

  /**
   * Instantiates a new Command.
   *
   * @param fatigue     the fatigue
   * @param health      the health
   * @param nourishment the nourishment
   */
  Command(Fatigue fatigue, Health health, Nourishment nourishment) {
    this.fatigue = fatigue;
    this.health = health;
    this.nourishment = nourishment;
  }

  public Nourishment getNourishment() {
    return nourishment;
  }

  public Health getHealth() {
    return health;
  }

  public Fatigue getFatigue() {
    return fatigue;
  }
}
