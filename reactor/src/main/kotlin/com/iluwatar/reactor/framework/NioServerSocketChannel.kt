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

// ABOUTME: Wrapper over NIO ServerSocketChannel for TCP communication in the Reactor pattern.
// ABOUTME: Handles accepting connections and reading/writing data on socket channels.
package com.iluwatar.reactor.framework

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

private val logger = KotlinLogging.logger {}

/**
 * A wrapper over [ServerSocketChannel] which can read and write data on a [SocketChannel].
 */
class NioServerSocketChannel
@Throws(IOException::class)
constructor(
    private val port: Int,
    handler: ChannelHandler
) : AbstractNioChannel(handler, ServerSocketChannel.open()) {

    /**
     * Being a server socket channel it is interested in accepting connection from remote peers.
     */
    override val interestedOps: Int
        get() = SelectionKey.OP_ACCEPT

    /**
     * Get server socket channel.
     *
     * @return the underlying [ServerSocketChannel].
     */
    override fun getJavaChannel(): ServerSocketChannel = super.getJavaChannel() as ServerSocketChannel

    /**
     * Reads and returns [ByteBuffer] from the underlying [SocketChannel] represented by
     * the [key]. Due to the fact that there is a dedicated channel for each client
     * connection we don't need to store the sender.
     */
    @Throws(IOException::class)
    override fun read(key: SelectionKey): ByteBuffer {
        val socketChannel = key.channel() as SocketChannel
        val buffer = ByteBuffer.allocate(1024)
        val read = socketChannel.read(buffer)
        buffer.flip()
        if (read == -1) {
            throw IOException("Socket closed")
        }
        return buffer
    }

    /**
     * Binds TCP socket on the provided [port].
     *
     * @throws IOException if any I/O error occurs.
     */
    @Throws(IOException::class)
    override fun bind() {
        val javaChannel = getJavaChannel()
        javaChannel.socket().bind(InetSocketAddress(InetAddress.getLocalHost(), port))
        javaChannel.configureBlocking(false)
        logger.info { "Bound TCP socket at port: $port" }
    }

    /**
     * Writes the pending [ByteBuffer] to the underlying channel sending data to the intended
     * receiver of the packet.
     */
    @Throws(IOException::class)
    override fun doWrite(pendingWrite: Any, key: SelectionKey) {
        val pendingBuffer = pendingWrite as ByteBuffer
        (key.channel() as SocketChannel).write(pendingBuffer)
    }
}
