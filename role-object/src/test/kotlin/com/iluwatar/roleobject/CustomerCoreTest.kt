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

// ABOUTME: Tests for CustomerCore verifying role management operations (add, has, remove, get).
// ABOUTME: Ensures the core customer correctly stores, queries, and removes roles with type safety.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CustomerCoreTest {

    @Test
    fun addRole() {
        val core = CustomerCore()
        assertTrue(core.addRole(Role.BORROWER))
    }

    @Test
    fun hasRole() {
        val core = CustomerCore()
        core.addRole(Role.BORROWER)
        assertTrue(core.hasRole(Role.BORROWER))
        assertFalse(core.hasRole(Role.INVESTOR))
    }

    @Test
    fun remRole() {
        val core = CustomerCore()
        core.addRole(Role.BORROWER)

        val bRole = core.getRole(Role.BORROWER, BorrowerRole::class.java)
        assertNotNull(bRole)

        assertTrue(core.remRole(Role.BORROWER))

        val empt = core.getRole(Role.BORROWER, BorrowerRole::class.java)
        assertNull(empt)
    }

    @Test
    fun getRole() {
        val core = CustomerCore()
        core.addRole(Role.BORROWER)

        val bRole = core.getRole(Role.BORROWER, BorrowerRole::class.java)
        assertNotNull(bRole)

        val nonRole = core.getRole(Role.BORROWER, InvestorRole::class.java)
        assertNull(nonRole)

        val invRole = core.getRole(Role.INVESTOR, InvestorRole::class.java)
        assertNull(invRole)
    }

    @Test
    fun toStringTest() {
        var core = CustomerCore()
        core.addRole(Role.BORROWER)
        assertEquals("Customer{roles=[BORROWER]}", core.toString())

        core = CustomerCore()
        core.addRole(Role.INVESTOR)
        assertEquals("Customer{roles=[INVESTOR]}", core.toString())

        core = CustomerCore()
        assertEquals("Customer{roles=[]}", core.toString())
    }
}
