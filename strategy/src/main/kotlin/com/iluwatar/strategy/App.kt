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
package com.iluwatar.strategy

// ABOUTME: Entry point demonstrating the Strategy pattern in three variants.
// ABOUTME: Shows GoF class-based, lambda-based, and enum-based strategy approaches.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

private const val RED_DRAGON_EMERGES = "Red dragon emerges."
private const val GREEN_DRAGON_SPOTTED = "Green dragon spotted ahead!"
private const val BLACK_DRAGON_LANDS = "Black dragon lands before you."

/**
 * The Strategy pattern (also known as the policy pattern) is a software design pattern that enables
 * an algorithm's behavior to be selected at runtime.
 *
 * In this example ([DragonSlayingStrategy]) encapsulates an algorithm. The containing
 * object ([DragonSlayer]) can alter its behavior by changing its strategy.
 */
fun main() {
    // GoF Strategy pattern
    logger.info { GREEN_DRAGON_SPOTTED }
    var dragonSlayer = DragonSlayer(MeleeStrategy())
    dragonSlayer.goToBattle()
    logger.info { RED_DRAGON_EMERGES }
    dragonSlayer.changeStrategy(ProjectileStrategy())
    dragonSlayer.goToBattle()
    logger.info { BLACK_DRAGON_LANDS }
    dragonSlayer.changeStrategy(SpellStrategy())
    dragonSlayer.goToBattle()

    // Lambda implementation Strategy pattern
    logger.info { GREEN_DRAGON_SPOTTED }
    dragonSlayer = DragonSlayer { logger.info { "With your Excalibur you sever the dragon's head!" } }
    dragonSlayer.goToBattle()
    logger.info { RED_DRAGON_EMERGES }
    dragonSlayer.changeStrategy { logger.info { "You shoot the dragon with the magical crossbow and it falls dead on the ground!" } }
    dragonSlayer.goToBattle()
    logger.info { BLACK_DRAGON_LANDS }
    dragonSlayer.changeStrategy { logger.info { "You cast the spell of disintegration and the dragon vaporizes in a pile of dust!" } }
    dragonSlayer.goToBattle()

    // Enum-based lambda implementation Strategy pattern
    logger.info { GREEN_DRAGON_SPOTTED }
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.MELEE_STRATEGY)
    dragonSlayer.goToBattle()
    logger.info { RED_DRAGON_EMERGES }
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.PROJECTILE_STRATEGY)
    dragonSlayer.goToBattle()
    logger.info { BLACK_DRAGON_LANDS }
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.SPELL_STRATEGY)
    dragonSlayer.goToBattle()
}
