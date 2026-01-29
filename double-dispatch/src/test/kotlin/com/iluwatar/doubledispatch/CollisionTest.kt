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
package com.iluwatar.doubledispatch

// ABOUTME: Abstract base test class for collision-based test scenarios.
// ABOUTME: Provides reusable collision verification logic for damage and fire state assertions.

import org.junit.jupiter.api.Assertions.assertEquals

/**
 * CollisionTest
 *
 * @param O Type of GameObject
 */
abstract class CollisionTest<O : GameObject> {

    /**
     * Get the tested object
     *
     * @return The tested object, should never return null
     */
    abstract fun getTestedObject(): O

    /**
     * Collide the tested item with the other given item and verify if the damage and fire state is
     * as expected
     *
     * @param other The other object we have to collide with
     * @param otherDamaged Indicates if the other object should be damaged after the collision
     * @param otherOnFire Indicates if the other object should be burning after the collision
     * @param thisDamaged Indicates if the test object should be damaged after the collision
     * @param thisOnFire Indicates if the other object should be burning after the collision
     */
    fun testCollision(
        other: GameObject,
        otherDamaged: Boolean,
        otherOnFire: Boolean,
        thisDamaged: Boolean,
        thisOnFire: Boolean
    ) {
        requireNotNull(other)
        requireNotNull(getTestedObject())

        val tested = getTestedObject()

        tested.collision(other)

        testOnFire(other, tested, otherOnFire)
        testDamaged(other, tested, otherDamaged)

        testOnFire(tested, other, thisOnFire)
        testDamaged(tested, other, thisDamaged)
    }

    /**
     * Test if the fire state of the target matches the expected state after colliding with the
     * given object
     *
     * @param target The target object
     * @param other The other object
     * @param expectTargetOnFire The expected state of fire on the target object
     */
    private fun testOnFire(
        target: GameObject,
        other: GameObject,
        expectTargetOnFire: Boolean
    ) {
        val targetName = target::class.simpleName
        val otherName = other::class.simpleName

        val errorMessage = if (expectTargetOnFire) {
            "Expected [$targetName] to be on fire after colliding with [$otherName] but it was not!"
        } else {
            "Expected [$targetName] not to be on fire after colliding with [$otherName] but it was!"
        }

        assertEquals(expectTargetOnFire, target.isOnFire, errorMessage)
    }

    /**
     * Test if the damage state of the target matches the expected state after colliding with the
     * given object
     *
     * @param target The target object
     * @param other The other object
     * @param expectedDamage The expected state of damage on the target object
     */
    private fun testDamaged(
        target: GameObject,
        other: GameObject,
        expectedDamage: Boolean
    ) {
        val targetName = target::class.simpleName
        val otherName = other::class.simpleName

        val errorMessage = if (expectedDamage) {
            "Expected [$targetName] to be damaged after colliding with [$otherName] but it was not!"
        } else {
            "Expected [$targetName] not to be damaged after colliding with [$otherName] but it was!"
        }

        assertEquals(expectedDamage, target.isDamaged, errorMessage)
    }
}
