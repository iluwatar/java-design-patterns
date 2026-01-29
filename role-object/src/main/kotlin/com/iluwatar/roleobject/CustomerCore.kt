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

// ABOUTME: Core implementation of Customer that stores and manages role objects in a map.
// ABOUTME: Provides add/remove/query operations for dynamically attached CustomerRole instances.

/**
 * Core class to store different customer roles.
 *
 * @see CustomerRole Note: not thread safe
 */
open class CustomerCore : Customer() {

    private val roles: MutableMap<Role, CustomerRole> = mutableMapOf()

    override fun addRole(role: Role): Boolean {
        val instance: CustomerRole? = role.instance()
        return if (instance != null) {
            roles[role] = instance
            true
        } else {
            false
        }
    }

    override fun hasRole(role: Role): Boolean = roles.containsKey(role)

    override fun remRole(role: Role): Boolean = roles.remove(role) != null

    override fun <T : Customer> getRole(role: Role, expectedRole: Class<T>): T? {
        val customerRole = roles[role] ?: return null
        return if (expectedRole.isInstance(customerRole)) {
            expectedRole.cast(customerRole)
        } else {
            null
        }
    }

    override fun toString(): String {
        val roleKeys = roles.keys.toTypedArray().contentToString()
        return "Customer{roles=$roleKeys}"
    }
}
