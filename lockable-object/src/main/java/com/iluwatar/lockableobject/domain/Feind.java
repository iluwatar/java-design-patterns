package com.iluwatar.lockableobject.domain;

import com.iluwatar.lockableobject.Lockable;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** A Feind is a creature that all it wants is to posses a Lockable object. */
public class Feind implements Runnable {

  private final Creature feind;
  private final Lockable target;
  private static final Logger LOGGER = LoggerFactory.getLogger(Feind.class.getName());

  public Feind(Creature feind, Lockable target) {
    this.feind = feind;
    this.target = target;
  }

  @Override
  public void run() {
    if (!feind.acquire(target)) {
      try {
        fightForTheSword(feind, target.getLocker(), target);
      } catch (InterruptedException e) {
        LOGGER.error(e.getMessage());
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
    Random random = new Random();
    LOGGER.info("A duel between {} and {} has been started!", reacher.getName(), holder.getName());
    while (this.target.isLocked() && reacher.isAlive() && holder.isAlive()) {
      if (random.nextBoolean()) {
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
