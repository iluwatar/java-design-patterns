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
package com.iluwatar.filterer.threat

// ABOUTME: Tests filtering of SimpleProbabilisticThreatAwareSystem by probability.
// ABOUTME: Verifies that threats can be filtered based on their probability of occurrence.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SimpleProbabilisticThreatAwareSystemTest {

    @Test
    fun shouldFilterByProbability() {
        // given
        val trojan = SimpleProbableThreat("Troyan-ArcBomb", 1, ThreatType.TROJAN, 0.99)
        val rootkit = SimpleProbableThreat("Rootkit-System", 2, ThreatType.ROOTKIT, 0.8)
        val probableThreats = listOf(trojan, rootkit)

        val simpleProbabilisticThreatAwareSystem =
            SimpleProbabilisticThreatAwareSystem("System-1", probableThreats)

        // when
        val filtered =
            simpleProbabilisticThreatAwareSystem
                .filtered()
                .by { probableThreat -> probableThreat.probability().compareTo(0.99) == 0 }

        // then
        assertEquals(1, filtered.threats().size)
        assertEquals(trojan, filtered.threats()[0])
    }
}
