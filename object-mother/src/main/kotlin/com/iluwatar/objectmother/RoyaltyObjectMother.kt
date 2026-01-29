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

// ABOUTME: Object Mother pattern implementation for generating Royalty types.
// ABOUTME: Provides factory methods to create pre-configured King and Queen instances for testing.

/** Object Mother Pattern generating Royalty Types. */
object RoyaltyObjectMother {

    /**
     * Method to create a sober and unhappy king. The standard parameters are set.
     *
     * @return An instance of [King] with the standard properties.
     */
    fun createSoberUnhappyKing(): King = King()

    /**
     * Method of the object mother to create a drunk king.
     *
     * @return A drunk [King].
     */
    fun createDrunkKing(): King = King().apply { makeDrunk() }

    /**
     * Method to create a happy king.
     *
     * @return A happy [King].
     */
    fun createHappyKing(): King = King().apply { makeHappy() }

    /**
     * Method to create a happy and drunk king.
     *
     * @return A drunk and happy [King].
     */
    fun createHappyDrunkKing(): King = King().apply {
        makeHappy()
        makeDrunk()
    }

    /**
     * Method to create a flirty queen.
     *
     * @return A flirty [Queen].
     */
    fun createFlirtyQueen(): Queen = Queen().apply { isFlirty = true }

    /**
     * Method to create a not flirty queen.
     *
     * @return A not flirty [Queen].
     */
    fun createNotFlirtyQueen(): Queen = Queen()
}
