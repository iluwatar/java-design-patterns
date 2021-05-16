package com.iluwatar.lockableobject.domain;

/** An Elf implementation of a Creature. */
public class Elf extends Creature {

  /**
   * A constructor that initializes the attributes of an elf.
   *
   * @param name as the name of the creature.
   */
  public Elf(String name) {
    super(name);
    setType(CreatureType.ELF);
    setDamage(CreatureStats.ELF_DAMAGE.getValue());
    setHealth(CreatureStats.ELF_HEALTH.getValue());
  }
}
