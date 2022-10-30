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
package com.iluwatar.lockmanager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Manager Class, the entire implementation of the Online Lock-Manager design pattern.
 */
public class Manager {

  private final HashMap<Object, String> lockSet;
  private static final HashMap<String, Object> managerSet = new HashMap<>();
  private static final Set<String> invalidNames = new HashSet<>();

  /**
   * Constructor for the manager class, creates a new set of locks and adds invalid names for managers
   * to the invalidNames hashmap.
   */
  public Manager() {
    lockSet = new HashMap<>();
    invalidNames.add(null);
    invalidNames.add("");
  }

  /**
   * Returns the manager object provided that it is managerSet.
   * If the manager is not in the managerSet, the method adds it to the hashmap and then returns.
   * Throws InvalidNameException if the name input is in the invalidNames Set.
   *
   * @param name the name of the manager you wish to retrieve or add.
   * @return Manager object requested.
   * @throws InvalidNameException if the name provided is invalid.
   */
  public static synchronized Manager getManager(String name) throws InvalidNameException {
    // If the name is invalid then throw an InvalidNameException
    if (invalidNames.contains(name)) {
      throw new InvalidNameException("Invalid Name of Manager.");
    }
    // If the set of manager's does not contain the key, create a new manager instance and add it to the set.
    managerSet.computeIfAbsent(name, key -> new Manager());
    return (Manager) managerSet.get(name);
  }

  /**
   * Request a lock from the manager for the lockable object.
   * Provided that the lockable object exists, check if the associated key equals the name provided.
   *
   * @param name     the name of the lockable object we are checking for.
   * @param lockable object we are attempting to retrieve a lock for.
   * @throws InvalidNameException if the name provided is invalid.
   */
  public boolean canLock(String name, Object lockable) throws InvalidNameException {
    // If the name is invalid then throw an InvalidNameException
    if (invalidNames.contains(name)) {
      throw new InvalidNameException("Invalid Name of Manager.");
    }
    // As this is an online implementation, we must synchronise lockSet to prevent multiple threads
    // accessing the set at the same time, causing data overwrites.
    synchronized (lockSet) {
      // If the lockSet doesn't contain the lockable object, then add it with the provided name.
      lockSet.computeIfAbsent(lockable, key -> name);
      // Otherwise, we must check that the name provided matches the key name.
      String lockName = lockSet.get(lockable);
      return name.equals(lockName);
    }
  }

  /**
   * Remove a lockable object from the set of lockable objects.
   *
   * @param lockable lockable object that we wish to remove.
   */
  public Object removeLock(Object lockable) {
    return lockSet.remove(lockable);
  }
}