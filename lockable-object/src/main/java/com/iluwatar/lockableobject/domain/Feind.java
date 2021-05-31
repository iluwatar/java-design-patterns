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
import java.security.SecureRandom;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** A Feind is a creature that all it wants is to posses a Lockable object. */
public class Feind implements Runnable {

  private final Creature creature;
  private final Lockable target;
  private final SecureRandom random;
  private static final Logger LOGGER = LoggerFactory.getLogger(Feind.class.getName());

  /**
   * public constructor.
   *
   * @param feind as the creature to lock to he lockable.
   * @param target as the target object.
   */
  public Feind(@NonNull Creature feind, @NonNull Lockable target) {
    this.creature = feind;
    this.target = target;
    this.random = new SecureRandom();
  }

  @Override
  public void run() {
    if (!creature.acquire(target)) {
      try {
        fightForTheSword(creature, target.getLocker(), target);
      } catch (InterruptedException e) {
        LOGGER.error(e.getMessage());
        Thread.currentThread().interrupt();
      }
    } else {
      LOGGER.info("{} has acquired the sword!", target.getLocker().getName());
    }
  }

  /**
   * Keeps on fighting until the Lockable is possessed.
   *
   * @param reacher as the source creature.
   * @param holder as the foe.
   * @param sword as the Lockable to posses.
   * @throws InterruptedException in case of interruption.
   */
  private void fightForTheSword(Creature reacher, @NonNull Creature holder, Lockable sword)
      throws InterruptedException {
    LOGGER.info("A duel between {} and {} has been started!", reacher.getName(), holder.getName());
    boolean randBool;
    while (this.target.isLocked() && reacher.isAlive() && holder.isAlive()) {
      randBool = random.nextBoolean();
      if (randBool) {
        reacher.attack(holder);
      } else {
        holder.attack(reacher);
      }
    }
    if (reacher.isAlive()) {
      if (!reacher.acquire(sword)) {
        fightForTheSword(reacher, sword.getLocker(), sword);
      } else {
        LOGGER.info("{} has acquired the sword!", reacher.getName());
      }
    }
  }
}
