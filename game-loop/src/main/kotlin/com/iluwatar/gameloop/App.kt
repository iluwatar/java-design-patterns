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
package com.iluwatar.gameloop

import io.github.oshai.kotlinlogging.KotlinLogging

// ABOUTME: Entry point demonstrating different game loop implementations.
// ABOUTME: Runs frame-based, variable-step, and fixed-step game loops for 2 seconds each.

private val logger = KotlinLogging.logger {}

private const val GAME_LOOP_DURATION_TIME = 2000L

fun main() {
    try {
        logger.info { "Start frame-based game loop:" }
        val frameBasedGameLoop = FrameBasedGameLoop()
        frameBasedGameLoop.run()
        Thread.sleep(GAME_LOOP_DURATION_TIME)
        frameBasedGameLoop.stop()
        logger.info { "Stop frame-based game loop." }

        logger.info { "Start variable-step game loop:" }
        val variableStepGameLoop = VariableStepGameLoop()
        variableStepGameLoop.run()
        Thread.sleep(GAME_LOOP_DURATION_TIME)
        variableStepGameLoop.stop()
        logger.info { "Stop variable-step game loop." }

        logger.info { "Start fixed-step game loop:" }
        val fixedStepGameLoop = FixedStepGameLoop()
        fixedStepGameLoop.run()
        Thread.sleep(GAME_LOOP_DURATION_TIME)
        fixedStepGameLoop.stop()
        logger.info { "Stop variable-step game loop." }
    } catch (e: InterruptedException) {
        logger.error { e.message }
    }
}
