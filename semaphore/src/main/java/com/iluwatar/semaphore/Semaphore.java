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

package com.iluwatar.semaphore;

/**
 * Semaphore is an implementation of a semaphore lock.
 */
public class Semaphore implements Lock {

  private final int licenses;
  /**
   * The number of concurrent resource accesses which are allowed.
   */
  private int counter;

  public Semaphore(int licenses) {
    this.licenses = licenses;
    this.counter = licenses;
  }

  /**
   * Returns the number of licenses managed by the Semaphore.
   */
  public int getNumLicenses() {
    return licenses;
  }

  /**
   * Returns the number of available licenses.
   */
  public int getAvailableLicenses() {
    return counter;
  }

  /**
   * Method called by a thread to acquire the lock. If there are no resources available this will
   * wait until the lock has been released to re-attempt the acquire.
   */
  public synchronized void acquire() throws InterruptedException {
    while (counter == 0) {
      wait();
    }
    counter = counter - 1;
  }

  /**
   * Method called by a thread to release the lock.
   */
  public synchronized void release() {
    if (counter < licenses) {
      counter = counter + 1;
      notify();
    }
  }

}
