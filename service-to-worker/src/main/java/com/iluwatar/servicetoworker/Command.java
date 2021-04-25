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

  Nourishment getNourishment() {
    return nourishment;
  }

  Health getHealth() {
    return health;
  }

  Fatigue getFatigue() {
    return fatigue;
  }
}
