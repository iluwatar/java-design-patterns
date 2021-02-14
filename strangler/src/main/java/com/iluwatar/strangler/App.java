/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.strangler;

/**
 *
 * <p>The Strangler pattern is a software design pattern that incrementally migrate a legacy
 * system by gradually replacing specific pieces of functionality with new applications and
 * services. As features from the legacy system are replaced, the new system eventually
 * replaces all of the old system's features, strangling the old system and allowing you
 * to decommission it.</p>
 *
 * <p>This pattern is not only about updating but also enhancement.</p>
 *
 * <p>In this example, {@link OldArithmetic} indicates old system and its implementation depends
 * on its source ({@link OldSource}). Now we tend to update system with new techniques and
 * new features. In reality, the system may too complex, so usually need gradual migration.
 * {@link HalfArithmetic} indicates system in the process of migration, its implementation
 * depends on old one ({@link OldSource}) and under development one ({@link HalfSource}). The
 * {@link HalfSource} covers part of {@link OldSource} and add new functionality. You can release
 * this version system with new features, which also supports old version system functionalities.
 * After whole migration, the new system ({@link NewArithmetic}) only depends on new source
 * ({@link NewSource}).</p>
 *
 */
public class App {
  /**
   * Program entry point.
   * @param args command line args
   */
  public static void main(final String[] args) {
    final var nums = new int[]{1, 2, 3, 4, 5};
    //Before migration
    final var oldSystem = new OldArithmetic(new OldSource());
    oldSystem.sum(nums);
    oldSystem.mul(nums);
    //In process of migration
    final var halfSystem = new HalfArithmetic(new HalfSource(), new OldSource());
    halfSystem.sum(nums);
    halfSystem.mul(nums);
    halfSystem.ifHasZero(nums);
    //After migration
    final var newSystem = new NewArithmetic(new NewSource());
    newSystem.sum(nums);
    newSystem.mul(nums);
    newSystem.ifHasZero(nums);
  }
}
