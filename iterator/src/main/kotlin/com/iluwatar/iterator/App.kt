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
package com.iluwatar.iterator

// ABOUTME: Entry point demonstrating the Iterator design pattern with treasure chest and BST examples.
// ABOUTME: Shows how iterators decouple traversal algorithms from the underlying container structures.

import com.iluwatar.iterator.bst.BstIterator
import com.iluwatar.iterator.bst.TreeNode
import com.iluwatar.iterator.list.ItemType
import com.iluwatar.iterator.list.TreasureChest
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

private val treasureChest = TreasureChest()

/**
 * The Iterator pattern is a design pattern in which an iterator is used to traverse a container and
 * access the container's elements. The Iterator pattern decouples algorithms from containers.
 *
 * In this example the Iterator ([Iterator]) adds abstraction layer on top of a collection
 * ([TreasureChest]). This way the collection can change its internal implementation without
 * affecting its clients.
 */
fun main() {
    demonstrateTreasureChestIteratorForType(ItemType.RING)
    demonstrateTreasureChestIteratorForType(ItemType.POTION)
    demonstrateTreasureChestIteratorForType(ItemType.WEAPON)
    demonstrateTreasureChestIteratorForType(ItemType.ANY)

    demonstrateBstIterator()
}

private fun demonstrateTreasureChestIteratorForType(itemType: ItemType) {
    logger.info { "------------------------" }
    logger.info { "Item Iterator for ItemType $itemType: " }
    val itemIterator = treasureChest.iterator(itemType)
    while (itemIterator.hasNext()) {
        logger.info { itemIterator.next().toString() }
    }
}

private fun demonstrateBstIterator() {
    logger.info { "------------------------" }
    logger.info { "BST Iterator: " }
    val root = buildIntegerBst()
    val bstIterator = BstIterator(root)
    while (bstIterator.hasNext()) {
        logger.info { "Next node: ${bstIterator.next().`val`}" }
    }
}

private fun buildIntegerBst(): TreeNode<Int> {
    val root = TreeNode(8)

    root.insert(3)
    root.insert(10)
    root.insert(1)
    root.insert(6)
    root.insert(14)
    root.insert(4)
    root.insert(7)
    root.insert(13)

    return root
}
