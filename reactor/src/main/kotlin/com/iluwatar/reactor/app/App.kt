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

// ABOUTME: Main application demonstrating the Reactor pattern for distributed logging.
// ABOUTME: Listens on multiple TCP/UDP sockets and handles concurrent client requests.
package com.iluwatar.reactor.app

import com.iluwatar.reactor.framework.AbstractNioChannel
import com.iluwatar.reactor.framework.ChannelHandler
import com.iluwatar.reactor.framework.Dispatcher
import com.iluwatar.reactor.framework.NioDatagramChannel
import com.iluwatar.reactor.framework.NioReactor
import com.iluwatar.reactor.framework.NioServerSocketChannel
import com.iluwatar.reactor.framework.ThreadPoolDispatcher
import java.io.IOException

/**
 * This application demonstrates Reactor pattern. The example demonstrated is a Distributed Logging
 * Service where it listens on multiple TCP or UDP sockets for incoming log requests.
 *
 * **INTENT**
 *
 * The Reactor design pattern handles service requests that are delivered concurrently to an
 * application by one or more clients. The application can register specific handlers for processing
 * which are called by reactor on specific events.
 *
 * **PROBLEM**
 *
 * Server applications in a distributed system must handle multiple clients that send them service
 * requests. Following forces need to be resolved:
 *
 * - Availability
 * - Efficiency
 * - Programming Simplicity
 * - Adaptability
 *
 * **PARTICIPANTS**
 *
 * - Synchronous Event De-multiplexer
 *
 *   [NioReactor] plays the role of synchronous event de-multiplexer. It waits for
 *   events on multiple channels registered to it in an event loop.
 *
 * - Initiation Dispatcher
 *
 *   [NioReactor] plays this role as the application specific [ChannelHandler]s
 *   are registered to the reactor.
 *
 * - Handle
 *
 *   [AbstractNioChannel] acts as a handle that is registered to the reactor. When any
 *   events occur on a handle, reactor calls the appropriate handler.
 *
 * - Event Handler
 *
 *   [ChannelHandler] acts as an event handler, which is bound to a channel and is
 *   called back when any event occurs on any of its associated handles. Application logic
 *   resides in event handlers.
 *
 * The application utilizes single thread to listen for requests on all ports. It does not create a
 * separate thread for each client, which provides better scalability under load (number of clients
 * increase). The example uses Java NIO framework to implement the Reactor.
 */
class App(private val dispatcher: Dispatcher) {

    private lateinit var reactor: NioReactor
    private val channels: MutableList<AbstractNioChannel> = mutableListOf()

    /**
     * Starts the NIO reactor.
     *
     * @throws IOException if any channel fails to bind.
     */
    @Throws(IOException::class)
    fun start() {
        /*
         * The application can customize its event dispatching mechanism.
         */
        reactor = NioReactor(dispatcher)

        /*
         * This represents application specific business logic that dispatcher will call on appropriate
         * events. These events are read events in our example.
         */
        val loggingHandler = LoggingHandler()

        /*
         * Our application binds to multiple channels and uses same logging handler to handle incoming
         * log requests.
         */
        reactor
            .registerChannel(tcpChannel(16666, loggingHandler))
            .registerChannel(tcpChannel(16667, loggingHandler))
            .registerChannel(udpChannel(16668, loggingHandler))
            .registerChannel(udpChannel(16669, loggingHandler))
            .start()
    }

    /**
     * Stops the NIO reactor. This is a blocking call.
     *
     * @throws InterruptedException if interrupted while stopping the reactor.
     * @throws IOException if any I/O error occurs
     */
    @Throws(InterruptedException::class, IOException::class)
    fun stop() {
        reactor.stop()
        dispatcher.stop()
        for (channel in channels) {
            channel.getJavaChannel().close()
        }
    }

    @Throws(IOException::class)
    private fun tcpChannel(port: Int, handler: ChannelHandler): AbstractNioChannel {
        val channel = NioServerSocketChannel(port, handler)
        channel.bind()
        channels.add(channel)
        return channel
    }

    @Throws(IOException::class)
    private fun udpChannel(port: Int, handler: ChannelHandler): AbstractNioChannel {
        val channel = NioDatagramChannel(port, handler)
        channel.bind()
        channels.add(channel)
        return channel
    }
}

/** App entry. */
@Throws(IOException::class)
fun main() {
    App(ThreadPoolDispatcher(2)).start()
}
