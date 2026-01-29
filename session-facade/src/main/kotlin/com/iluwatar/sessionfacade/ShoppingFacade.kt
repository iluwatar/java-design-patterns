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

// ABOUTME: Facade class providing a simplified interface for the shopping system.
// ABOUTME: Coordinates cart, order, and payment services using the Session Facade pattern.

package com.iluwatar.sessionfacade

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * The ShoppingFacade class provides a simplified interface for clients to interact with the
 * shopping system. It acts as a facade to handle operations related to a shopping cart, order
 * processing, and payment. Responsibilities: - Add products to the shopping cart. - Remove products
 * from the shopping cart. - Retrieve the current shopping cart. - Finalize an order by calling the
 * order service. - Check if a payment is required based on the order total. - Process payment using
 * different payment methods (e.g., cash, credit card). The ShoppingFacade class delegates
 * operations to the following services: - CartService: Manages the cart and product catalog. -
 * OrderService: Handles the order finalization process and calculation of the total. -
 * PaymentService: Handles the payment processing based on the selected payment method.
 */
class ShoppingFacade {
    private val cartService: CartService
    private val orderService: OrderService
    private val paymentService: PaymentService

    /**
     * Instantiates a new Shopping facade.
     */
    init {
        val productCatalog = mutableMapOf(
            1 to Product(1, "Wireless Mouse", 25.99, "Ergonomic wireless mouse with USB receiver."),
            2 to Product(2, "Gaming Keyboard", 79.99, "RGB mechanical gaming keyboard with programmable keys.")
        )
        val cart = mutableMapOf<Int, Product>()
        cartService = CartService(cart, productCatalog)
        orderService = OrderService(cart)
        paymentService = PaymentService()
    }

    /**
     * Gets cart.
     *
     * @return the cart
     */
    fun getCart(): Map<Int, Product> = cartService.cart

    /**
     * Add to cart.
     *
     * @param productId the product id
     */
    fun addToCart(productId: Int) {
        cartService.addToCart(productId)
    }

    /**
     * Remove from cart.
     *
     * @param productId the product id
     */
    fun removeFromCart(productId: Int) {
        cartService.removeFromCart(productId)
    }

    /**
     * Order.
     */
    fun order() {
        orderService.order()
    }

    /**
     * Is payment required boolean.
     *
     * @return the boolean
     */
    fun isPaymentRequired(): Boolean {
        val total = orderService.getTotal()
        if (total == 0.0) {
            logger.info { "No payment required" }
            return false
        }
        return true
    }

    /**
     * Process payment.
     *
     * @param method the method
     */
    fun processPayment(method: String) {
        val paymentRequired = isPaymentRequired()
        if (paymentRequired) {
            paymentService.selectPaymentMethod(method)
        }
    }
}
