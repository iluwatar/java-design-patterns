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
package com.iluwatar.registry

// ABOUTME: Entry point demonstrating the Registry design pattern.
// ABOUTME: Shows how Customer objects are stored in and retrieved from the CustomerRegistry singleton.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * In Registry pattern, objects of a single class are stored and provide a global point of access to
 * them. Note that there is no restriction on the number of objects.
 *
 * The given example [CustomerRegistry] represents the registry used to store and access
 * [Customer] objects.
 */
fun main() {
    val john = Customer("1", "John")
    CustomerRegistry.addCustomer(john)

    val julia = Customer("2", "Julia")
    CustomerRegistry.addCustomer(julia)

    logger.info { "John ${CustomerRegistry.getCustomer("1")}" }
    logger.info { "Julia ${CustomerRegistry.getCustomer("2")}" }
}
