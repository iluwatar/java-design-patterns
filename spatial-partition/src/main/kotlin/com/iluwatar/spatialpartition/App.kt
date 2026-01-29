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

// ABOUTME: Entry point demonstrating the Spatial Partition design pattern with bubbles.
// ABOUTME: Compares collision detection performance with and without quadtree spatial partitioning.
package com.iluwatar.spatialpartition

import io.github.oshai.kotlinlogging.KotlinLogging
import java.security.SecureRandom
import java.util.concurrent.ConcurrentHashMap

private val logger = KotlinLogging.logger {}

/**
 * The idea behind the **Spatial Partition** design pattern is to enable efficient location of
 * objects by storing them in a data structure that is organised by their positions. This is
 * especially useful in the gaming world, where one may need to look up all the objects within a
 * certain boundary, or near a certain other object, repeatedly. The data structure can be used to
 * store moving and static objects, though in order to keep track of the moving objects, their
 * positions will have to be reset each time they move. This would mean having to create a new
 * instance of the data structure each frame, which would use up additional memory, and so this
 * pattern should only be used if one does not mind trading memory for speed and the number of
 * objects to keep track of is large to justify the use of the extra space.
 *
 * In our example, we use **[QuadTree] data structure** which divides into 4 (quad)
 * sub-sections when the number of objects added to it exceeds a certain number (int field
 * capacity). There is also a **[Rect]** class to define the boundary of the quadtree. We
 * use an abstract class **[Point]** with x and y coordinate fields and also an id field so
 * that it can easily be put and looked up in the hashmap. This class has abstract methods to define
 * how the object moves (move()), when to check for collision with any object (touches(obj)) and how
 * to handle collision (handleCollision(obj)), and will be extended by any object whose position has
 * to be kept track of in the quadtree. The **[SpatialPartitionGeneric]** abstract class
 * has 2 fields - a hashmap containing all objects (we use hashmap for faster lookups, insertion and
 * deletion) and a quadtree, and contains an abstract method which defines how to handle
 * interactions between objects using the quadtree.
 *
 * Using the quadtree data structure will reduce the time complexity of finding the objects
 * within a certain range from **O(n^2) to O(nlogn)**, increasing the speed of computations
 * immensely in case of large number of objects, which will have a positive effect on the rendering
 * speed of the game.
 */

internal fun noSpatialPartition(numOfMovements: Int, bubbles: MutableMap<Int, Bubble>) {
    // all bubbles have to be checked for collision for all bubbles
    val bubblesToCheck = bubbles.values
    var movements = numOfMovements

    // will run numOfMovement times or till all bubbles have popped
    while (movements > 0 && bubbles.isNotEmpty()) {
        bubbles.forEach { (i, bubble) ->
            // bubble moves, new position gets updated
            // and collisions are checked with all bubbles in bubblesToCheck
            bubble.move()
            bubbles[i] = bubble
            bubble.handleCollision(bubblesToCheck, bubbles)
        }
        movements--
    }
    // bubbles not popped
    bubbles.keys.forEach { key -> logger.info { "Bubble $key not popped" } }
}

internal fun withSpatialPartition(
    height: Int,
    width: Int,
    numOfMovements: Int,
    bubbles: MutableMap<Int, Bubble>
) {
    // creating quadtree
    val rect = Rect(width / 2.0, height / 2.0, width.toDouble(), height.toDouble())
    val quadTree = QuadTree(rect, 4)
    var movements = numOfMovements

    // will run numOfMovement times or till all bubbles have popped
    while (movements > 0 && bubbles.isNotEmpty()) {
        // quadtree updated each time
        bubbles.values.forEach { quadTree.insert(it) }
        bubbles.forEach { (i, bubble) ->
            // bubble moves, new position gets updated, quadtree used to reduce computations
            bubble.move()
            bubbles[i] = bubble
            val sp = SpatialPartitionBubbles(bubbles, quadTree)
            sp.handleCollisionsUsingQt(bubble)
        }
        movements--
    }
    // bubbles not popped
    bubbles.keys.forEach { key -> logger.info { "Bubble $key not popped" } }
}

/**
 * Program entry point.
 *
 * @param args command line args
 */
fun main(args: Array<String>) {
    val bubbles1 = ConcurrentHashMap<Int, Bubble>()
    val bubbles2 = ConcurrentHashMap<Int, Bubble>()
    val rand = SecureRandom()
    for (i in 0 until 10000) {
        val b = Bubble(rand.nextInt(300), rand.nextInt(300), i, rand.nextInt(2) + 1)
        bubbles1[i] = b
        bubbles2[i] = b
        logger.info {
            "Bubble $i with radius ${b.radius} added at (${b.coordinateX},${b.coordinateY})"
        }
    }

    val start1 = System.currentTimeMillis()
    noSpatialPartition(20, bubbles1)
    val end1 = System.currentTimeMillis()
    val start2 = System.currentTimeMillis()
    withSpatialPartition(300, 300, 20, bubbles2)
    val end2 = System.currentTimeMillis()
    logger.info { "Without spatial partition takes ${end1 - start1} ms" }
    logger.info { "With spatial partition takes ${end2 - start2} ms" }
}
