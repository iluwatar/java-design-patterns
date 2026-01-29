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

// ABOUTME: Concrete implementation of Point representing a bubble with radius.
// ABOUTME: Handles movement, collision detection, and popping logic for bubble interactions.
package com.iluwatar.spatialpartition

import io.github.oshai.kotlinlogging.KotlinLogging
import java.security.SecureRandom

private val logger = KotlinLogging.logger {}

/**
 * Bubble class extends Point. In this example, we create several bubbles in the field, let them
 * move and keep track of which ones have popped and which ones remain.
 */
class Bubble(
    x: Int,
    y: Int,
    id: Int,
    val radius: Int
) : Point<Bubble>(x, y, id) {

    companion object {
        private val RANDOM = SecureRandom()
    }

    override fun move() {
        // moves by 1 unit in either direction
        this.coordinateX += RANDOM.nextInt(3) - 1
        this.coordinateY += RANDOM.nextInt(3) - 1
    }

    override fun touches(obj: Bubble): Boolean {
        // distance between them is greater than sum of radii (both sides of equation squared)
        return (this.coordinateX - obj.coordinateX) * (this.coordinateX - obj.coordinateX) +
            (this.coordinateY - obj.coordinateY) * (this.coordinateY - obj.coordinateY) <=
            (this.radius + obj.radius) * (this.radius + obj.radius)
    }

    fun pop(allBubbles: MutableMap<Int, Bubble>) {
        logger.info { "Bubble ${this.id} popped at (${this.coordinateX},${this.coordinateY})!" }
        allBubbles.remove(this.id)
    }

    override fun handleCollision(toCheck: Collection<Point<*>>, all: MutableMap<Int, Bubble>) {
        var toBePopped = false // if any other bubble collides with it, made true
        for (point in toCheck) {
            val otherId = point.id
            if (all[otherId] != null && // the bubble hasn't been popped yet
                this.id != otherId && // the two bubbles are not the same
                this.touches(all[otherId]!!) // the bubbles touch
            ) {
                all[otherId]!!.pop(all)
                toBePopped = true
            }
        }
        if (toBePopped) {
            this.pop(all)
        }
    }
}
