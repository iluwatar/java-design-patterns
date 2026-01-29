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
package com.iluwatar.decorator

// ABOUTME: Entry point demonstrating the Decorator design pattern.
// ABOUTME: Shows how a SimpleTroll's behavior changes when decorated with ClubbedTroll.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The Decorator pattern is a more flexible alternative to subclassing. The Decorator class
 * implements the same interface as the target and uses composition to "decorate" calls to the
 * target. Using the Decorator pattern it is possible to change the behavior of the class during
 * runtime.
 *
 * In this example we show how the simple [SimpleTroll] first attacks and then flees the
 * battle. Then we decorate the [SimpleTroll] with a [ClubbedTroll] and perform the
 * attack again. You can see how the behavior changes after the decoration.
 */
fun main() {
    // simple troll
    logger.info { "A simple looking troll approaches." }
    val troll = SimpleTroll()
    troll.attack()
    troll.fleeBattle()
    logger.info { "Simple troll power: ${troll.getAttackPower()}.\n" }

    // change the behavior of the simple troll by adding a decorator
    logger.info { "A troll with huge club surprises you." }
    val clubbedTroll = ClubbedTroll(troll)
    clubbedTroll.attack()
    clubbedTroll.fleeBattle()
    logger.info { "Clubbed troll power: ${clubbedTroll.getAttackPower()}.\n" }
}
