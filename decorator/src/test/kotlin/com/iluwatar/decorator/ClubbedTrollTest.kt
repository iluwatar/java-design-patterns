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
package com.iluwatar.decorator

// ABOUTME: Tests for ClubbedTroll verifying decoration behavior and delegation.
// ABOUTME: Uses MockK spyk to verify that decorated troll methods are properly called.

import io.mockk.confirmVerified
import io.mockk.spyk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** Tests for [ClubbedTroll] */
class ClubbedTrollTest {

    @Test
    fun testClubbedTroll() {
        // Create a normal troll first, but make sure we can spy on it later on.
        val simpleTroll = spyk(SimpleTroll())

        // Now we want to decorate the troll to make it stronger ...
        val clubbed = ClubbedTroll(simpleTroll)
        assertEquals(20, clubbed.getAttackPower())
        verify(exactly = 1) { simpleTroll.getAttackPower() }

        // Check if the clubbed troll actions are delegated to the decorated troll
        clubbed.attack()
        verify(exactly = 1) { simpleTroll.attack() }

        clubbed.fleeBattle()
        verify(exactly = 1) { simpleTroll.fleeBattle() }
        confirmVerified(simpleTroll)
    }
}
