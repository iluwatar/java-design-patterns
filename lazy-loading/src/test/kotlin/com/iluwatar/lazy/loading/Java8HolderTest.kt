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

package com.iluwatar.lazy.loading

// ABOUTME: Tests for the Java8Holder lazy loading implementation.
// ABOUTME: Uses reflection to access the supplier's internal state for verification.

import java.util.function.Supplier

/**
 * Java8HolderTest
 */
class Java8HolderTest : AbstractHolderTest() {

    private val holder = Java8Holder()

    override fun getInternalHeavyValue(): Heavy? {
        val supplier = holder.heavy
        val supplierClass = supplier.javaClass

        // This is a little fishy, but I don't know another way to test this:
        // The lazy holder is at first a lambda, but gets replaced with a new supplier after loading ...
        return if (supplierClass.isLocalClass) {
            val instanceField = supplierClass.getDeclaredField("heavyInstance")
            instanceField.isAccessible = true
            instanceField.get(supplier) as Heavy
        } else {
            null
        }
    }

    override fun getHeavy(): Heavy = holder.getHeavy()
}
