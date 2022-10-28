package com.iluwatar.lockableobject.domain;

/** Attribute constants of each Creature implementation. */
public enum CreatureStats {
  ELF_HEALTH(90),
  ELF_DAMAGE(40),
  ORC_HEALTH(70),
  ORC_DAMAGE(50),
  HUMAN_HEALTH(60),
  HUMAN_DAMAGE(60);

  int value;

  private CreatureStats(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
