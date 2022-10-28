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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;

class RemoveLockTest {
  @Test
  void removingLockTest() throws InvalidNameException {
    Manager manager = Manager.getManager("TESTING");
    Object lockable = new Object();
    manager.canLock("Lock", lockable);
    assertEquals("Lock", manager.removeLock(lockable));

    //Adding multiple locks then removing
    Object lockable1 = new Object();
    Object lockable2 = new Object();
    Object lockable3 = new Object();
    Object lockable4 = new Object();

    manager.canLock("Lock1", lockable1);
    manager.canLock("Lock2", lockable2);
    manager.canLock("Lock3", lockable3);
    manager.canLock("Lock4", lockable4);

    assertEquals("Lock2", manager.removeLock(lockable2));
    assertEquals("Lock4", manager.removeLock(lockable4));
    assertEquals("Lock1", manager.removeLock(lockable1));
    assertEquals("Lock3", manager.removeLock(lockable3));

    //Re-adding a removed lock with the same name
    assertTrue(manager.canLock("Lock", lockable));

    assertEquals("Lock", manager.removeLock(lockable));
  }
}
