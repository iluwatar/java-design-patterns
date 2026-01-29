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

// ABOUTME: Meteoroid game object that participates in double-dispatch collisions.
// ABOUTME: Logs collision events but does not take damage or catch fire from any collision.

import com.iluwatar.doubledispatch.constants.HITS
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/** Meteoroid game object. */
open class Meteoroid(
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
) : GameObject(left, top, right, bottom) {

    override fun collision(gameObject: GameObject) {
        gameObject.collisionResolve(this)
    }

    override fun collisionResolve(asteroid: FlamingAsteroid) {
        logger.info { HITS.format(asteroid::class.simpleName, this::class.simpleName) }
    }

    override fun collisionResolve(meteoroid: Meteoroid) {
        logger.info { HITS.format(meteoroid::class.simpleName, this::class.simpleName) }
    }

    override fun collisionResolve(mir: SpaceStationMir) {
        logger.info { HITS.format(mir::class.simpleName, this::class.simpleName) }
    }

    override fun collisionResolve(iss: SpaceStationIss) {
        logger.info { HITS.format(iss::class.simpleName, this::class.simpleName) }
    }
}
