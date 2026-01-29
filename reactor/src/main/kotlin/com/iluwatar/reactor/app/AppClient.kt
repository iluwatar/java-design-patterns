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

// ABOUTME: Client application for testing the Reactor pattern with concurrent logging requests.
// ABOUTME: Includes TCP and UDP clients that send requests and receive acknowledgements.
package com.iluwatar.reactor.app

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.IOException
import java.io.InputStream
import java.io.PrintWriter
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private val logger = KotlinLogging.logger {}

/**
 * Represents the clients of Reactor pattern. Multiple clients are run concurrently and send logging
 * requests to Reactor.
 */
class AppClient {

    private val service: ExecutorService = Executors.newFixedThreadPool(4)

    /**
     * Starts the logging clients.
     *
     * @throws IOException if any I/O error occurs.
     */
    @Throws(IOException::class)
    fun start() {
        logger.info { "Starting logging clients" }
        service.execute(TcpLoggingClient("Client 1", 16666))
        service.execute(TcpLoggingClient("Client 2", 16667))
        service.execute(UdpLoggingClient("Client 3", 16668))
        service.execute(UdpLoggingClient("Client 4", 16669))
    }

    /** Stops logging clients. This is a blocking call. */
    fun stop() {
        service.shutdown()
        if (!service.isTerminated) {
            service.shutdownNow()
            try {
                service.awaitTermination(1000, TimeUnit.SECONDS)
            } catch (e: InterruptedException) {
                logger.error(e) { "exception awaiting termination" }
            }
        }
        logger.info { "Logging clients stopped" }
    }

    /** A logging client that sends requests to Reactor on TCP socket. */
    private class TcpLoggingClient(
        private val clientName: String,
        private val serverPort: Int
    ) : Runnable {

        override fun run() {
            try {
                Socket(InetAddress.getLocalHost(), serverPort).use { socket ->
                    val outputStream = socket.getOutputStream()
                    val writer = PrintWriter(outputStream)
                    sendLogRequests(writer, socket.getInputStream())
                }
            } catch (e: IOException) {
                logger.error(e) { "error sending requests" }
                throw RuntimeException(e)
            }
        }

        @Throws(IOException::class)
        private fun sendLogRequests(writer: PrintWriter, inputStream: InputStream) {
            for (i in 0 until 4) {
                writer.println("$clientName - Log request: $i")
                writer.flush()

                val data = ByteArray(1024)
                val read = inputStream.read(data, 0, data.size)
                if (read == 0) {
                    logger.info { "Read zero bytes" }
                } else {
                    logger.info { String(data, 0, read) }
                }

                artificialDelayOf(100)
            }
        }
    }

    /** A logging client that sends requests to Reactor on UDP socket. */
    private class UdpLoggingClient(
        private val clientName: String,
        port: Int
    ) : Runnable {
        private val remoteAddress: InetSocketAddress = InetSocketAddress(InetAddress.getLocalHost(), port)

        override fun run() {
            DatagramSocket().use { socket ->
                for (i in 0 until 4) {
                    val message = "$clientName - Log request: $i"
                    val bytes = message.toByteArray()
                    val request = DatagramPacket(bytes, bytes.size, remoteAddress)

                    socket.send(request)

                    val data = ByteArray(1024)
                    val reply = DatagramPacket(data, data.size)
                    socket.receive(reply)
                    if (reply.length == 0) {
                        logger.info { "Read zero bytes" }
                    } else {
                        logger.info { String(reply.data, 0, reply.length) }
                    }

                    artificialDelayOf(100)
                }
            }
        }
    }

    companion object {
        private fun artificialDelayOf(millis: Long) {
            try {
                Thread.sleep(millis)
            } catch (e: InterruptedException) {
                logger.error(e) { "sleep interrupted" }
            }
        }

        /**
         * App client entry.
         *
         * @throws IOException if any I/O error occurs.
         */
        @JvmStatic
        @Throws(IOException::class)
        fun main(args: Array<String>) {
            val appClient = AppClient()
            appClient.start()
        }
    }
}
