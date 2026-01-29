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
package com.iluwatar.client.session

// ABOUTME: Entry point demonstrating the Client-Session design pattern.
// ABOUTME: Shows how session data is stored on the client side and sent to the server with each request.

/**
 * The Client-Session pattern allows the session data to be stored on the client side and send this
 * data to the server with each request.
 *
 * In this example, the [Server] class represents the server that would process the incoming
 * [Request] and also assign [Session] to a client. Here one instance of Server is created. Then we
 * create two sessions for two different clients. These sessions are then passed on to the server in
 * the request along with the data. The server is then able to interpret the client based on the
 * session associated with it.
 */
fun main() {
    val server = Server("localhost", 8080)
    val session1 = server.getSession("Session1")
    val session2 = server.getSession("Session2")
    val request1 = Request("Data1", session1)
    val request2 = Request("Data2", session2)
    server.process(request1)
    server.process(request2)
}