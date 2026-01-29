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
package com.iluwatar.iterator.list

// ABOUTME: Parameterized tests verifying TreasureChest iterator and getItems functionality.
// ABOUTME: Ensures every expected item can be found via both the iterator and the direct item list.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

/** TreasureChestTest */
class TreasureChestTest {

    companion object {
        /**
         * Create a list of all expected items in the chest.
         *
         * @return The set of all expected items in the chest
         */
        @JvmStatic
        fun dataProvider(): List<Array<Any>> = listOf(
            arrayOf(Item(ItemType.POTION, "Potion of courage")),
            arrayOf(Item(ItemType.RING, "Ring of shadows")),
            arrayOf(Item(ItemType.POTION, "Potion of wisdom")),
            arrayOf(Item(ItemType.POTION, "Potion of blood")),
            arrayOf(Item(ItemType.WEAPON, "Sword of silver +1")),
            arrayOf(Item(ItemType.POTION, "Potion of rust")),
            arrayOf(Item(ItemType.POTION, "Potion of healing")),
            arrayOf(Item(ItemType.RING, "Ring of armor")),
            arrayOf(Item(ItemType.WEAPON, "Steel halberd")),
            arrayOf(Item(ItemType.WEAPON, "Dagger of poison"))
        )
    }

    /**
     * Test if the expected item can be retrieved from the chest using the
     * [TreasureChestItemIterator]
     */
    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testIterator(expectedItem: Item) {
        val chest = TreasureChest()
        val iterator = chest.iterator(expectedItem.type)
        assertNotNull(iterator)

        while (iterator.hasNext()) {
            val item = iterator.next()
            assertNotNull(item)
            assertEquals(expectedItem.type, item!!.type)

            val name = item.toString()
            assertNotNull(name)
            if (expectedItem.toString() == name) {
                return
            }
        }

        fail<Unit>("Expected to find item [$expectedItem] using iterator, but we didn't.")
    }

    /**
     * Test if the expected item can be retrieved from the chest using the
     * [TreasureChest.getItems] method
     */
    @ParameterizedTest
    @MethodSource("dataProvider")
    fun testGetItems(expectedItem: Item) {
        val chest = TreasureChest()
        val items = chest.getItems()
        assertNotNull(items)

        for (item in items) {
            assertNotNull(item)
            assertNotNull(item.type)
            assertNotNull(item.toString())

            val sameType = expectedItem.type == item.type
            val sameName = expectedItem.toString() == item.toString()
            if (sameType && sameName) {
                return
            }
        }

        fail<Unit>("Expected to find item [$expectedItem] in the item list, but we didn't.")
    }
}
