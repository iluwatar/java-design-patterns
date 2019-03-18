/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
 * Mutex is an implementation of a mutual exclusion lock.
 */
public class Mutex implements Lock {

  /**
   * The current owner of the lock.
   */
  private Object owner;

  /**
   * Returns the current owner of the Mutex, or null if available
   */
  public Object getOwner() {
    return owner;
  }
  
  /**
   * Method called by a thread to acquire the lock. If the lock has already
   * been acquired this will wait until the lock has been released to 
   * re-attempt the acquire.
   */
  @Override
  public synchronized void acquire() throws InterruptedException {
    while (owner != null) {
      wait();
    }

    owner = Thread.currentThread();
  }

  /**
   * Method called by a thread to release the lock.
   */
  @Override
  public synchronized void release() {
    if (Thread.currentThread() == owner) {
      owner = null;
      notify();
    }
  }

}
