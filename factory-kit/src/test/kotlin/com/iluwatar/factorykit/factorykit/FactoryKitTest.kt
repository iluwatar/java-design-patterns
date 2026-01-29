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
package com.iluwatar.factorykit.factorykit

// ABOUTME: Tests the factory-kit pattern by verifying each weapon type is correctly instantiated.
// ABOUTME: Validates that WeaponFactory creates the proper concrete Weapon class for each WeaponType.

import com.iluwatar.factorykit.Axe
import com.iluwatar.factorykit.Spear
import com.iluwatar.factorykit.Sword
import com.iluwatar.factorykit.Weapon
import com.iluwatar.factorykit.WeaponFactory
import com.iluwatar.factorykit.WeaponType
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/** Test Factory Kit Pattern */
class FactoryKitTest {

    private lateinit var factory: WeaponFactory

    @BeforeEach
    fun init() {
        factory = WeaponFactory.factory { builder ->
            builder.add(WeaponType.SPEAR, ::Spear)
            builder.add(WeaponType.AXE, ::Axe)
            builder.add(WeaponType.SWORD, ::Sword)
        }
    }

    /**
     * Testing [WeaponFactory] to produce a SPEAR asserting that the Weapon is an instance of
     * [Spear]
     */
    @Test
    fun testSpearWeapon() {
        val weapon = factory.create(WeaponType.SPEAR)
        verifyWeapon(weapon, Spear::class.java)
    }

    /**
     * Testing [WeaponFactory] to produce an AXE asserting that the Weapon is an instance of
     * [Axe]
     */
    @Test
    fun testAxeWeapon() {
        val weapon = factory.create(WeaponType.AXE)
        verifyWeapon(weapon, Axe::class.java)
    }

    /**
     * Testing [WeaponFactory] to produce a SWORD asserting that the Weapon is an instance of
     * [Sword]
     */
    @Test
    fun testWeapon() {
        val weapon = factory.create(WeaponType.SWORD)
        verifyWeapon(weapon, Sword::class.java)
    }

    /**
     * This method asserts that the weapon object that is passed is an instance of the clazz
     *
     * @param weapon weapon object which is to be verified
     * @param clazz expected class of the weapon
     */
    private fun verifyWeapon(weapon: Weapon, clazz: Class<*>) {
        assertTrue(clazz.isInstance(weapon), "Weapon must be an object of: ${clazz.name}")
    }
}
