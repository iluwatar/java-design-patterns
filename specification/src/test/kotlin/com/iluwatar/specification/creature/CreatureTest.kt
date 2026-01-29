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

// ABOUTME: Parameterized tests for all Creature implementations.
// ABOUTME: Verifies name, size, movement, color, mass, and toString for each creature type.
package com.iluwatar.specification.creature

import com.iluwatar.specification.property.Color
import com.iluwatar.specification.property.Mass
import com.iluwatar.specification.property.Movement
import com.iluwatar.specification.property.Size
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/** CreatureTest */
class CreatureTest {

    companion object {
        /**
         * @return The tested [Creature] instance and its expected specs
         */
        @JvmStatic
        fun dataProvider(): Collection<Array<Any>> = listOf(
            arrayOf(Dragon(), "Dragon", Size.LARGE, Movement.FLYING, Color.RED, Mass(39300.0)),
            arrayOf(Goblin(), "Goblin", Size.SMALL, Movement.WALKING, Color.GREEN, Mass(30.0)),
            arrayOf(KillerBee(), "KillerBee", Size.SMALL, Movement.FLYING, Color.LIGHT, Mass(6.7)),
            arrayOf(Octopus(), "Octopus", Size.NORMAL, Movement.SWIMMING, Color.DARK, Mass(12.0)),
            arrayOf(Shark(), "Shark", Size.NORMAL, Movement.SWIMMING, Color.LIGHT, Mass(500.0)),
            arrayOf(Troll(), "Troll", Size.LARGE, Movement.WALKING, Color.DARK, Mass(4000.0))
        )
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testGetName(testedCreature: Creature, name: String) {
        assertEquals(name, testedCreature.name)
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testGetSize(testedCreature: Creature, name: String, size: Size) {
        assertEquals(size, testedCreature.size)
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testGetMovement(testedCreature: Creature, name: String, size: Size, movement: Movement) {
        assertEquals(movement, testedCreature.movement)
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testGetColor(
        testedCreature: Creature,
        name: String,
        size: Size,
        movement: Movement,
        color: Color
    ) {
        assertEquals(color, testedCreature.color)
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testGetMass(
        testedCreature: Creature,
        name: String,
        size: Size,
        movement: Movement,
        color: Color,
        mass: Mass
    ) {
        assertEquals(mass, testedCreature.mass)
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testToString(
        testedCreature: Creature,
        name: String,
        size: Size,
        movement: Movement,
        color: Color,
        mass: Mass
    ) {
        val toString = testedCreature.toString()
        assertNotNull(toString)
        assertEquals(
            "$name [size=$size, movement=$movement, color=$color, mass=$mass]",
            toString
        )
    }
}
