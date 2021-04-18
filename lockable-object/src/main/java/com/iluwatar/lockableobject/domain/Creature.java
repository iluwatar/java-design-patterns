package com.iluwatar.lockableobject.domain;

import com.iluwatar.lockableobject.Lockable;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * An abstract class of a creature that wanders across the wasteland. It can attack, get hit and
 * acquire a Lockable object.
 */
@Getter
@Setter
@Slf4j
public abstract class Creature {

  private String name;
  private CreatureType type;
  private int health;
  private int damage;
  Set<Lockable> instruments;

  protected Creature(@NonNull String name) {
    this.name = name;
    this.instruments = new HashSet<>();
  }

  /**
   * Reaches for the Lockable and tried to hold it.
   *
   * @param lockable as the Lockable to lock.
   * @return true of Lockable was locked by this creature.
   */
  public boolean acquire(@NonNull Lockable lockable) {
    if (lockable.lock(this)) {
      instruments.add(lockable);
      return true;
    }
    return false;
  }

  /** Terminates the Creature and unlocks all of the Lockable that it posses. */
  public synchronized void kill() {
    LOGGER.info("{} {} has been slayed!", type, name);
    for (Lockable lockable : instruments) {
      lockable.unlock(this);
    }
    this.instruments.clear();
  }

  /**
   * Attacks a foe.
   *
   * @param creature as the foe to be attacked.
   */
  public synchronized void attack(@NonNull Creature creature) {
    creature.hit(getDamage());
  }

  /**
   * When a creature gets hit it removed the amount of damage from the creature's life.
   *
   * @param damage as the damage that was taken.
   */
  public synchronized void hit(int damage) {
    if (damage < 0) {
      throw new IllegalArgumentException("Damage cannot be a negative number");
    }
    if (isAlive()) {
      setHealth(getHealth() - damage);
      if (!isAlive()) {
        kill();
      }
    }
  }

  /**
   * Checks if the creature is still alive.
   *
   * @return true of creature is alive.
   */
  public synchronized boolean isAlive() {
    return getHealth() > 0;
  }

}
