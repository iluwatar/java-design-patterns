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
package com.iluwatar.leaderelection

// ABOUTME: Abstract base class for message managers that handle inter-instance communication.
// ABOUTME: Provides utility methods for finding the next instance in the ring topology.

/**
 * Abstract class of all the message manager classes.
 */
abstract class AbstractMessageManager(
    /** Contains all the instances in the system. Key is its ID, and value is the instance itself. */
    internal val instanceMap: Map<Int, Instance>
) : MessageManager {

    /**
     * Find the next instance with the smallest ID.
     *
     * @return The next instance.
     */
    internal fun findNextInstance(currentId: Int): Instance {
        val candidateList = instanceMap.keys
            .filter { it > currentId && instanceMap[it]!!.isAlive() }
            .sorted()

        return if (candidateList.isEmpty()) {
            val index = instanceMap.keys
                .filter { instanceMap[it]!!.isAlive() }
                .sorted()
                .first()
            instanceMap[index]!!
        } else {
            val index = candidateList.first()
            instanceMap[index]!!
        }
    }
}
