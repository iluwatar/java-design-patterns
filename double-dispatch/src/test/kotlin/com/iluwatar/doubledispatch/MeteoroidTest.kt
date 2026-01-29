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

// ABOUTME: Unit tests for Meteoroid verifying construction and collision outcomes.
// ABOUTME: Validates that meteoroids do not take damage or catch fire from any collision.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

/** MeteoroidTest */
class MeteoroidTest : CollisionTest<Meteoroid>() {

    override fun getTestedObject(): Meteoroid = Meteoroid(1, 2, 3, 4)

    /** Test the constructor parameters */
    @Test
    fun testConstructor() {
        val meteoroid = Meteoroid(1, 2, 3, 4)
        assertEquals(1, meteoroid.left)
        assertEquals(2, meteoroid.top)
        assertEquals(3, meteoroid.right)
        assertEquals(4, meteoroid.bottom)
        assertFalse(meteoroid.isOnFire)
        assertFalse(meteoroid.isDamaged)
        assertEquals("Meteoroid at [1,2,3,4] damaged=false onFire=false", meteoroid.toString())
    }

    /** Test what happens we collide with an asteroid */
    @Test
    fun testCollideFlamingAsteroid() {
        testCollision(FlamingAsteroid(1, 1, 3, 4), false, true, false, false)
    }

    /** Test what happens we collide with a meteoroid */
    @Test
    fun testCollideMeteoroid() {
        testCollision(Meteoroid(1, 1, 3, 4), false, false, false, false)
    }

    /** Test what happens we collide with ISS */
    @Test
    fun testCollideSpaceStationIss() {
        testCollision(SpaceStationIss(1, 1, 3, 4), true, false, false, false)
    }

    /** Test what happens we collide with MIR */
    @Test
    fun testCollideSpaceStationMir() {
        testCollision(SpaceStationMir(1, 1, 3, 4), true, false, false, false)
    }
}
