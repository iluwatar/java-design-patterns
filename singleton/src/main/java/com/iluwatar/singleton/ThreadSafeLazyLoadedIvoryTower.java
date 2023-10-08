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
package com.iluwatar.singleton;

/**
 * <p>Thread-safe Singleton class. The instance is lazily initialized and thus needs synchronization
 * mechanism.</p>
 *
 */
public final class ThreadSafeLazyLoadedIvoryTower {

  /**
   * Singleton instance of the class, declared as volatile to ensure atomic access by multiple threads.
   */
  private static volatile ThreadSafeLazyLoadedIvoryTower instance;

  /**
   * Private constructor to prevent instantiation from outside the class.
   */
  private ThreadSafeLazyLoadedIvoryTower() {
    // Protect against instantiation via reflection
    if (instance != null) {
      throw new IllegalStateException("Already initialized.");
    }
  }

  /**
   * The instance doesn't get created until the method is called for the first time.
   *
   * @return an instance of the class.
   */
  public static synchronized ThreadSafeLazyLoadedIvoryTower getInstance() {
    if (instance == null) {
      instance = new ThreadSafeLazyLoadedIvoryTower();
    }
    return instance;
  }
}
