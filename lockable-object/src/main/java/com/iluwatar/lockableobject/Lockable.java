/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
