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

// ABOUTME: Abstract base class representing a Handle in the Reactor pattern.
// ABOUTME: Wraps NIO channels and provides queued write operations for better throughput.
package com.iluwatar.reactor.framework

import java.io.IOException
import java.nio.channels.SelectableChannel
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.util.Queue
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * This represents the Handle of Reactor pattern. These are resources managed by OS which can
 * be submitted to [NioReactor].
 *
 * This class serves has the responsibility of reading the data when a read event occurs and
 * writing the data back when the channel is writable. It leaves the reading and writing of data on
 * the concrete implementation. It provides a block writing mechanism wherein when any
 * [ChannelHandler] wants to write data back, it queues the data in pending write queue and clears it
 * in block manner. This provides better throughput.
 */
abstract class AbstractNioChannel(
    val handler: ChannelHandler,
    private val channel: SelectableChannel
) {
    private val channelToPendingWrites: MutableMap<SelectableChannel, Queue<Any>> = ConcurrentHashMap()
    private lateinit var reactor: NioReactor

    /** Injects the reactor in this channel. */
    internal fun setReactor(reactor: NioReactor) {
        this.reactor = reactor
    }

    /**
     * Get channel.
     *
     * @return the wrapped NIO channel.
     */
    open fun getJavaChannel(): SelectableChannel = channel

    /**
     * The operation in which the channel is interested, this operation is provided to [Selector].
     *
     * @return interested operation.
     * @see SelectionKey
     */
    abstract val interestedOps: Int

    /**
     * Binds the channel on provided port.
     *
     * @throws IOException if any I/O error occurs.
     */
    @Throws(IOException::class)
    abstract fun bind()

    /**
     * Reads the data using the key and returns the read data. The underlying channel should be
     * fetched using [SelectionKey.channel].
     *
     * @param key the key on which read event occurred.
     * @return data read.
     * @throws IOException if any I/O error occurs.
     */
    @Throws(IOException::class)
    abstract fun read(key: SelectionKey): Any

    /**
     * Called from the context of reactor thread when the key becomes writable. The channel writes the
     * whole pending block of data at once.
     */
    @Throws(IOException::class)
    internal fun flush(key: SelectionKey) {
        val pendingWrites = channelToPendingWrites[key.channel()]
        var pendingWrite: Any?
        while (pendingWrites?.poll().also { pendingWrite = it } != null) {
            // ask the concrete channel to make sense of data and write it to java channel
            doWrite(pendingWrite!!, key)
        }
        // We don't have anything more to write so channel is interested in reading more data
        reactor.changeOps(key, SelectionKey.OP_READ)
    }

    /**
     * Writes the data to the channel.
     *
     * @param pendingWrite the data to be written on channel.
     * @param key the key which is writable.
     * @throws IOException if any I/O error occurs.
     */
    @Throws(IOException::class)
    protected abstract fun doWrite(pendingWrite: Any, key: SelectionKey)

    /**
     * Queues the data for writing. The data is not guaranteed to be written on underlying channel
     * when this method returns. It will be written when the channel is flushed.
     *
     * This method is used by the [ChannelHandler] to send reply back to the client.
     *
     * Example:
     * ```
     * override fun handleChannelRead(channel: AbstractNioChannel, readObj: Any, key: SelectionKey) {
     *     val data = (readObj as ByteBuffer).array()
     *     val buffer = ByteBuffer.wrap("Server reply".toByteArray())
     *     channel.write(buffer, key)
     * }
     * ```
     *
     * @param data the data to be written on underlying channel.
     * @param key the key which is writable.
     */
    open fun write(data: Any, key: SelectionKey) {
        var pendingWrites = channelToPendingWrites[key.channel()]
        if (pendingWrites == null) {
            synchronized(channelToPendingWrites) {
                pendingWrites = channelToPendingWrites.computeIfAbsent(key.channel()) {
                    ConcurrentLinkedQueue()
                }
            }
        }
        pendingWrites!!.add(data)
        reactor.changeOps(key, SelectionKey.OP_WRITE)
    }
}
