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
import java.security.SecureRandom

// ABOUTME: Abstract base class for game loop implementations.
// ABOUTME: Provides common functionality for running, stopping, and rendering the game.

private val logger = KotlinLogging.logger {}

abstract class GameLoop {
    @Volatile
    internal var status: GameStatus = GameStatus.STOPPED

    internal val controller: GameController = GameController()

    fun run() {
        status = GameStatus.RUNNING
        Thread { processGameLoop() }.start()
    }

    fun stop() {
        status = GameStatus.STOPPED
    }

    fun isGameRunning(): Boolean = status == GameStatus.RUNNING

    internal open fun processInput() {
        try {
            val lag = SecureRandom().nextInt(200) + 50
            Thread.sleep(lag.toLong())
        } catch (e: InterruptedException) {
            logger.error { e.message }
            /* Clean up whatever needs to be handled before interrupting  */
            Thread.currentThread().interrupt()
        }
    }

    internal open fun render() {
        val position = controller.getBulletPosition()
        logger.info { "Current bullet position: $position" }
    }

    internal abstract fun processGameLoop()
}
