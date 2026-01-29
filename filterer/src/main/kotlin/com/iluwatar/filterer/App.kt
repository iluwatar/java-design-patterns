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
package com.iluwatar.filterer

// ABOUTME: Entry point demonstrating the Filterer pattern for filtering container-like objects.
// ABOUTME: Shows filtering of ThreatAwareSystem and ProbabilisticThreatAwareSystem by predicates.

import com.iluwatar.filterer.threat.SimpleProbabilisticThreatAwareSystem
import com.iluwatar.filterer.threat.SimpleProbableThreat
import com.iluwatar.filterer.threat.SimpleThreat
import com.iluwatar.filterer.threat.SimpleThreatAwareSystem
import com.iluwatar.filterer.threat.ThreatType
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * This demo class represents how the [com.iluwatar.filterer.domain.Filterer] pattern is used to
 * filter container-like objects to return filtered versions of themselves. The container-like
 * objects are systems that are aware of threats that they can be vulnerable to. We would like to
 * have a way to create a copy of different system objects but with filtered threats. The key is to
 * keep it simple: if we add a new subtype of Threat (for example ProbableThreat), we still need
 * to be able to filter by its properties.
 */
fun main() {
    filteringSimpleThreats()
    filteringSimpleProbableThreats()
}

/**
 * Demonstrates how to filter [SimpleThreatAwareSystem] based on threat type.
 * The [com.iluwatar.filterer.domain.Filterer.by] method accepts a predicate on [com.iluwatar.filterer.threat.Threat].
 */
private fun filteringSimpleThreats() {
    logger.info { "### Filtering ThreatAwareSystem by ThreatType ###" }

    val rootkit = SimpleThreat(ThreatType.ROOTKIT, 1, "Simple-Rootkit")
    val trojan = SimpleThreat(ThreatType.TROJAN, 2, "Simple-Trojan")
    val threats = listOf(rootkit, trojan)

    val threatAwareSystem = SimpleThreatAwareSystem("Sys-1", threats)

    logger.info { "Filtering ThreatAwareSystem. Initial : $threatAwareSystem" }

    // Filtering using Filterer
    val rootkitThreatAwareSystem =
        threatAwareSystem.filtered().by { threat -> threat.type() == ThreatType.ROOTKIT }

    logger.info { "Filtered by threatType = ROOTKIT : $rootkitThreatAwareSystem" }
}

/**
 * Demonstrates how to filter [SimpleProbabilisticThreatAwareSystem] based on probability property.
 * The [com.iluwatar.filterer.domain.Filterer.by] method accepts a predicate on
 * [com.iluwatar.filterer.threat.ProbableThreat].
 */
private fun filteringSimpleProbableThreats() {
    logger.info { "### Filtering ProbabilisticThreatAwareSystem by probability ###" }

    val trojanArcBomb = SimpleProbableThreat("Trojan-ArcBomb", 1, ThreatType.TROJAN, 0.99)
    val rootkit = SimpleProbableThreat("Rootkit-Kernel", 2, ThreatType.ROOTKIT, 0.8)

    val probableThreats = listOf(trojanArcBomb, rootkit)

    val probabilisticThreatAwareSystem =
        SimpleProbabilisticThreatAwareSystem("Sys-1", probableThreats)

    logger.info { "Filtering ProbabilisticThreatAwareSystem. Initial : $probabilisticThreatAwareSystem" }

    // Filtering using filterer
    val filteredThreatAwareSystem =
        probabilisticThreatAwareSystem
            .filtered()
            .by { probableThreat -> probableThreat.probability().compareTo(0.99) == 0 }

    logger.info { "Filtered by probability = 0.99 : $filteredThreatAwareSystem" }
}
