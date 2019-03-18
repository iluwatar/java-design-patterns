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
 * A Mutex prevents multiple threads from accessing a resource simultaneously.
 * <p>
 * In this example we have two thieves who are taking beans from a jar.
 * Only one thief can take a bean at a time. This is ensured by a Mutex lock
 * which must be acquired in order to access the jar. Each thief attempts to
 * acquire the lock, take a bean and then release the lock. If the lock has 
 * already been acquired, the thief will be prevented from continuing (blocked)
 * until the lock has been released. The thieves stop taking beans once there
 * are no beans left to take.
 */
public class App {

  /**
   * main method
   */
  public static void main(String[] args) {
    Mutex mutex = new Mutex();
    Jar jar = new Jar(1000, mutex);
    Thief peter = new Thief("Peter", jar);
    Thief john = new Thief("John", jar);
    peter.start();
    john.start();
  }

}
