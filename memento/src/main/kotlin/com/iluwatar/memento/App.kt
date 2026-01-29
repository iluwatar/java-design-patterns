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
package com.iluwatar.memento

// ABOUTME: Entry point demonstrating the Memento design pattern.
// ABOUTME: Shows how a Star's state can be saved and restored using memento objects.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The Memento pattern is a software design pattern that provides the ability to restore an object
 * to its previous state (undo via rollback).
 *
 * The Memento pattern is implemented with three objects: the originator, a caretaker and a
 * memento. The originator is some object that has an internal state. The caretaker is going to do
 * something to the originator, but wants to be able to undo the change. The caretaker first asks
 * the originator for a memento object. Then it does whatever operation (or sequence of operations)
 * it was going to do. To roll back to the state before the operations, it returns the memento
 * object to the originator. The memento object itself is an opaque object (one which the caretaker
 * cannot, or should not, change). When using this pattern, care should be taken if the originator
 * may change other objects or resources - the memento pattern operates on a single object.
 *
 * In this example the object ([Star]) gives out a "memento" ([StarMemento]) that contains the
 * state of the object. Later on the memento can be set back to the object restoring the state.
 */
fun main() {
    val states = ArrayDeque<StarMemento>()

    val star = Star(StarType.SUN, 10000000, 500000)
    logger.info { star.toString() }
    states.addLast(star.getMemento())
    star.timePasses()
    logger.info { star.toString() }
    states.addLast(star.getMemento())
    star.timePasses()
    logger.info { star.toString() }
    states.addLast(star.getMemento())
    star.timePasses()
    logger.info { star.toString() }
    states.addLast(star.getMemento())
    star.timePasses()
    logger.info { star.toString() }
    while (states.isNotEmpty()) {
        star.setMemento(states.removeLast())
        logger.info { star.toString() }
    }
}
