package com.iluwatar.lockableobject.domain;

/** A Orc implementation of a Creature. */
public class Orc extends Creature {
  /**
   * A constructor that initializes the attributes of an orc.
   *
   * @param name as the name of the creature.
   */
  public Orc(String name) {
    super(name);
    setType(CreatureType.ORC);
    setDamage(CreatureStats.ORC_DAMAGE.getValue());
    setHealth(CreatureStats.ORC_HEALTH.getValue());
  }
}
