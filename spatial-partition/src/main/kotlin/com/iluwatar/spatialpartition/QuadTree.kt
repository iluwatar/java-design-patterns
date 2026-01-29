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

// ABOUTME: QuadTree data structure for efficient spatial partitioning and point queries.
// ABOUTME: Subdivides space into quadrants when capacity is exceeded for O(n log n) range queries.
package com.iluwatar.spatialpartition

/**
 * The quadtree data structure is being used to keep track of the objects' locations. It has the
 * insert(Point) and query(range) methods to insert a new object and find the objects within a
 * certain (rectangular) range respectively.
 */
class QuadTree(
    internal val boundary: Rect,
    internal val capacity: Int
) {
    internal var divided: Boolean = false
    internal val points: MutableMap<Int, Point<*>> = mutableMapOf()
    internal var northwest: QuadTree? = null
    internal var northeast: QuadTree? = null
    internal var southwest: QuadTree? = null
    internal var southeast: QuadTree? = null

    fun insert(p: Point<*>) {
        if (this.boundary.contains(p)) {
            if (this.points.size < this.capacity) {
                points[p.id] = p
            } else {
                if (!this.divided) {
                    this.divide()
                }
                when {
                    this.northwest!!.boundary.contains(p) -> this.northwest!!.insert(p)
                    this.northeast!!.boundary.contains(p) -> this.northeast!!.insert(p)
                    this.southwest!!.boundary.contains(p) -> this.southwest!!.insert(p)
                    this.southeast!!.boundary.contains(p) -> this.southeast!!.insert(p)
                }
            }
        }
    }

    internal fun divide() {
        val x = this.boundary.coordinateX
        val y = this.boundary.coordinateY
        val width = this.boundary.width
        val height = this.boundary.height
        val nw = Rect(x - width / 4, y + height / 4, width / 2, height / 2)
        this.northwest = QuadTree(nw, this.capacity)
        val ne = Rect(x + width / 4, y + height / 4, width / 2, height / 2)
        this.northeast = QuadTree(ne, this.capacity)
        val sw = Rect(x - width / 4, y - height / 4, width / 2, height / 2)
        this.southwest = QuadTree(sw, this.capacity)
        val se = Rect(x + width / 4, y - height / 4, width / 2, height / 2)
        this.southeast = QuadTree(se, this.capacity)
        this.divided = true
    }

    fun query(r: Rect, relevantPoints: MutableCollection<Point<*>>): Collection<Point<*>> {
        // could also be a circle instead of a rectangle
        if (this.boundary.intersects(r)) {
            this.points.values.filter { r.contains(it) }.forEach { relevantPoints.add(it) }
            if (this.divided) {
                this.northwest!!.query(r, relevantPoints)
                this.northeast!!.query(r, relevantPoints)
                this.southwest!!.query(r, relevantPoints)
                this.southeast!!.query(r, relevantPoints)
            }
        }
        return relevantPoints
    }
}
