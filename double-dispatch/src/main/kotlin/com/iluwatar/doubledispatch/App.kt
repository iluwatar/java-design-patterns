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

// ABOUTME: Entry point demonstrating the Double Dispatch pattern with game object collisions.
// ABOUTME: Creates game objects and resolves collisions using type-specific visitor methods.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * When a message with a parameter is sent to an object, the resultant behaviour is defined by the
 * implementation of that method in the receiver. Sometimes the behaviour must also be determined by
 * the type of the parameter.
 *
 * One way to implement this would be to create multiple instanceof-checks for the method's
 * parameter. However, this creates a maintenance issue. When new types are added we would also need
 * to change the method's implementation and add a new instanceof-check. This violates the single
 * responsibility principle - a class should have only one reason to change.
 *
 * Instead of the instanceof-checks a better way is to make another virtual call on the parameter
 * object. This way new functionality can be easily added without the need to modify existing
 * implementation (open-closed principle).
 *
 * In this example we have hierarchy of objects ([GameObject]) that can collide with each other.
 * Each object has its own coordinates which are checked against the other objects' coordinates. If
 * there is an overlap, then the objects collide utilizing the Double Dispatch pattern.
 */
fun main() {
    // initialize game objects and print their status
    logger.info { "Init objects and print their status" }
    val objects = listOf(
        FlamingAsteroid(0, 0, 5, 5),
        SpaceStationMir(1, 1, 2, 2),
        Meteoroid(10, 10, 15, 15),
        SpaceStationIss(12, 12, 14, 14)
    )
    objects.forEach { logger.info { it.toString() } }

    // collision check
    logger.info { "Collision check" }
    objects.forEach { o1 ->
        objects.forEach { o2 ->
            if (o1 !== o2 && o1.intersectsWith(o2)) {
                o1.collision(o2)
            }
        }
    }

    // output eventual object statuses
    logger.info { "Print object status after collision checks" }
    objects.forEach { logger.info { it.toString() } }
}
