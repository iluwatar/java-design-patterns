package com.iluwatar.lockableobject.domain;

/** A human implementation of a Creature. */
public class Human extends Creature {

  /**
   * A constructor that initializes the attributes of an human.
   *
   * @param name as the name of the creature.
   */
  public Human(String name) {
    super(name);
    setType(CreatureType.HUMAN);
    setDamage(CreatureStats.HUMAN_DAMAGE.getValue());
    setHealth(CreatureStats.HUMAN_HEALTH.getValue());
  }
}
