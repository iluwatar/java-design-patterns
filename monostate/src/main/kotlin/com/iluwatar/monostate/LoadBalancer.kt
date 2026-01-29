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

// ABOUTME: Implements the MonoState pattern using a companion object to share state across all instances.
// ABOUTME: Delegates incoming requests to servers in a Round Robin fashion.

/**
 * The LoadBalancer class. This implements the MonoState pattern. It holds a series of servers. Upon
 * receiving a new Request, it delegates the call to the servers in a Round Robin Fashion. Since all
 * instances of the class share the same state, all instances will delegate to the same server on
 * receiving a new Request.
 */
class LoadBalancer {

  companion object {
    private val servers: MutableList<Server> = mutableListOf()
    private var lastServedId: Int = 0

    init {
      var id = 0
      for (port in intArrayOf(8080, 8081, 8082, 8083, 8084)) {
        servers.add(Server("localhost", port, ++id))
      }
    }
  }

  /** Add new server. */
  fun addServer(server: Server) {
    synchronized(servers) {
      servers.add(server)
    }
  }

  val noOfServers: Int
    get() = servers.size

  val lastServedId: Int
    get() = Companion.lastServedId

  /** Handle request. */
  @Synchronized
  fun serverRequest(request: Request) {
    if (Companion.lastServedId >= servers.size) {
      Companion.lastServedId = 0
    }
    val server = servers[Companion.lastServedId++]
    server.serve(request)
  }
}
