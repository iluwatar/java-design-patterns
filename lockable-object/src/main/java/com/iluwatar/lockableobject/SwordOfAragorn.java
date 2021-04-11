package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of a Lockable object.
 * This is the the Sword of Aragorn and every creature wants to posses it!
 */
public class SwordOfAragorn implements Lockable {

  private static final Logger LOGGER = LoggerFactory.getLogger(SwordOfAragorn.class.getName());
  private Creature locker;
  private final Object synchronizer;
  private static final String NAME = "The Sword of Aragorn";

  public SwordOfAragorn() {
    this.locker = null;
    this.synchronizer = new Object();
  }

  @Override
  public boolean isLocked() {
    return this.locker != null;
  }

  @Override
  public boolean lock(Creature creature) {
    if (creature == null) {
      throw new NullPointerException("id must not be null.");
    }
    synchronized (synchronizer) {
      LOGGER.info("{} is now trying to acquire {}!", creature.getName(), this.getName());
      if (!isLocked()) {
        locker = creature;
        return true;
      } else {
        if (!locker.getName().equals(creature.getName())) {
          return false;
        }
      }
    }
    return false;
  }

  @Override
  public void unlock(Creature creature) {
    synchronized (synchronizer) {
      if (locker != null && locker.getName().equals(creature.getName())) {
        locker = null;
        LOGGER.info("{} is now free!", this.getName());
      }
      if (locker != null) {
        throw new LockingException("You cannot unlock an object you are not the owner of.");
      }
    }
  }

  @Override
  public Creature getLocker() {
    return this.locker;
  }

  @Override
  public String getName() {
    return NAME;
  }
}
