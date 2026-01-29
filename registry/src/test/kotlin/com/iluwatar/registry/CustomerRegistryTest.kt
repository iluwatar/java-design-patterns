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

// ABOUTME: Tests for the CustomerRegistry verifying add and query operations.
// ABOUTME: Validates that customers can be stored and retrieved by ID, and that missing IDs return null.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class CustomerRegistryTest {

    @Test
    fun shouldBeAbleToAddAndQueryCustomerObjectFromRegistry() {
        val john = Customer("1", "john")
        val julia = Customer("2", "julia")

        CustomerRegistry.addCustomer(john)
        CustomerRegistry.addCustomer(julia)

        val customerWithId1 = CustomerRegistry.getCustomer("1")
        assertNotNull(customerWithId1)
        assertEquals("1", customerWithId1?.id)
        assertEquals("john", customerWithId1?.name)

        val customerWithId2 = CustomerRegistry.getCustomer("2")
        assertNotNull(customerWithId2)
        assertEquals("2", customerWithId2?.id)
        assertEquals("julia", customerWithId2?.name)
    }

    @Test
    fun shouldReturnNullWhenQueriedCustomerIsNotInRegistry() {
        val customerWithId5 = CustomerRegistry.getCustomer("5")
        assertNull(customerWithId5)
    }
}
