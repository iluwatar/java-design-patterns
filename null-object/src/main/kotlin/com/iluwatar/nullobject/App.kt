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
package com.iluwatar.nullobject

// ABOUTME: Entry point demonstrating the Null Object pattern with a binary tree.
// ABOUTME: Builds a tree using NullNode singletons instead of null references for traversal safety.

/**
 * Null Object pattern replaces null values with neutral objects. Many times this simplifies
 * algorithms since no extra null checks are needed.
 *
 * In this example we build a binary tree where the nodes are either normal or Null Objects. No
 * null values are used in the tree making the traversal easy.
 */
fun main() {
    val root = NodeImpl(
        "1",
        NodeImpl(
            "11",
            NodeImpl("111", NullNode, NullNode),
            NullNode
        ),
        NodeImpl(
            "12",
            NullNode,
            NodeImpl("122", NullNode, NullNode)
        )
    )

    root.walk()
}
