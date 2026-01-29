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
package com.iluwatar.objectmother

// ABOUTME: Tests for the Object Mother pattern generating Royalty types.
// ABOUTME: Validates King flirting behavior and correct type generation from the object mother.

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/** Test Generation of Royalty Types using the object-mother */
class RoyaltyObjectMotherTest {

    @Test
    fun unsuccessfulKingFlirt() {
        val soberUnhappyKing = RoyaltyObjectMother.createSoberUnhappyKing()
        val flirtyQueen = RoyaltyObjectMother.createFlirtyQueen()
        soberUnhappyKing.flirt(flirtyQueen)
        assertFalse(soberUnhappyKing.isHappy())
    }

    @Test
    fun queenIsBlockingFlirtCauseDrunkKing() {
        val drunkUnhappyKing = RoyaltyObjectMother.createDrunkKing()
        val notFlirtyQueen = RoyaltyObjectMother.createNotFlirtyQueen()
        drunkUnhappyKing.flirt(notFlirtyQueen)
        assertFalse(drunkUnhappyKing.isHappy())
    }

    @Test
    fun queenIsBlockingFlirt() {
        val soberHappyKing = RoyaltyObjectMother.createHappyKing()
        val notFlirtyQueen = RoyaltyObjectMother.createNotFlirtyQueen()
        soberHappyKing.flirt(notFlirtyQueen)
        assertFalse(soberHappyKing.isHappy())
    }

    @Test
    fun successfulKingFlirt() {
        val soberHappyKing = RoyaltyObjectMother.createHappyKing()
        val flirtyQueen = RoyaltyObjectMother.createFlirtyQueen()
        soberHappyKing.flirt(flirtyQueen)
        assertTrue(soberHappyKing.isHappy())
    }

    @Test
    fun testQueenType() {
        val flirtyQueen = RoyaltyObjectMother.createFlirtyQueen()
        val notFlirtyQueen = RoyaltyObjectMother.createNotFlirtyQueen()
        assertEquals(flirtyQueen::class, Queen::class)
        assertEquals(notFlirtyQueen::class, Queen::class)
    }

    @Test
    fun testKingType() {
        val drunkKing = RoyaltyObjectMother.createDrunkKing()
        val happyDrunkKing = RoyaltyObjectMother.createHappyDrunkKing()
        val happyKing = RoyaltyObjectMother.createHappyKing()
        val soberUnhappyKing = RoyaltyObjectMother.createSoberUnhappyKing()
        assertEquals(drunkKing::class, King::class)
        assertEquals(happyDrunkKing::class, King::class)
        assertEquals(happyKing::class, King::class)
        assertEquals(soberUnhappyKing::class, King::class)
    }
}
