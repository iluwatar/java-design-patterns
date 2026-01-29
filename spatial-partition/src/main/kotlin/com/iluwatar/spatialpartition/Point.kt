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

// ABOUTME: Abstract base class for objects tracked in a spatial partition data structure.
// ABOUTME: Defines position, movement, collision detection, and collision handling contracts.
package com.iluwatar.spatialpartition

/**
 * The abstract Point class which will be extended by any object in the field whose location has to
 * be kept track of. Defined by x,y coordinates and an id for easy hashing into hashtable.
 *
 * @param T T will be type subclass
 */
abstract class Point<T>(
    var coordinateX: Int,
    var coordinateY: Int,
    val id: Int
) {
    /** defines how the object moves. */
    abstract fun move()

    /**
     * defines conditions for interacting with an object obj.
     *
     * @param obj is another object on field which also extends Point
     * @return whether the object can interact with the other or not
     */
    abstract fun touches(obj: T): Boolean

    /**
     * handling interactions/collisions with other objects.
     *
     * @param toCheck contains the objects which need to be checked
     * @param all contains hashtable of all points on field at this time
     */
    abstract fun handleCollision(toCheck: Collection<Point<*>>, all: MutableMap<Int, T>)
}
