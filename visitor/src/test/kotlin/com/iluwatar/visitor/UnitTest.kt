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
package com.iluwatar.visitor

// ABOUTME: Abstract base test class for verifying Unit accept/visit behavior using MockK.
// ABOUTME: Creates mock children and a mock visitor to verify double dispatch and child traversal.

import io.mockk.mockk
import io.mockk.verify
import io.mockk.confirmVerified
import org.junit.jupiter.api.Test

/**
 * Test related to Units
 *
 * @param U Type of Unit
 */
abstract class UnitTest<U : Unit>(private val factory: (Array<Unit>) -> U) {

    @Test
    fun testAccept() {
        val children = Array<Unit>(5) { mockk(relaxed = true) }

        val unit = factory(children)
        val visitor = mockk<UnitVisitor>(relaxed = true)
        unit.accept(visitor)
        verifyVisit(unit, visitor)

        children.forEach { child -> verify { child.accept(visitor) } }

        confirmVerified(*children)
        confirmVerified(visitor)
    }

    /**
     * Verify if the correct visit method is called on the mock, depending on the tested instance.
     *
     * @param unit The tested unit instance
     * @param mockedVisitor The mocked [UnitVisitor] who should have gotten a visit by the unit
     */
    abstract fun verifyVisit(unit: U, mockedVisitor: UnitVisitor)
}
