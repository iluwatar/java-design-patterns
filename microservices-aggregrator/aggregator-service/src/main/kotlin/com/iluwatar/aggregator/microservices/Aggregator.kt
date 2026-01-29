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
// ABOUTME: REST controller that aggregates calls on various micro-services.
// ABOUTME: Collects product data from information and inventory services and publishes under a REST endpoint.
package com.iluwatar.aggregator.microservices

import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * The aggregator aggregates calls on various micro-services, collects data and further publishes
 * them under a REST endpoint.
 */
@RestController
class Aggregator {
    @Resource
    internal lateinit var informationClient: ProductInformationClient

    @Resource
    internal lateinit var inventoryClient: ProductInventoryClient

    /**
     * Retrieves product data.
     *
     * @return a Product.
     */
    @GetMapping("/product")
    fun getProduct(): Product {
        val productTitle = informationClient.getProductTitle()
        val productInventory = inventoryClient.getProductInventories()

        return Product(
            // Fallback to error message
            title = productTitle ?: "Error: Fetching Product Title Failed",
            // Fallback to default error inventory
            productInventories = productInventory ?: -1,
        )
    }
}
