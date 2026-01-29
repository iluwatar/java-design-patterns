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

// ABOUTME: Concrete spatial partition implementation for bubble collision detection.
// ABOUTME: Uses quadtree to efficiently find and handle collisions between bubbles.
package com.iluwatar.spatialpartition

/**
 * This class extends the generic SpatialPartition abstract class and is used in our example to keep
 * track of all the bubbles that collide, pop and stay un-popped.
 */
class SpatialPartitionBubbles(
    private val bubbles: MutableMap<Int, Bubble>,
    private val bubblesQuadTree: QuadTree
) : SpatialPartitionGeneric<Bubble>() {

    override fun handleCollisionsUsingQt(obj: Bubble) {
        // finding points within area of a square drawn with centre same as
        // centre of bubble and length = radius of bubble
        val rect = Rect(obj.coordinateX.toDouble(), obj.coordinateY.toDouble(), 2.0 * obj.radius, 2.0 * obj.radius)
        val quadTreeQueryResult = mutableListOf<Point<*>>()
        this.bubblesQuadTree.query(rect, quadTreeQueryResult)
        // handling these collisions
        obj.handleCollision(quadTreeQueryResult, this.bubbles)
    }
}
