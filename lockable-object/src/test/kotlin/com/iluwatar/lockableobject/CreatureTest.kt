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

// ABOUTME: Tests for Creature class functionality including combat and locking behavior.
// ABOUTME: Verifies hit, kill, attack, acquire operations and input validation.
package com.iluwatar.lockableobject

import com.iluwatar.lockableobject.domain.Creature
import com.iluwatar.lockableobject.domain.CreatureStats
import com.iluwatar.lockableobject.domain.CreatureType
import com.iluwatar.lockableobject.domain.Elf
import com.iluwatar.lockableobject.domain.Orc
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreatureTest {

    private lateinit var orc: Creature
    private lateinit var elf: Creature
    private lateinit var sword: Lockable

    @BeforeEach
    fun init() {
        elf = Elf("Elf test")
        orc = Orc("Orc test")
        sword = SwordOfAragorn()
    }

    @Test
    fun baseTest() {
        assertEquals("Elf test", elf.name)
        assertEquals(CreatureType.ELF, elf.type)
        assertThrows(IllegalArgumentException::class.java) { elf.hit(-10) }
    }

    @Test
    fun hitTest() {
        elf.hit(CreatureStats.ELF_HEALTH.value / 2)
        assertEquals(CreatureStats.ELF_HEALTH.value / 2, elf.health)
        elf.hit(CreatureStats.ELF_HEALTH.value / 2)
        assertFalse(elf.isAlive())

        assertEquals(0, orc.instruments.size)
        assertTrue(orc.acquire(sword))
        assertEquals(1, orc.instruments.size)
        orc.kill()
        assertEquals(0, orc.instruments.size)
    }

    @Test
    fun testFight() {
        killCreature(elf, orc)
        assertTrue(elf.isAlive())
        assertFalse(orc.isAlive())
        assertTrue(elf.health > 0)
        assertTrue(orc.health <= 0)
    }

    @Test
    fun testAcqusition() {
        assertTrue(elf.acquire(sword))
        assertEquals(elf.name, sword.getLocker()?.name)
        assertTrue(elf.instruments.contains(sword))
        assertFalse(orc.acquire(sword))
        killCreature(orc, elf)
        assertTrue(orc.acquire(sword))
        assertEquals(orc, sword.getLocker())
    }

    private fun killCreature(source: Creature, target: Creature) {
        while (target.isAlive()) {
            source.attack(target)
        }
    }

    @Test
    fun invalidDamageTest() {
        assertThrows(IllegalArgumentException::class.java) { elf.hit(-50) }
    }
}
