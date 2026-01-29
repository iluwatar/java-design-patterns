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

// ABOUTME: Main entry point demonstrating the Session Facade pattern for shopping operations.
// ABOUTME: Shows how ShoppingFacade simplifies cart, payment, and order interactions for clients.

package com.iluwatar.sessionfacade

/**
 * The main entry point of the application that demonstrates the usage of the ShoppingFacade to
 * manage the shopping process using the Session Facade pattern. This class serves as a client that
 * interacts with the simplified interface provided by the ShoppingFacade, which encapsulates
 * complex interactions with the underlying business services. The ShoppingFacade acts as a session
 * bean that coordinates the communication between multiple services, hiding their complexity and
 * providing a single, unified API.
 */
fun main() {
    val shoppingFacade = ShoppingFacade()

    // Adding items to the shopping cart
    shoppingFacade.addToCart(1)
    shoppingFacade.addToCart(2)

    // Processing the payment with the chosen method
    shoppingFacade.processPayment("cash")

    // Finalizing the order
    shoppingFacade.order()
}
