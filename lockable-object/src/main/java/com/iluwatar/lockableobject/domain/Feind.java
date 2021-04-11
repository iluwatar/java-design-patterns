package com.iluwatar.lockableobject.domain;

import com.iluwatar.lockableobject.Lockable;
import java.security.SecureRandom;
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
  public Feind(Creature feind, Lockable target) {
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
  private void fightForTheSword(Creature reacher, Creature holder, Lockable sword)
      throws InterruptedException {
    LOGGER.info("A duel between {} and {} has been started!", reacher.getName(), holder.getName());
    while (this.target.isLocked() && reacher.isAlive() && holder.isAlive()) {
      if (random.nextBoolean()) {
        reacher.attack(holder);
      } else {
        holder.attack(reacher);
      }
      synchronized (this) {
        wait(new SecureRandom().nextInt(50)); // cool down
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
