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
package com.iluwatar.immutable;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Immutable pattern ensures that an object's state cannot be changed after construction.
 *
 * <p>In this example, {@link ImmutableUser} demonstrates the pattern: all fields are final, the
 * mutable {@code roles} list is defensively copied, and any state change produces a brand-new
 * instance via {@link ImmutableUser#withAge(int)}.
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    var alice = new ImmutableUser("Alice", 30, List.of("admin", "user"));
    LOGGER.info("Original user: {}", alice);

    var olderAlice = alice.withAge(31);
    LOGGER.info("Updated user (new object): {}", olderAlice);
    LOGGER.info("Original is unchanged: {}", alice);

    // Demonstrate defensive copy: mutating the source list does not affect alice
    var mutableRoles = new java.util.ArrayList<>(List.of("viewer"));
    var bob = new ImmutableUser("Bob", 25, mutableRoles);
    mutableRoles.add("editor");
    LOGGER.info("Bob's roles (unchanged despite external list mutation): {}", bob.getRoles());
  }
}
