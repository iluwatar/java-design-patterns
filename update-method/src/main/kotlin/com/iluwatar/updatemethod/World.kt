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
package com.iluwatar.updatemethod

// ABOUTME: The game world that maintains a collection of entities and runs the game loop.
// ABOUTME: Each loop iteration processes input, updates all entities, and renders the frame.

import io.github.oshai.kotlinlogging.KotlinLogging
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

/** The game world class. Maintain all the objects existed in the game frames. */
class World {

    internal val entities: MutableList<Entity> = mutableListOf()

    @Volatile
    internal var isRunning: Boolean = false

    /**
     * Main game loop. This loop will always run until the game is over. For each loop it will
     * process user input, update internal status, and render the next frames. For more detail
     * please refer to the game-loop pattern.
     */
    private fun gameLoop() {
        while (isRunning) {
            processInput()
            update()
            render()
        }
    }

    /**
     * Handle any user input that has happened since the last call. In order to simulate the
     * situation in real-life game, here we add a random time lag. The time lag ranges from
     * 50 ms to 250 ms.
     */
    private fun processInput() {
        try {
            val lag = SecureRandom().nextInt(200) + 50
            Thread.sleep(lag.toLong())
        } catch (e: InterruptedException) {
            logger.error { e.message }
            Thread.currentThread().interrupt()
        }
    }

    /**
     * Update internal status. The update method pattern invoke update method for each entity
     * in the game.
     */
    private fun update() {
        for (entity in entities) {
            entity.update()
        }
    }

    /** Render the next frame. Here we do nothing since it is not related to the pattern. */
    private fun render() {
        // Does Nothing
    }

    /** Run game loop. */
    fun run() {
        logger.info { "Start game." }
        isRunning = true
        val thread = Thread { gameLoop() }
        thread.start()
    }

    /** Stop game loop. */
    fun stop() {
        logger.info { "Stop game." }
        isRunning = false
    }

    fun addEntity(entity: Entity) {
        entities.add(entity)
    }
}
