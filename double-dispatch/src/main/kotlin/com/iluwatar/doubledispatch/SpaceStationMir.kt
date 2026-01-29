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
package com.iluwatar.doubledispatch

// ABOUTME: Space station Mir game object that takes damage and can catch fire on collision.
// ABOUTME: Demonstrates double-dispatch by resolving collisions with typed visitor methods.

import com.iluwatar.doubledispatch.constants.HITS
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/** Space station Mir game object. */
open class SpaceStationMir(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
) : GameObject(left, top, right, bottom) {

    override fun collision(gameObject: GameObject) {
        gameObject.collisionResolve(this)
    }

    override fun collisionResolve(asteroid: FlamingAsteroid) {
        logger.info {
            "${HITS.format(asteroid::class.simpleName, this::class.simpleName)} " +
                "${this::class.simpleName} is damaged! ${this::class.simpleName} is set on fire!"
        }
        isDamaged = true
        isOnFire = true
    }

    override fun collisionResolve(meteoroid: Meteoroid) {
        logHits(meteoroid)
        isDamaged = true
    }

    override fun collisionResolve(mir: SpaceStationMir) {
        logHits(mir)
        isDamaged = true
    }

    override fun collisionResolve(iss: SpaceStationIss) {
        logHits(iss)
        isDamaged = true
    }

    private fun logHits(gameObject: GameObject) {
        logger.info {
            "${HITS.format(gameObject::class.simpleName, this::class.simpleName)} " +
                "${this::class.simpleName} is damaged!"
        }
    }
}
