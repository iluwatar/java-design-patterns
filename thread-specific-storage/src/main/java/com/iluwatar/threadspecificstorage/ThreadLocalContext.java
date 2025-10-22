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
package com.iluwatar.threadspecificstorage;

/**
 * ThreadLocalContext provides a thread-specific storage mechanism. Each thread will have its own
 * instance of user and luggage count, preventing any data sharing across threads.
 */
public class ThreadLocalContext {

  private static final ThreadLocal<String> user = ThreadLocal.withInitial(() -> "Unknown");
  private static final ThreadLocal<Integer> luggageCount = ThreadLocal.withInitial(() -> 0);

  private ThreadLocalContext() {}

  public static void setUser(String name) {
    user.set(name);
  }

  public static String getUser() {
    return user.get();
  }

  public static void setLuggageCount(int count) {
    luggageCount.set(count);
  }

  public static int getLuggageCount() {
    return luggageCount.get();
  }

  /** Clears thread-local data to prevent memory leaks. */
  public static void clear() {
    user.remove();
    luggageCount.remove();
  }
}
