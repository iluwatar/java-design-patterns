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
package com.iluwatar.roleobject

// ABOUTME: Abstract base class defining the Customer interface for the Role Object pattern.
// ABOUTME: Provides factory methods to create customers and abstract role management operations.

/** The main abstraction to work with Customer. */
abstract class Customer {

    /**
     * Add specific role @see [Role].
     *
     * @param role to add
     * @return true if the operation has been successful otherwise false
     */
    abstract fun addRole(role: Role): Boolean

    /**
     * Check specific role @see [Role].
     *
     * @param role to check
     * @return true if the role exists otherwise false
     */
    abstract fun hasRole(role: Role): Boolean

    /**
     * Remove specific role @see [Role].
     *
     * @param role to remove
     * @return true if the operation has been successful otherwise false
     */
    abstract fun remRole(role: Role): Boolean

    /**
     * Get specific instance associated with this role @see [Role].
     *
     * @param role to get
     * @param expectedRole instance class expected to get
     * @return the role instance if it exists and corresponds to expected class, or null
     */
    abstract fun <T : Customer> getRole(role: Role, expectedRole: Class<T>): T?

    companion object {
        fun newCustomer(): Customer = CustomerCore()

        /**
         * Create [Customer] with given roles.
         *
         * @param role roles
         * @return Customer
         */
        fun newCustomer(vararg role: Role): Customer {
            val customer = newCustomer()
            role.forEach { customer.addRole(it) }
            return customer
        }
    }
}
