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

// ABOUTME: Service class representing the modern shop system with anti-corruption layer integration.
// ABOUTME: Coordinates order placement between modern store and legacy system via ACL.
package com.iluwatar.corruption.system.modern

import com.iluwatar.corruption.system.AntiCorruptionLayer
import com.iluwatar.corruption.system.ShopException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

/**
 * The class represents a modern shop system. The main purpose of the class is to place orders and
 * find orders.
 */
@Service
open class ModernShop {
    @Autowired
    private lateinit var store: ModernStore

    @Autowired
    private lateinit var acl: AntiCorruptionLayer

    /**
     * Places the order in the modern system. If the order is already present in the legacy system,
     * then no need to place it again.
     */
    @Throws(ShopException::class)
    fun placeOrder(order: ModernOrder) {
        val id = order.id
        // check if the order is already present in the legacy system
        val orderInObsoleteSystem = acl.findOrderInLegacySystem(id)

        if (orderInObsoleteSystem.isPresent) {
            val legacyOrder = orderInObsoleteSystem.get()
            if (order != legacyOrder) {
                throw ShopException.throwIncorrectData(legacyOrder.toString(), order.toString())
            }
        } else {
            store.put(id, order)
        }
    }

    /**
     * Finds the order in the modern system.
     */
    fun findOrder(orderId: String): Optional<ModernOrder> = store.get(orderId)
}