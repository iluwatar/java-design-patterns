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

// ABOUTME: Integration tests for the Anti-Corruption Layer pattern.
// ABOUTME: Validates order synchronization between legacy and modern shop systems.
package com.iluwatar.corruption.system

import com.iluwatar.corruption.system.legacy.LegacyOrder
import com.iluwatar.corruption.system.legacy.LegacyShop
import com.iluwatar.corruption.system.modern.Customer
import com.iluwatar.corruption.system.modern.ModernOrder
import com.iluwatar.corruption.system.modern.ModernShop
import com.iluwatar.corruption.system.modern.Shipment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Optional

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AntiCorruptionLayerTest {
    @Autowired
    private lateinit var legacyShop: LegacyShop

    @Autowired
    private lateinit var modernShop: ModernShop

    /**
     * Test the anti-corruption layer. Main intention is to demonstrate how the anti-corruption layer
     * works. The 2 shops (modern and legacy) should operate independently and in the same time
     * synchronize the data.
     */
    @Test
    fun antiCorruptionLayerTest() {
        // a new order comes to the legacy shop.
        val legacyOrder = LegacyOrder("1", "addr1", "item1", 1, 1)
        // place the order in the legacy shop.
        legacyShop.placeOrder(legacyOrder)
        // the order is placed as usual since there is no other orders with the id in the both systems.
        val legacyOrderWithIdOne = legacyShop.findOrder("1")
        assertEquals(Optional.of(legacyOrder), legacyOrderWithIdOne)

        // a new order (or maybe just the same order) appears in the modern shop
        val modernOrder = ModernOrder("1", Customer("addr1"), Shipment("item1", 1, 1), "")
        // the system places it, but it checks if there is an order with the same id in the legacy shop.
        modernShop.placeOrder(modernOrder)

        val modernOrderWithIdOne = modernShop.findOrder("1")
        // there is no new order placed since there is already an order with the same id in the legacy
        // shop.
        assertTrue(modernOrderWithIdOne.isEmpty)
    }

    /**
     * Test the anti-corruption layer when a conflict occurs between systems. This test ensures that
     * an exception is thrown when conflicting orders are placed.
     */
    @Test
    fun antiCorruptionLayerWithExTest() {
        // a new order comes to the legacy shop.
        val legacyOrder = LegacyOrder("1", "addr1", "item1", 1, 1)
        // place the order in the legacy shop.
        legacyShop.placeOrder(legacyOrder)
        // the order is placed as usual since there is no other orders with the id in the both systems.
        val legacyOrderWithIdOne = legacyShop.findOrder("1")
        assertEquals(Optional.of(legacyOrder), legacyOrderWithIdOne)
        // a new order but with the same id and different data appears in the modern shop
        val modernOrder = ModernOrder("1", Customer("addr1"), Shipment("item1", 10, 1), "")
        // the system rejects the order since there are 2 orders with contradiction there.
        assertThrows(ShopException::class.java) { modernShop.placeOrder(modernOrder) }
    }
}