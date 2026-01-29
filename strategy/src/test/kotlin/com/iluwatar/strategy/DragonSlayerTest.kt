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
package com.iluwatar.strategy

// ABOUTME: Tests for DragonSlayer context class verifying strategy delegation.
// ABOUTME: Uses MockK to verify execute() is called and strategy changes work correctly.

import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/** DragonSlayerTest */
class DragonSlayerTest {

    /** Verify if the dragon slayer uses the strategy during battle. */
    @Test
    fun testGoToBattle() {
        val strategy = mockk<DragonSlayingStrategy>(relaxed = true)
        val dragonSlayer = DragonSlayer(strategy)

        dragonSlayer.goToBattle()
        verify { strategy.execute() }
        confirmVerified(strategy)
    }

    /** Verify if the dragon slayer uses the new strategy during battle after a change of strategy. */
    @Test
    fun testChangeStrategy() {
        val initialStrategy = mockk<DragonSlayingStrategy>(relaxed = true)
        val dragonSlayer = DragonSlayer(initialStrategy)

        dragonSlayer.goToBattle()
        verify { initialStrategy.execute() }

        val newStrategy = mockk<DragonSlayingStrategy>(relaxed = true)
        dragonSlayer.changeStrategy(newStrategy)

        dragonSlayer.goToBattle()
        verify { newStrategy.execute() }

        confirmVerified(initialStrategy, newStrategy)
    }
}
