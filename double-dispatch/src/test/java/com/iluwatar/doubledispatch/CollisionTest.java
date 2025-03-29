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
package com.iluwatar.doubledispatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;

/**
 * CollisionTest
 *
 * @param <O> Type of GameObject
 */
public abstract class CollisionTest<O extends GameObject> {

  /**
   * Get the tested object
   *
   * @return The tested object, should never return 'null'
   */
  abstract O getTestedObject();

  /**
   * Collide the tested item with the other given item and verify if the damage and fire state is as
   * expected
   *
   * @param other The other object we have to collide with
   * @param otherDamaged Indicates if the other object should be damaged after the collision
   * @param otherOnFire Indicates if the other object should be burning after the collision
   * @param thisDamaged Indicates if the test object should be damaged after the collision
   * @param thisOnFire Indicates if the other object should be burning after the collision
   */
  void testCollision(
      final GameObject other,
      final boolean otherDamaged,
      final boolean otherOnFire,
      final boolean thisDamaged,
      final boolean thisOnFire) {

    Objects.requireNonNull(other);
    Objects.requireNonNull(getTestedObject());

    final var tested = getTestedObject();

    tested.collision(other);

    testOnFire(other, tested, otherOnFire);
    testDamaged(other, tested, otherDamaged);

    testOnFire(tested, other, thisOnFire);
    testDamaged(tested, other, thisDamaged);
  }

  /**
   * Test if the fire state of the target matches the expected state after colliding with the given
   * object
   *
   * @param target The target object
   * @param other The other object
   * @param expectTargetOnFire The expected state of fire on the target object
   */
  private void testOnFire(
      final GameObject target, final GameObject other, final boolean expectTargetOnFire) {
    final var targetName = target.getClass().getSimpleName();
    final var otherName = other.getClass().getSimpleName();

    final var errorMessage =
        expectTargetOnFire
            ? "Expected ["
                + targetName
                + "] to be on fire after colliding with ["
                + otherName
                + "] but it was not!"
            : "Expected ["
                + targetName
                + "] not to be on fire after colliding with ["
                + otherName
                + "] but it was!";

    assertEquals(expectTargetOnFire, target.isOnFire(), errorMessage);
  }

  /**
   * Test if the damage state of the target matches the expected state after colliding with the
   * given object
   *
   * @param target The target object
   * @param other The other object
   * @param expectedDamage The expected state of damage on the target object
   */
  private void testDamaged(
      final GameObject target, final GameObject other, final boolean expectedDamage) {
    final var targetName = target.getClass().getSimpleName();
    final var otherName = other.getClass().getSimpleName();

    final var errorMessage =
        expectedDamage
            ? "Expected ["
                + targetName
                + "] to be damaged after colliding with ["
                + otherName
                + "] but it was not!"
            : "Expected ["
                + targetName
                + "] not to be damaged after colliding with ["
                + otherName
                + "] but it was!";

    assertEquals(expectedDamage, target.isDamaged(), errorMessage);
  }
}
