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

// ABOUTME: Abstract base class for coarse-grained objects that manage dependent objects.
// ABOUTME: Handles the lifecycle and data coordination of multiple dependent objects.
package com.iluwatar.compositeentity

/**
 * A coarse-grained object is an object with its own life cycle manages its own relationships to
 * other objects. It can be an object contained in the composite entity, or, composite entity itself
 * can be the coarse-grained object which holds dependent objects.
 */
abstract class CoarseGrainedObject<T> {
    internal lateinit var dependentObjects: Array<DependentObject<T>>

    open fun setData(vararg data: T) {
        data.forEachIndexed { index, value ->
            dependentObjects[index].data = value
        }
    }

    @Suppress("UNCHECKED_CAST")
    open fun getData(): Array<Any?> = dependentObjects.map { it.data }.toTypedArray()
}