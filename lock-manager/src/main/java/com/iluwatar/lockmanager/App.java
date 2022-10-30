/*
 * This project is licensed under the MIT license. Module model-view- viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
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

import lombok.extern.slf4j.Slf4j;

/**
 * The lock manager design pattern is used to store objects by reference rather than having
 * a direct boolean field on each object.
 *
 * <p>In this example the Manager ({@link Manager}) is used to distribute keys to clients and then
 * informs the client if they already have a key to the object and returns false if the client does
 * not have access to the locked object.
 */
@Slf4j
public class App {

  // The usernames of three different clients.
  public static final String client1 = "client1";
  public static final String client2 = "client2";
  public static final String client3 = "client3";

  // The objects that we are locking.
  public static final Object object1 = new Object();
  public static final Object object2 = new Object();
  public static final Object object3 = new Object();

  /**
   * Visualization of the manager working.
   */
  public static void main(String[] args) {
    try {
      // Declaring a new manager for the clients.
      Manager manager = Manager.getManager("Clients");
      // Adding the objects to the lockable object set under the username of each of the clients.
      manager.canLock(client1, object1);
      manager.canLock(client2, object2);
      manager.canLock(client3, object3);

      // Giving the clients access to their specific object with the key as their usernames.
      LOGGER.info("Gave user 1 access to object 1: " + manager.canLock(client1, object1));
      LOGGER.info("Gave user 1 access to object 2: " + manager.canLock(client2, object2));
      LOGGER.info("Gave user 1 access to object 3: " + manager.canLock(client3, object3));
      // Checking if users have access to other users objects.
      LOGGER.info("Does user 1 have access to object 2: " + manager.canLock(client1, object2));
      LOGGER.info("Does user 2 have access to object 1: " + manager.canLock(client2, object1));
      LOGGER.info("Does user 3 have access to object 1: " + manager.canLock(client3, object1));
      // Removing the object2 from the lockable object set.
      LOGGER.info("Removed object2 with user: " + manager.removeLock(object2));
      // Adding object2 to the set again but so that client3 has privileges.
      LOGGER.info("Gave user 3 access to object 2: " + manager.canLock(client3, object2));
      // Checking that client3 has access to object2
      LOGGER.info("Does user 3 have access to object 2: " + manager.canLock(client3, object3));
      // Checking that client3 has access to object3
      LOGGER.info("Does user 3 have access to object 3: " + manager.canLock(client3, object3));

    } catch (Exception e) {
      //If the name input is invalid at any point.
      LOGGER.info("Invalid name.");
    }
  }
}
