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

// ABOUTME: Application-specific handler that logs incoming requests and sends acknowledgements.
// ABOUTME: Demonstrates handling both TCP (ByteBuffer) and UDP (DatagramPacket) data.
package com.iluwatar.reactor.app

import com.iluwatar.reactor.framework.AbstractNioChannel
import com.iluwatar.reactor.framework.ChannelHandler
import com.iluwatar.reactor.framework.NioDatagramChannel.DatagramPacket
import io.github.oshai.kotlinlogging.KotlinLogging
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey

private val logger = KotlinLogging.logger {}

/**
 * Logging server application logic. It logs the incoming requests on standard console and returns a
 * canned acknowledgement back to the remote peer.
 */
class LoggingHandler : ChannelHandler {

    companion object {
        private val ACK = "Data logged successfully".toByteArray()
    }

    /** Decodes the received data and logs it on standard console. */
    override fun handleChannelRead(channel: AbstractNioChannel, readObject: Any, key: SelectionKey) {
        /*
         * As this handler is attached with both TCP and UDP channels we need to check whether the data
         * received is a ByteBuffer (from TCP channel) or a DatagramPacket (from UDP channel).
         */
        when (readObject) {
            is ByteBuffer -> {
                doLogging(readObject)
                sendReply(channel, key)
            }
            is DatagramPacket -> {
                doLogging(readObject.data)
                sendReply(channel, readObject, key)
            }
            else -> throw IllegalStateException("Unknown data received")
        }
    }

    private fun sendReply(channel: AbstractNioChannel, incomingPacket: DatagramPacket, key: SelectionKey) {
        /*
         * Create a reply acknowledgement datagram packet setting the receiver to the sender of incoming
         * message.
         */
        val replyPacket = DatagramPacket(ByteBuffer.wrap(ACK)).apply {
            receiver = incomingPacket.sender
        }

        channel.write(replyPacket, key)
    }

    private fun sendReply(channel: AbstractNioChannel, key: SelectionKey) {
        val buffer = ByteBuffer.wrap(ACK)
        channel.write(buffer, key)
    }

    private fun doLogging(data: ByteBuffer) {
        // assuming UTF-8 :(
        logger.info { String(data.array(), 0, data.limit()) }
    }
}
