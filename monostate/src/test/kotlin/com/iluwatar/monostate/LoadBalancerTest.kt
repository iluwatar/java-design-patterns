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

package com.iluwatar.monostate

// ABOUTME: Tests for the LoadBalancer class verifying MonoState pattern behavior.
// ABOUTME: Validates shared state across instances and round-robin request delegation using MockK.

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/** LoadBalancerTest */
class LoadBalancerTest {

  @Test
  fun testSameStateAmongstAllInstances() {
    val firstBalancer = LoadBalancer()
    val secondBalancer = LoadBalancer()
    firstBalancer.addServer(Server("localhost", 8085, 6))
    // Both should have the same number of servers.
    assertEquals(firstBalancer.noOfServers, secondBalancer.noOfServers)
    // Both Should have the same LastServedId
    assertEquals(firstBalancer.lastServedId, secondBalancer.lastServedId)
  }

  @Test
  fun testServe() {
    val server = mockk<Server>()
    every { server.host } returns "testhost"
    every { server.port } returns 1234
    every { server.serve(any()) } just runs

    val loadBalancer = LoadBalancer()
    loadBalancer.addServer(server)

    val request = Request("test")
    for (i in 0 until loadBalancer.noOfServers * 2) {
      loadBalancer.serverRequest(request)
    }

    verify(exactly = 2) { server.serve(request) }
  }
}
