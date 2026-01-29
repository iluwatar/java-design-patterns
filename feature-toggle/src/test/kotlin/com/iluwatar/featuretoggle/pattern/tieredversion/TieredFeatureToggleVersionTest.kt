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

// ABOUTME: Tests for TieredFeatureToggleVersion functionality.
// ABOUTME: Verifies that paid users get enhanced messages while free users get generic ones.
package com.iluwatar.featuretoggle.pattern.tieredversion

import com.iluwatar.featuretoggle.pattern.Service
import com.iluwatar.featuretoggle.user.User
import com.iluwatar.featuretoggle.user.UserGroup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Test Tiered Feature Toggle */
class TieredFeatureToggleVersionTest {

    private val paidUser = User("Jamie Coder")
    private val freeUser = User("Alan Defect")
    private val service: Service = TieredFeatureToggleVersion()

    @BeforeEach
    fun setUp() {
        UserGroup.addUserToPaidGroup(paidUser)
        UserGroup.addUserToFreeGroup(freeUser)
    }

    @Test
    fun testGetWelcomeMessageForPaidUser() {
        val welcomeMessage = service.getWelcomeMessage(paidUser)
        val expected = "You're amazing Jamie Coder. Thanks for paying for this awesome software."
        assertEquals(expected, welcomeMessage)
    }

    @Test
    fun testGetWelcomeMessageForFreeUser() {
        val welcomeMessage = service.getWelcomeMessage(freeUser)
        val expected = "I suppose you can use this software."
        assertEquals(expected, welcomeMessage)
    }

    @Test
    fun testIsEnhancedAlwaysTrueAsTiered() {
        assertTrue(service.isEnhanced)
    }
}
