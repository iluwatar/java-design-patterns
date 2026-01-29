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

// ABOUTME: Tests for PropertiesFeatureToggleVersion functionality.
// ABOUTME: Verifies feature toggle behavior based on property configuration.
package com.iluwatar.featuretoggle.pattern.propertiesversion

import com.iluwatar.featuretoggle.user.User
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.Properties

/** Test Properties Toggle */
class PropertiesFeatureToggleVersionTest {

    @Test
    fun testNullPropertiesPassed() {
        assertThrows(IllegalArgumentException::class.java) { PropertiesFeatureToggleVersion(null) }
    }

    @Test
    fun testNonBooleanProperty() {
        assertThrows(IllegalArgumentException::class.java) {
            val properties = Properties()
            properties.setProperty("enhancedWelcome", "Something")
            PropertiesFeatureToggleVersion(properties)
        }
    }

    @Test
    fun testFeatureTurnedOn() {
        val properties = Properties()
        properties["enhancedWelcome"] = true
        val service = PropertiesFeatureToggleVersion(properties)
        assertTrue(service.isEnhanced)
        val welcomeMessage = service.getWelcomeMessage(User("Jamie No Code"))
        assertEquals(
            "Welcome Jamie No Code. You're using the enhanced welcome message.",
            welcomeMessage
        )
    }

    @Test
    fun testFeatureTurnedOff() {
        val properties = Properties()
        properties["enhancedWelcome"] = false
        val service = PropertiesFeatureToggleVersion(properties)
        assertFalse(service.isEnhanced)
        val welcomeMessage = service.getWelcomeMessage(User("Jamie No Code"))
        assertEquals("Welcome to the application.", welcomeMessage)
    }
}
