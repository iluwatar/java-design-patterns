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

// ABOUTME: Main application entry point for the server session pattern demonstration.
// ABOUTME: Creates an HTTP server with login/logout endpoints and session expiration management.
package com.iluwatar.sessionserver

import com.sun.net.httpserver.HttpServer
import io.github.oshai.kotlinlogging.KotlinLogging
import java.net.InetSocketAddress
import java.time.Instant

private val logger = KotlinLogging.logger {}

private val sessions = mutableMapOf<String, Int>()
private val sessionCreationTimes = mutableMapOf<String, Instant>()
private const val SESSION_EXPIRATION_TIME = 10000L

/**
 * The server session pattern is a behavioral design pattern concerned with assigning the
 * responsibility of storing session data on the server side. Within the context of stateless
 * protocols like HTTP all requests are isolated events independent of previous requests. In order
 * to create sessions during user-access for a particular web application various methods can be
 * used, such as cookies. Cookies are a small piece of data that can be sent between client and
 * server on every request and response so that the server can "remember" the previous requests. In
 * general cookies can either store the session data or the cookie can store a session identifier
 * and be used to access appropriate data from a persistent storage. In the latter case the session
 * data is stored on the server-side and appropriate data is identified by the cookie sent from a
 * client's request. This project demonstrates the latter case. In the following example the App
 * class starts a server and assigns LoginHandler class to handle login request. When a user logs
 * in a session identifier is created and stored for future requests in a list. When a user logs
 * out the session identifier is deleted from the list along with the appropriate user session
 * data, which is handled by the LogoutHandler class.
 */
fun main() {
    // Create HTTP server listening on port 8080
    val server = HttpServer.create(InetSocketAddress(8080), 0)

    // Set up session management endpoints
    server.createContext("/login", LoginHandler(sessions, sessionCreationTimes))
    server.createContext("/logout", LogoutHandler(sessions, sessionCreationTimes))

    // Start the server
    server.start()

    // Start background task to check for expired sessions
    sessionExpirationTask()

    logger.info { "Server started. Listening on port 8080..." }
}

private fun sessionExpirationTask() {
    Thread {
        while (true) {
            try {
                logger.info { "Session expiration checker started..." }
                Thread.sleep(SESSION_EXPIRATION_TIME)
                val currentTime = Instant.now()
                synchronized(sessions) {
                    synchronized(sessionCreationTimes) {
                        val iterator = sessionCreationTimes.entries.iterator()
                        while (iterator.hasNext()) {
                            val entry = iterator.next()
                            if (entry.value.plusMillis(SESSION_EXPIRATION_TIME).isBefore(currentTime)) {
                                sessions.remove(entry.key)
                                iterator.remove()
                            }
                        }
                    }
                }
                logger.info { "Session expiration checker finished!" }
            } catch (e: InterruptedException) {
                logger.error(e) { "An error occurred" }
                Thread.currentThread().interrupt()
            }
        }
    }.start()
}
