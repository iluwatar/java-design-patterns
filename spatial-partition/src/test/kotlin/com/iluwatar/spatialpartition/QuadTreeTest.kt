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

// ABOUTME: Unit tests for the QuadTree class verifying spatial query correctness.
// ABOUTME: Compares quadtree query results against brute-force verification method.
package com.iluwatar.spatialpartition

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Hashtable
import java.util.Random

/** Testing QuadTree class. */
class QuadTreeTest {

    @Test
    fun queryTest() {
        val points = mutableListOf<Point<*>>()
        val rand = Random()
        for (i in 0 until 20) {
            val p = Bubble(rand.nextInt(300), rand.nextInt(300), i, rand.nextInt(2) + 1)
            points.add(p)
        }
        val field = Rect(150.0, 150.0, 300.0, 300.0) // size of field
        val queryRange = Rect(70.0, 130.0, 100.0, 100.0) // result = all points lying in this rectangle
        // points found in the query range using quadtree and normal method is same
        val points1 = quadTreeTest(points, field, queryRange)
        val points2 = verify(points, queryRange)
        assertEquals(points1, points2)
    }

    companion object {
        fun quadTreeTest(
            points: Collection<Point<*>>,
            field: Rect,
            queryRange: Rect
        ): Hashtable<Int, Point<*>> {
            // creating quadtree and inserting all points
            val qTree = QuadTree(queryRange, 4)
            points.forEach { qTree.insert(it) }

            val result = Hashtable<Int, Point<*>>()
            qTree.query(field, mutableListOf()).forEach { point ->
                result[point.id] = point
            }
            return result
        }

        fun verify(points: Collection<Point<*>>, queryRange: Rect): Hashtable<Int, Point<*>> {
            val result = Hashtable<Int, Point<*>>()
            points.filter { queryRange.contains(it) }.forEach { point ->
                result[point.id] = point
            }
            return result
        }
    }
}
