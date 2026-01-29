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

// ABOUTME: Tests for Feind class that manages creature competition for lockable objects.
// ABOUTME: Verifies threading behavior and lock acquisition/release cycle.
package com.iluwatar.lockableobject

import com.iluwatar.lockableobject.domain.Creature
import com.iluwatar.lockableobject.domain.Elf
import com.iluwatar.lockableobject.domain.Feind
import com.iluwatar.lockableobject.domain.Orc
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FeindTest {

    private lateinit var elf: Creature
    private lateinit var orc: Creature
    private lateinit var sword: Lockable

    @BeforeEach
    fun init() {
        elf = Elf("Nagdil")
        orc = Orc("Ghandar")
        sword = SwordOfAragorn()
    }

    @Test
    fun testBaseCase() {
        val base = Thread(Feind(orc, sword))
        assertNull(sword.getLocker())
        base.start()
        base.join()
        assertEquals(orc, sword.getLocker())
        val extend = Thread(Feind(elf, sword))
        extend.start()
        extend.join()
        assertTrue(sword.isLocked())

        sword.unlock(if (elf.isAlive()) elf else orc)
        assertNull(sword.getLocker())
    }
}
