/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
