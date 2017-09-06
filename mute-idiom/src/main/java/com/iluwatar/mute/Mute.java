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
package com.iluwatar.mute;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * A utility class that allows you to utilize mute idiom.
 */
public final class Mute {
  
  // The constructor is never meant to be called.
  private Mute() {}

  /**
   * Executes the <code>runnable</code> and throws the exception occurred within a {@link AssertionError}.
   * This method should be utilized to mute the operations that are guaranteed not to throw an exception.
   * For instance {@link ByteArrayOutputStream#write(byte[])} declares in it's signature that it can throw
   * an {@link IOException}, but in reality it cannot. This is because the bulk write method is not overridden
   * in {@link ByteArrayOutputStream}.
   * 
   * @param runnable a runnable that should never throw an exception on execution.
   */
  public static void mute(CheckedRunnable runnable) {
    try {
      runnable.run();
    } catch (Exception e) {
      throw new AssertionError(e);
    }
  }

  /**
   * Executes the <code>runnable</code> and logs the exception occurred on {@link System#err}.
   * This method should be utilized to mute the operations about which most you can do is log.
   * For instance while closing a connection to database, or cleaning up a resource, 
   * all you can do is log the exception occurred.
   * 
   * @param runnable a runnable that may throw an exception on execution.
   */
  public static void loggedMute(CheckedRunnable runnable) {
    try {
      runnable.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
