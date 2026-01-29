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
package com.iluwatar.datatransfer.product

// ABOUTME: Resource class that serves product information, acting as the server in the demo.
// ABOUTME: Provides different product views for admin (with cost) and customer (without cost).

/**
 * The resource class which serves product information. This class acts as the server in
 * the demo, holding all product details.
 */
class ProductResource(val products: MutableList<Product>) {
    /**
     * Get all products with private (admin-level) details including cost.
     *
     * @return all products in the scheme of the private DTO.
     */
    fun getAllProductsForAdmin(): List<ProductDto.Response.Private> =
        products.map { p ->
            ProductDto.Response.Private(
                id = p.id,
                name = p.name,
                cost = p.cost,
                price = p.price,
            )
        }

    /**
     * Get all products with public (customer-level) details, excluding cost.
     *
     * @return all products in the scheme of the public DTO.
     */
    fun getAllProductsForCustomer(): List<ProductDto.Response.Public> =
        products.map { p ->
            ProductDto.Response.Public(
                id = p.id,
                name = p.name,
                price = p.price,
            )
        }

    /**
     * Save a new product.
     *
     * @param createProductDto the creation request DTO.
     */
    fun save(createProductDto: ProductDto.Request.Create) {
        products.add(
            Product(
                id = (products.size + 1).toLong(),
                name = createProductDto.name,
                supplier = createProductDto.supplier,
                price = createProductDto.price,
                cost = createProductDto.cost,
            ),
        )
    }
}
