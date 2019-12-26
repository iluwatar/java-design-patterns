/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.mutex;

/**
 * A Jar has a resource of beans which can only be accessed by a single Thief (thread) at any one
 * time. A Mutex lock is used to prevent more than one Thief taking a bean simultaneously.
 */
public class Jar {

  /**
   * The lock which must be acquired to access the beans resource.
   */
  private final Lock lock;

  /**
   * The resource within the jar.
   */
  private int beans;

  public Jar(int beans, Lock lock) {
    this.beans = beans;
    this.lock = lock;
  }

  /**
   * Method for a thief to take a bean.
   */
  public boolean takeBean() {
    boolean success = false;
    try {
      lock.acquire();
      success = beans > 0;
      if (success) {
        beans = beans - 1;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.release();
    }

    return success;
  }

}
