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

// ABOUTME: Game entity that coordinates component managers for AI, physics, and rendering.
// ABOUTME: Demonstrates data locality by processing components of the same type together.
package com.iluwatar.data.locality.game

import com.iluwatar.data.locality.game.component.manager.AiComponentManager
import com.iluwatar.data.locality.game.component.manager.PhysicsComponentManager
import com.iluwatar.data.locality.game.component.manager.RenderComponentManager
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The game Entity maintains a big array of pointers. Each spin of the game loop, we need to run
 * the following:
 *
 * - Update the AI components.
 * - Update the physics components for them.
 * - Render them using their render components.
 */
class GameEntity(numEntities: Int) {

    private val aiComponentManager: AiComponentManager
    private val physicsComponentManager: PhysicsComponentManager
    private val renderComponentManager: RenderComponentManager

    init {
        logger.info { "Init Game with #Entity : $numEntities" }
        aiComponentManager = AiComponentManager(numEntities)
        physicsComponentManager = PhysicsComponentManager(numEntities)
        renderComponentManager = RenderComponentManager(numEntities)
    }

    /** Start all components. */
    fun start() {
        logger.info { "Start Game" }
        aiComponentManager.start()
        physicsComponentManager.start()
        renderComponentManager.start()
    }

    /** Update all components. */
    fun update() {
        logger.info { "Update Game Component" }
        // Process AI.
        aiComponentManager.update()

        // update physics.
        physicsComponentManager.update()

        // Draw to screen.
        renderComponentManager.render()
    }
}
