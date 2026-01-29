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

// ABOUTME: Player input component that handles keyboard events.
// ABOUTME: Translates key locations to velocity changes for player movement.
package com.iluwatar.component.component.inputcomponent

import com.iluwatar.component.GameObject
import io.github.oshai.kotlinlogging.KotlinLogging
import java.awt.event.KeyEvent

private val logger = KotlinLogging.logger {}

/**
 * PlayerInputComponent is used to handle user key event inputs, and thus it implements the
 * InputComponent interface.
 */
class PlayerInputComponent : InputComponent {
    companion object {
        private const val WALK_ACCELERATION = 1
    }

    /**
     * The update method to change the velocity based on the input key event.
     *
     * @param gameObject the gameObject instance
     * @param e key event instance
     */
    override fun update(
        gameObject: GameObject,
        e: Int,
    ) {
        when (e) {
            KeyEvent.KEY_LOCATION_LEFT -> {
                gameObject.updateVelocity(-WALK_ACCELERATION)
                logger.info { "${gameObject.name} has moved left." }
            }
            KeyEvent.KEY_LOCATION_RIGHT -> {
                gameObject.updateVelocity(WALK_ACCELERATION)
                logger.info { "${gameObject.name} has moved right." }
            }
            else -> {
                logger.info { "${gameObject.name}'s velocity is unchanged due to the invalid input" }
                gameObject.updateVelocity(0)
            }
        }
    }
}