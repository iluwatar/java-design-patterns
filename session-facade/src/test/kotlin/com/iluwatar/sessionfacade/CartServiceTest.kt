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

// ABOUTME: Test class for CartService functionality.
// ABOUTME: Tests add/remove cart operations including edge cases with invalid product IDs.

package com.iluwatar.sessionfacade

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * The type Cart service test.
 */
class CartServiceTest {

    private lateinit var cartService: CartService
    private lateinit var cart: MutableMap<Int, Product>

    /**
     * Sets up.
     */
    @BeforeEach
    fun setUp() {
        cart = mutableMapOf()
        val productCatalog = mutableMapOf(
            1 to Product(1, "Product A", 2.0, "any description"),
            2 to Product(2, "Product B", 300.0, "a watch")
        )
        cartService = CartService(cart, productCatalog)
    }

    /**
     * Test add to cart.
     */
    @Test
    fun testAddToCart() {
        cartService.addToCart(1)
        assertEquals(1, cart.size)
        assertEquals("Product A", cart[1]?.name)
    }

    /**
     * Test remove from cart.
     */
    @Test
    fun testRemoveFromCart() {
        cartService.addToCart(1)
        assertEquals(1, cart.size)
        cartService.removeFromCart(1)
        assertTrue(cart.isEmpty())
    }

    /**
     * Test add to cart with invalid product id.
     */
    @Test
    fun testAddToCartWithInvalidProductId() {
        cartService.addToCart(999)
        assertTrue(cart.isEmpty())
    }

    /**
     * Test remove from cart with invalid product id.
     */
    @Test
    fun testRemoveFromCartWithInvalidProductId() {
        cartService.removeFromCart(999)
        assertTrue(cart.isEmpty())
    }
}
