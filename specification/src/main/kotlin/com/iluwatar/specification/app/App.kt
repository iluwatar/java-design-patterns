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

// ABOUTME: Main application demonstrating the Specification pattern for creature filtering.
// ABOUTME: Shows hard-coded, parameterized, and composite specification examples.
package com.iluwatar.specification.app

import com.iluwatar.specification.creature.Creature
import com.iluwatar.specification.creature.Dragon
import com.iluwatar.specification.creature.Goblin
import com.iluwatar.specification.creature.KillerBee
import com.iluwatar.specification.creature.Octopus
import com.iluwatar.specification.creature.Shark
import com.iluwatar.specification.creature.Troll
import com.iluwatar.specification.property.Color
import com.iluwatar.specification.property.Movement
import com.iluwatar.specification.selector.ColorSelector
import com.iluwatar.specification.selector.MassEqualSelector
import com.iluwatar.specification.selector.MassGreaterThanSelector
import com.iluwatar.specification.selector.MassSmallerThanOrEqSelector
import com.iluwatar.specification.selector.MovementSelector
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.function.Predicate

private val logger = KotlinLogging.logger {}

/**
 * The central idea of the Specification pattern is to separate the statement of how to match a
 * candidate, from the candidate object that it is matched against. As well as its usefulness in
 * selection, it is also valuable for validation and for building to order.
 *
 * In this example we have a pool of creatures with different properties. We then have defined
 * separate selection rules (Specifications) that we apply to the collection and as output receive
 * only the creatures that match the selection criteria.
 *
 * http://martinfowler.com/apsupp/spec.pdf
 */
fun main() {
    // initialize creatures list
    val creatures = listOf(
        Goblin(), Octopus(), Dragon(), Shark(), Troll(), KillerBee()
    )
    // so-called "hard-coded" specification
    logger.info { "Demonstrating hard-coded specification :" }
    // find all walking creatures
    logger.info { "Find all walking creatures" }
    print(creatures, MovementSelector(Movement.WALKING))
    // find all dark creatures
    logger.info { "Find all dark creatures" }
    print(creatures, ColorSelector(Color.DARK))
    logger.info { "\n" }
    // so-called "parameterized" specification
    logger.info { "Demonstrating parameterized specification :" }
    // find all creatures heavier than 500kg
    logger.info { "Find all creatures heavier than 600kg" }
    print(creatures, MassGreaterThanSelector(600.0))
    // find all creatures heavier than 500kg
    logger.info { "Find all creatures lighter than or weighing exactly 500kg" }
    print(creatures, MassSmallerThanOrEqSelector(500.0))
    logger.info { "\n" }
    // so-called "composite" specification
    logger.info { "Demonstrating composite specification :" }
    // find all red and flying creatures
    logger.info { "Find all red and flying creatures" }
    val redAndFlying = ColorSelector(Color.RED).and(MovementSelector(Movement.FLYING))
    print(creatures, redAndFlying)
    // find all creatures dark or red, non-swimming, and heavier than or equal to 400kg
    logger.info { "Find all scary creatures" }
    val scaryCreaturesSelector = ColorSelector(Color.DARK)
        .or(ColorSelector(Color.RED))
        .and(MovementSelector(Movement.SWIMMING).not())
        .and(MassGreaterThanSelector(400.0).or(MassEqualSelector(400.0)))
    print(creatures, scaryCreaturesSelector)
}

private fun print(creatures: List<Creature>, selector: Predicate<Creature>) {
    creatures.filter { selector.test(it) }.forEach { logger.info { it.toString() } }
}
