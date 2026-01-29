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

// ABOUTME: Core reactor class implementing the Synchronous Event De-multiplexer and Initiation Dispatcher.
// ABOUTME: Uses NIO Selector to wait for events on multiple channels and dispatches them to handlers.
package com.iluwatar.reactor.framework

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.IOException
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/**
 * This class acts as Synchronous Event De-multiplexer and Initiation Dispatcher of Reactor pattern.
 * Multiple handles i.e. [AbstractNioChannel]s can be registered to the reactor, and it blocks
 * for events from all these handles. Whenever an event occurs on any of the registered handles, it
 * synchronously de-multiplexes the event which can be any of read, write or accept, and dispatches
 * the event to the appropriate [ChannelHandler] using the [Dispatcher].
 *
 * Implementation: A NIO reactor runs in its own thread when it is started using [start]
 * method. [NioReactor] uses [Selector] for realizing Synchronous Event De-multiplexing.
 *
 * NOTE: This is one of the ways to implement NIO reactor, and it does not take care of all
 * possible edge cases which are required in a real application. This implementation is meant to
 * demonstrate the fundamental concepts that lie behind Reactor pattern.
 */
class NioReactor
@Throws(IOException::class)
constructor(private val dispatcher: Dispatcher) {

    private val selector: Selector = Selector.open()

    /**
     * All the work of altering the SelectionKey operations and Selector operations are performed in
     * the context of main event loop of reactor. So when any channel needs to change its readability
     * or writability, a new command is added in the command queue and then the event loop picks up
     * the command and executes it in next iteration.
     */
    private val pendingCommands: Queue<Runnable> = ConcurrentLinkedQueue()

    private val reactorMain: ExecutorService = Executors.newSingleThreadExecutor()

    /** Starts the reactor event loop in a new thread. */
    fun start() {
        reactorMain.execute {
            try {
                logger.info { "Reactor started, waiting for events..." }
                eventLoop()
            } catch (e: IOException) {
                logger.error(e) { "exception in event loop" }
            }
        }
    }

    /**
     * Stops the reactor and related resources such as dispatcher.
     *
     * @throws InterruptedException if interrupted while stopping the reactor.
     * @throws IOException if any I/O error occurs.
     */
    @Throws(InterruptedException::class, IOException::class)
    fun stop() {
        reactorMain.shutdown()
        selector.wakeup()
        if (!reactorMain.awaitTermination(4, TimeUnit.SECONDS)) {
            reactorMain.shutdownNow()
        }
        selector.close()
        logger.info { "Reactor stopped" }
    }

    /**
     * Registers a new channel (handle) with this reactor. Reactor will start waiting for events on
     * this channel and notify of any events. While registering the channel the reactor uses
     * [AbstractNioChannel.interestedOps] to know about the interested operation of this channel.
     *
     * @param channel a new channel on which reactor will wait for events. The channel must be bound
     *     prior to being registered.
     * @return this
     * @throws IOException if any I/O error occurs.
     */
    @Throws(IOException::class)
    fun registerChannel(channel: AbstractNioChannel): NioReactor {
        val key = channel.getJavaChannel().register(selector, channel.interestedOps)
        key.attach(channel)
        channel.setReactor(this)
        return this
    }

    @Throws(IOException::class)
    private fun eventLoop() {
        // honor interrupt request
        while (!Thread.interrupted()) {
            // honor any pending commands first
            processPendingCommands()

            /*
             * Synchronous event de-multiplexing happens here, this is blocking call which returns when it
             * is possible to initiate non-blocking operation on any of the registered channels.
             */
            selector.select()

            /*
             * Represents the events that have occurred on registered handles.
             */
            val keys = selector.selectedKeys()
            val iterator = keys.iterator()

            while (iterator.hasNext()) {
                val key = iterator.next()
                if (!key.isValid) {
                    iterator.remove()
                    continue
                }
                processKey(key)
            }
            keys.clear()
        }
    }

    private fun processPendingCommands() {
        val iterator = pendingCommands.iterator()
        while (iterator.hasNext()) {
            val command = iterator.next()
            command.run()
            iterator.remove()
        }
    }

    /**
     * Initiation dispatcher logic, it checks the type of event and notifier application specific
     * event handler to handle the event.
     */
    @Throws(IOException::class)
    private fun processKey(key: SelectionKey) {
        when {
            key.isAcceptable -> onChannelAcceptable(key)
            key.isReadable -> onChannelReadable(key)
            key.isWritable -> onChannelWritable(key)
        }
    }

    @Throws(IOException::class)
    private fun onChannelWritable(key: SelectionKey) {
        val channel = key.attachment() as AbstractNioChannel
        channel.flush(key)
    }

    private fun onChannelReadable(key: SelectionKey) {
        try {
            // reads the incoming data in context of reactor main loop. Can this be improved?
            val readObject = (key.attachment() as AbstractNioChannel).read(key)
            dispatchReadEvent(key, readObject)
        } catch (e: IOException) {
            try {
                key.channel().close()
            } catch (e1: IOException) {
                logger.error(e1) { "error closing channel" }
            }
        }
    }

    /**
     * Uses the application provided dispatcher to dispatch events to application handler.
     */
    private fun dispatchReadEvent(key: SelectionKey, readObject: Any) {
        dispatcher.onChannelReadEvent(key.attachment() as AbstractNioChannel, readObject, key)
    }

    @Throws(IOException::class)
    private fun onChannelAcceptable(key: SelectionKey) {
        val serverSocketChannel = key.channel() as ServerSocketChannel
        val socketChannel = serverSocketChannel.accept()
        socketChannel.configureBlocking(false)
        val readKey = socketChannel.register(selector, SelectionKey.OP_READ)
        readKey.attach(key.attachment())
    }

    /**
     * Queues the change of operations request of a channel, which will change the interested
     * operations of the channel sometime in the future.
     *
     * This is a non-blocking method and does not guarantee that the operations have changed when
     * this method returns.
     *
     * @param key the key for which operations have to be changed.
     * @param interestedOps the new interest operations.
     */
    fun changeOps(key: SelectionKey, interestedOps: Int) {
        pendingCommands.add(ChangeKeyOpsCommand(key, interestedOps))
        selector.wakeup()
    }

    /** A command that changes the interested operations of the key provided. */
    private class ChangeKeyOpsCommand(
        private val key: SelectionKey,
        private val interestedOps: Int
    ) : Runnable {
        override fun run() {
            key.interestOps(interestedOps)
        }

        override fun toString(): String = "Change of ops to: $interestedOps"
    }
}
