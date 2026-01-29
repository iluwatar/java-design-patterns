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

// ABOUTME: In-memory implementation of ProductRepository for storing products.
// ABOUTME: Pre-populated with sample products (Laptop and Smartphone) for demonstration.
package com.iluwatar.cleanarchitecture

/**
 * In-memory implementation of the [ProductRepository] interface.
 *
 * This repository stores products in memory allowing retrieval by product ID.
 */
class InMemoryProductRepository : ProductRepository {
    private val products: MutableMap<String, Product> =
        mutableMapOf(
            "1" to Product("1", "Laptop", LAPTOP_PRICE),
            "2" to Product("2", "Smartphone", SMARTPHONE_PRICE),
        )

    /**
     * Retrieves a product by its unique ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return The [Product] corresponding to the given ID, or null if not found.
     */
    override fun getProductById(productId: String): Product? = products[productId]

    companion object {
        /** The price of the Laptop in USD. */
        private const val LAPTOP_PRICE = 1000.0

        /** The price of the Smartphone in USD. */
        private const val SMARTPHONE_PRICE = 500.0
    }
}