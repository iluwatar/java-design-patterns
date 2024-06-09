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
package com.iluwatar.resource.acquisition.is.initialization;

import lombok.extern.slf4j.Slf4j;

/**
 * Resource Acquisition Is Initialization pattern was developed for exception safe resource
 * management by C++ creator Bjarne Stroustrup.
 *
 * <p>In RAII resource is tied to object lifetime: resource allocation is done during object
 * creation while resource deallocation is done during object destruction.
 *
 * <p>In Java RAII is achieved with try-with-resources statement and interfaces {@link
 * java.io.Closeable} and {@link AutoCloseable}. The try-with-resources statement ensures that each
 * resource is closed at the end of the statement. Any object that implements {@link
 * java.lang.AutoCloseable}, which includes all objects which implement {@link java.io.Closeable},
 * can be used as a resource.
 *
 * <p>In this example, {@link SlidingDoor} implements {@link AutoCloseable} and {@link
 * TreasureChest} implements {@link java.io.Closeable}. Running the example, we can observe that
 * both resources are automatically closed.
 *
 * <p>http://docs.oracle.com/javase/7/docs/technotes/guides/language/try-with-resources.html
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   */
  public static void main(String[] args) {

    try (var ignored = new SlidingDoor()) {
      LOGGER.info("Walking in.");
    }

    try (var ignored = new TreasureChest()) {
      LOGGER.info("Looting contents.");
    }
  }
}
