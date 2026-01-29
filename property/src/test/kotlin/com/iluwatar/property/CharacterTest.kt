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
package com.iluwatar.property

// ABOUTME: Tests the Character class prototype inheritance behavior for stats, names, and types.
// ABOUTME: Verifies property delegation, stat removal, toString formatting, and prototype chaining.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** CharacterTest */
class CharacterTest {

    @Test
    fun testPrototypeStats() {
        val prototype = Character()

        for (stat in Stats.entries) {
            assertFalse(prototype.has(stat))
            assertNull(prototype.get(stat))

            val expectedValue = stat.ordinal
            prototype.set(stat, expectedValue)
            assertTrue(prototype.has(stat))
            assertEquals(expectedValue, prototype.get(stat))

            prototype.remove(stat)
            assertFalse(prototype.has(stat))
            assertNull(prototype.get(stat))
        }
    }

    @Test
    fun testCharacterStats() {
        val prototype = Character()
        Stats.entries.forEach { stat -> prototype.set(stat, stat.ordinal) }

        val mage = Character(Character.Type.MAGE, prototype)
        for (stat in Stats.entries) {
            val expectedValue = stat.ordinal
            assertTrue(mage.has(stat))
            assertEquals(expectedValue, mage.get(stat))
        }
    }

    @Test
    fun testToString() {
        val prototype = Character()
        prototype.set(Stats.ARMOR, 1)
        prototype.set(Stats.AGILITY, 2)
        prototype.set(Stats.INTELLECT, 3)
        val message =
            "Stats:\n" +
            " - AGILITY:2\n" +
            " - ARMOR:1\n" +
            " - INTELLECT:3\n"
        assertEquals(message, prototype.toString())

        val stupid = Character(Character.Type.ROGUE, prototype)
        stupid.remove(Stats.INTELLECT)
        val expectedStupidString =
            "Character type: ROGUE\n" +
            "Stats:\n" +
            " - AGILITY:2\n" +
            " - ARMOR:1\n"
        assertEquals(expectedStupidString, stupid.toString())

        val weak = Character("weak", prototype)
        weak.remove(Stats.ARMOR)
        val expectedWeakString =
            "Player: weak\n" +
            "Stats:\n" +
            " - AGILITY:2\n" +
            " - INTELLECT:3\n"
        assertEquals(expectedWeakString, weak.toString())
    }

    @Test
    fun testName() {
        val prototype = Character()
        prototype.set(Stats.ARMOR, 1)
        prototype.set(Stats.INTELLECT, 2)
        assertNull(prototype.name)

        val stupid = Character(Character.Type.ROGUE, prototype)
        stupid.remove(Stats.INTELLECT)
        assertNull(stupid.name)

        val weak = Character("weak", prototype)
        weak.remove(Stats.ARMOR)
        assertEquals("weak", weak.name)
    }

    @Test
    fun testType() {
        val prototype = Character()
        prototype.set(Stats.ARMOR, 1)
        prototype.set(Stats.INTELLECT, 2)
        assertNull(prototype.type)

        val stupid = Character(Character.Type.ROGUE, prototype)
        stupid.remove(Stats.INTELLECT)
        assertEquals(Character.Type.ROGUE, stupid.type)

        val weak = Character("weak", prototype)
        weak.remove(Stats.ARMOR)
        assertNull(weak.type)
    }
}
