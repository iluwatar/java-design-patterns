package com.iluwatar.servicetoworker;

import com.iluwatar.model.view.controller.Fatigue;
import com.iluwatar.model.view.controller.Health;
import com.iluwatar.model.view.controller.Nourishment;
import lombok.Getter;

/**
 * The type Command.
 */
public class Command {

  @Getter
  private final Fatigue fatigue;
  @Getter
  private final Health health;
  @Getter
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
}
