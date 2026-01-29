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

// ABOUTME: Wrapper over NIO DatagramChannel for UDP communication in the Reactor pattern.
// ABOUTME: Handles reading and writing UDP datagrams with sender/receiver address preservation.
package com.iluwatar.reactor.framework

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel
import java.nio.channels.SelectionKey

private val logger = KotlinLogging.logger {}

/**
 * A wrapper over [DatagramChannel] which can read and write data on a DatagramChannel.
 */
class NioDatagramChannel
@Throws(IOException::class)
constructor(
    private val port: Int,
    handler: ChannelHandler
) : AbstractNioChannel(handler, DatagramChannel.open()) {

    /**
     * There is no need to accept connections in UDP, so the channel shows interest in reading data.
     */
    override val interestedOps: Int
        get() = SelectionKey.OP_READ

    /**
     * Reads and returns a [DatagramPacket] from the underlying channel.
     *
     * @return the datagram packet read having the sender address.
     */
    @Throws(IOException::class)
    override fun read(key: SelectionKey): DatagramPacket {
        val buffer = ByteBuffer.allocate(1024)
        val sender = (key.channel() as DatagramChannel).receive(buffer)

        /*
         * It is required to create a DatagramPacket because we need to preserve which socket address
         * acts as destination for sending reply packets.
         */
        buffer.flip()
        return DatagramPacket(buffer).apply {
            this.sender = sender
        }
    }

    /**
     * Get datagram channel.
     *
     * @return the underlying datagram channel.
     */
    override fun getJavaChannel(): DatagramChannel = super.getJavaChannel() as DatagramChannel

    /**
     * Binds UDP socket on the provided [port].
     *
     * @throws IOException if any I/O error occurs.
     */
    @Throws(IOException::class)
    override fun bind() {
        getJavaChannel().socket().bind(InetSocketAddress(InetAddress.getLocalHost(), port))
        getJavaChannel().configureBlocking(false)
        logger.info { "Bound UDP socket at port: $port" }
    }

    /**
     * Writes the pending [DatagramPacket] to the underlying channel sending data to the
     * intended receiver of the packet.
     */
    @Throws(IOException::class)
    override fun doWrite(pendingWrite: Any, key: SelectionKey) {
        val pendingPacket = pendingWrite as DatagramPacket
        getJavaChannel().send(pendingPacket.data, pendingPacket.receiver)
    }

    /**
     * Writes the outgoing [DatagramPacket] to the channel. The intended receiver of the
     * datagram packet must be set in the [data] using [DatagramPacket.receiver].
     */
    override fun write(data: Any, key: SelectionKey) {
        super.write(data, key)
    }

    /**
     * Container of data used for [NioDatagramChannel] to communicate with remote peer.
     */
    data class DatagramPacket(
        val data: ByteBuffer,
        var sender: SocketAddress? = null,
        var receiver: SocketAddress? = null
    )
}
