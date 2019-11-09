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

package com.iluwatar.lazy.loading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lazy loading idiom defers object creation until needed.
 *
 * <p>This example shows different implementations of the pattern with increasing sophistication.
 *
 * <p>Additional information and lazy loading flavours are described in
 * http://martinfowler.com/eaaCatalog/lazyLoad.html
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    // Simple lazy loader - not thread safe
    HolderNaive holderNaive = new HolderNaive();
    Heavy heavy = holderNaive.getHeavy();
    LOGGER.info("heavy={}", heavy);

    // Thread safe lazy loader, but with heavy synchronization on each access
    HolderThreadSafe holderThreadSafe = new HolderThreadSafe();
    Heavy another = holderThreadSafe.getHeavy();
    LOGGER.info("another={}", another);

    // The most efficient lazy loader utilizing Java 8 features
    Java8Holder java8Holder = new Java8Holder();
    Heavy next = java8Holder.getHeavy();
    LOGGER.info("next={}", next);
  }
}
