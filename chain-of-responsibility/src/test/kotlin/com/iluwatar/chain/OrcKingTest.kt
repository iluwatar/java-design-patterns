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
package com.iluwatar.chain

// ABOUTME: Tests that OrcKing correctly routes all request types through the handler chain.
// ABOUTME: Verifies each request type is handled by the appropriate handler.

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** OrcKingTest */
class OrcKingTest {
    /** All possible requests */
    private val requests =
        listOf(
            Request(RequestType.DEFEND_CASTLE, "Don't let the barbarians enter my castle!!"),
            Request(RequestType.TORTURE_PRISONER, "Don't just stand there, tickle him!"),
            Request(RequestType.COLLECT_TAX, "Don't steal, the King hates competition ..."),
        )

    @Test
    fun testMakeRequest() {
        val king = OrcKing()

        requests.forEach { request ->
            king.makeRequest(request)
            assertTrue(request.handled) {
                "Expected all requests from King to be handled, but [$request] was not!"
            }
        }
    }
}