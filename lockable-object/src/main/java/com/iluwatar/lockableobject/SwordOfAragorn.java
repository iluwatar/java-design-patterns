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

package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * An implementation of a Lockable object. This is the the Sword of Aragorn and every creature wants
 * to posses it!
 */
@Slf4j
public class SwordOfAragorn implements Lockable {

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
  public boolean lock(@NonNull Creature creature) {
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
  public void unlock(@NonNull Creature creature) {
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
