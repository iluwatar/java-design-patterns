package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;

/** This interface describes the methods to be supported by a lockable-object. */
public interface Lockable {

  /**
   * Checks if the object is locked.
   *
   * @return true if it is locked.
   */
  boolean isLocked();

  /**
   * locks the object with the creature as the locker.
   *
   * @param creature as the locker.
   * @return true if the object was locked successfully.
   */
  boolean lock(Creature creature);

  /**
   * Unlocks the object.
   *
   * @param creature as the locker.
   */
  void unlock(Creature creature);

  /**
   * Gets the locker.
   *
   * @return the Creature that holds the object. Returns null if no one is locking.
   */
  Creature getLocker();

  /**
   * Returns the name of the object.
   *
   * @return the name of the object.
   */
  String getName();
}
