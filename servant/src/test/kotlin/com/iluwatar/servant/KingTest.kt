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
package com.iluwatar.servant

// ABOUTME: Tests for the King class verifying mood changes under various conditions.
// ABOUTME: Covers all combinations of hungry/fed, sober/drunk, and complimented/not states.

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** KingTest */
class KingTest {

    @Test
    fun testHungrySoberUncomplimentedKing() {
        val king = King()
        king.changeMood()
        assertFalse(king.getMood())
    }

    @Test
    fun testFedSoberUncomplimentedKing() {
        val king = King()
        king.getFed()
        king.changeMood()
        assertFalse(king.getMood())
    }

    @Test
    fun testHungryDrunkUncomplimentedKing() {
        val king = King()
        king.getDrink()
        king.changeMood()
        assertFalse(king.getMood())
    }

    @Test
    fun testHungrySoberComplimentedKing() {
        val king = King()
        king.receiveCompliments()
        king.changeMood()
        assertFalse(king.getMood())
    }

    @Test
    fun testFedDrunkUncomplimentedKing() {
        val king = King()
        king.getFed()
        king.getDrink()
        king.changeMood()
        assertTrue(king.getMood())
    }

    @Test
    fun testFedSoberComplimentedKing() {
        val king = King()
        king.getFed()
        king.receiveCompliments()
        king.changeMood()
        assertFalse(king.getMood())
    }

    @Test
    fun testFedDrunkComplimentedKing() {
        val king = King()
        king.getFed()
        king.getDrink()
        king.receiveCompliments()
        king.changeMood()
        assertFalse(king.getMood())
    }

    @Test
    fun testHungryDrunkComplimentedKing() {
        val king = King()
        king.getDrink()
        king.receiveCompliments()
        king.changeMood()
        assertFalse(king.getMood())
    }
}
