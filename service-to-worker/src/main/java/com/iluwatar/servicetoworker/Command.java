package com.iluwatar.servicetoworker;

import com.iluwatar.model.view.controller.Fatigue;
import com.iluwatar.model.view.controller.Health;
import com.iluwatar.model.view.controller.Nourishment;

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
  public Command(Fatigue fatigue, Health health, Nourishment nourishment) {
    this.fatigue = fatigue;
    this.health = health;
    this.nourishment = nourishment;
  }

  /**
   * Gets nourishment from the Command object.
   *
   * @return the nourishment
   */
  Nourishment getNourishment() {
    return nourishment;
  }

  /**
   * Gets health from the Command object.
   *
   * @return the health
   */
  Health getHealth() {
    return health;
  }

  /**
   * Gets fatigue from the Command object.
   *
   * @return the fatigue
   */
  Fatigue getFatigue() {
    return fatigue;
  }
}
