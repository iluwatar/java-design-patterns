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

// ABOUTME: Represents a rectangular boundary in the spatial partition system.
// ABOUTME: Provides containment and intersection checks for points and other rectangles.
package com.iluwatar.spatialpartition

/**
 * The Rect class helps in defining the boundary of the quadtree and is also used to define the
 * range within which objects need to be found in our example.
 */
class Rect(
    var coordinateX: Double,
    var coordinateY: Double,
    var width: Double,
    var height: Double
) {
    // (x,y) - centre of rectangle

    fun contains(p: Point<*>): Boolean {
        return p.coordinateX >= this.coordinateX - this.width / 2 &&
            p.coordinateX <= this.coordinateX + this.width / 2 &&
            p.coordinateY >= this.coordinateY - this.height / 2 &&
            p.coordinateY <= this.coordinateY + this.height / 2
    }

    fun intersects(other: Rect): Boolean {
        return !(this.coordinateX + this.width / 2 <= other.coordinateX - other.width / 2 ||
            this.coordinateX - this.width / 2 >= other.coordinateX + other.width / 2 ||
            this.coordinateY + this.height / 2 <= other.coordinateY - other.height / 2 ||
            this.coordinateY - this.height / 2 >= other.coordinateY + other.height / 2)
    }
}
