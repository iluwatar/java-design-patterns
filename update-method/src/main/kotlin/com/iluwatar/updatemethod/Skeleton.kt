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

// ABOUTME: Skeleton entity that patrols back and forth on the game map.
// ABOUTME: Moves one position per frame, reversing direction at the boundaries (0 and 100).

/**
 * Skeletons are always patrolling on the game map. Initially all the skeletons patrolling to the
 * right, and after them reach the bounding, it will start patrolling to the left. For each frame,
 * one skeleton will move 1 position step.
 */
class Skeleton(id: Int, position: Int = 0) : Entity(id) {

    internal var patrollingLeft: Boolean = false

    init {
        this.position = position
    }

    override fun update() {
        if (patrollingLeft) {
            position -= 1
            if (position == PATROLLING_LEFT_BOUNDING) {
                patrollingLeft = false
            }
        } else {
            position += 1
            if (position == PATROLLING_RIGHT_BOUNDING) {
                patrollingLeft = true
            }
        }
        logger.info { "Skeleton $id is on position $position." }
    }

    companion object {
        private const val PATROLLING_LEFT_BOUNDING = 0
        private const val PATROLLING_RIGHT_BOUNDING = 100
    }
}
