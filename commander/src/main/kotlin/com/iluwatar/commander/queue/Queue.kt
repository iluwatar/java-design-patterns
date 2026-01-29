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

// ABOUTME: Queue data structure implementation using linked nodes.
// ABOUTME: Provides enqueue, dequeue, peek, and isEmpty operations.
package com.iluwatar.commander.queue

import com.iluwatar.commander.exceptions.IsEmptyException

class Queue<T> {
    private var front: Node<T>? = null
    private var rear: Node<T>? = null
    private var size: Int = 0

    private class Node<V>(
        val value: V,
        var next: Node<V>? = null,
    )

    fun isEmpty(): Boolean = size == 0

    fun enqueue(obj: T) {
        if (front == null) {
            front = Node(obj)
            rear = front
        } else {
            val temp = Node(obj)
            rear?.next = temp
            rear = temp
        }
        size++
    }

    @Throws(IsEmptyException::class)
    fun dequeue(): T {
        if (isEmpty()) {
            throw IsEmptyException()
        }
        val temp = front
        front = front?.next
        size--
        return temp!!.value
    }

    @Throws(IsEmptyException::class)
    fun peek(): T {
        if (isEmpty()) {
            throw IsEmptyException()
        }
        return front!!.value
    }
}