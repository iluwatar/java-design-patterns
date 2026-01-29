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

// ABOUTME: Tests for Elf, Orc, and Human creature implementations.
// ABOUTME: Verifies each creature type initializes with correct stats from CreatureStats.
package com.iluwatar.lockableobject

import com.iluwatar.lockableobject.domain.CreatureStats
import com.iluwatar.lockableobject.domain.Elf
import com.iluwatar.lockableobject.domain.Human
import com.iluwatar.lockableobject.domain.Orc
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SubCreaturesTests {

    @Test
    fun statsTest() {
        val elf = Elf("Limbar")
        val orc = Orc("Dargal")
        val human = Human("Jerry")
        assertEquals(CreatureStats.ELF_HEALTH.value, elf.health)
        assertEquals(CreatureStats.ELF_DAMAGE.value, elf.damage)
        assertEquals(CreatureStats.ORC_DAMAGE.value, orc.damage)
        assertEquals(CreatureStats.ORC_HEALTH.value, orc.health)
        assertEquals(CreatureStats.HUMAN_DAMAGE.value, human.damage)
        assertEquals(CreatureStats.HUMAN_HEALTH.value, human.health)
    }
}
