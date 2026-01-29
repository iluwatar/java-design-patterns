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
package com.iluwatar.bridge

// ABOUTME: Abstract base class for weapon tests providing shared verification logic.
// ABOUTME: Uses MockK to verify that weapon actions correctly delegate to the enchantment.

import io.mockk.confirmVerified
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull

/** Base class for weapon tests */
abstract class WeaponTest {
    /**
     * Invoke the basic actions of the given weapon, and test if the underlying enchantment
     * implementation is invoked
     */
    fun testBasicWeaponActions(weapon: Weapon) {
        assertNotNull(weapon)
        val enchantment = weapon.enchantment
        assertNotNull(enchantment)
        assertNotNull(weapon.enchantment)

        weapon.swing()
        verify { enchantment.apply() }
        confirmVerified(enchantment)

        weapon.wield()
        verify { enchantment.onActivate() }
        confirmVerified(enchantment)

        weapon.unwield()
        verify { enchantment.onDeactivate() }
        confirmVerified(enchantment)
    }
}