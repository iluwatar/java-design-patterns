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
package com.iluwatar.prototype

// ABOUTME: Parameterized tests verifying that each prototype correctly clones itself.
// ABOUTME: Validates toString, copy identity, class equality, and value equality for all prototypes.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/** PrototypeTest */
class PrototypeTest {

    companion object {
        @JvmStatic
        fun dataProvider(): Collection<Array<Any>> = listOf(
            arrayOf(OrcBeast("axe"), "Orcish wolf attacks with axe"),
            arrayOf(OrcMage("sword"), "Orcish mage attacks with sword"),
            arrayOf(OrcWarlord("laser"), "Orcish warlord attacks with laser"),
            arrayOf(ElfBeast("cooking"), "Elven eagle helps in cooking"),
            arrayOf(ElfMage("cleaning"), "Elven mage helps in cleaning"),
            arrayOf(ElfWarlord("protecting"), "Elven warlord helps in protecting")
        )
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testPrototype(testedPrototype: Prototype, expectedToString: String) {
        assertEquals(expectedToString, testedPrototype.toString())

        val clone = testedPrototype.copy<Prototype>()
        assertNotNull(clone)
        assertNotSame(clone, testedPrototype)
        assertSame(testedPrototype.javaClass, clone.javaClass)
        assertEquals(clone, testedPrototype)
    }
}
