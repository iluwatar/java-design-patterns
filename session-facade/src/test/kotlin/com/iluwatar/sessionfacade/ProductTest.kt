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

// ABOUTME: Test class for Product data class functionality.
// ABOUTME: Tests product creation, equals/hashCode, and toString methods.

package com.iluwatar.sessionfacade

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * The type Product test.
 */
class ProductTest {

    /**
     * Test product creation.
     */
    @Test
    fun testProductCreation() {
        val id = 1
        val name = "Product A"
        val price = 200.0
        val description = "a description"
        val product = Product(id, name, price, description)
        assertEquals(id, product.id)
        assertEquals(name, product.name)
        assertEquals(price, product.price)
        assertEquals(description, product.description)
    }

    /**
     * Test equals and hash code.
     */
    @Test
    fun testEqualsAndHashCode() {
        val product1 = Product(1, "Product A", 99.99, "a description")
        val product2 = Product(1, "Product A", 99.99, "a description")
        val product3 = Product(2, "Product B", 199.99, "a description")

        assertEquals(product1, product2)
        assertNotEquals(product1, product3)
        assertEquals(product1.hashCode(), product2.hashCode())
        assertNotEquals(product1.hashCode(), product3.hashCode())
    }

    /**
     * Test to string.
     */
    @Test
    fun testToString() {
        val product = Product(1, "Product A", 99.99, "a description")
        val toStringResult = product.toString()
        assertTrue(toStringResult.contains("Product A"))
        assertTrue(toStringResult.contains("99.99"))
    }
}
