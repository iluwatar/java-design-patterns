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
package com.iluwatar.leaderelection

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

// ABOUTME: Abstract base class for all instance implementations in the leader election system.
// ABOUTME: Provides message queue handling and dispatches messages to type-specific handlers.

private val logger = KotlinLogging.logger {}

/**
 * Abstract class of all the instance implementation classes.
 */
abstract class AbstractInstance(
    internal var messageManager: MessageManager?,
    internal val localId: Int,
    internal var leaderId: Int
) : Instance, Runnable {

    internal val messageQueue: Queue<Message> = ConcurrentLinkedQueue()
    internal var alive: Boolean = true

    /**
     * The instance will execute the message in its message queue periodically once it is alive.
     */
    @Suppress("squid:S2189")
    override fun run() {
        while (true) {
            if (messageQueue.isNotEmpty()) {
                processMessage(messageQueue.remove())
            }
        }
    }

    /**
     * Once messages are sent to the certain instance, it will firstly be added to the queue and wait
     * to be executed.
     *
     * @param message Message sent by other instances
     */
    override fun onMessage(message: Message) {
        messageQueue.offer(message)
    }

    /**
     * Check if the instance is alive or not.
     *
     * @return `true` if the instance is alive.
     */
    override fun isAlive(): Boolean = alive

    /**
     * Set the health status of the certain instance.
     *
     * @param alive `true` for alive.
     */
    override fun setAlive(alive: Boolean) {
        this.alive = alive
    }

    /**
     * Process the message according to its type.
     *
     * @param message Message polled from queue.
     */
    private fun processMessage(message: Message) {
        when (message.type) {
            MessageType.ELECTION -> {
                logger.info { "$INSTANCE$localId - Election Message handling..." }
                handleElectionMessage(message)
            }
            MessageType.LEADER -> {
                logger.info { "$INSTANCE$localId - Leader Message handling..." }
                handleLeaderMessage(message)
            }
            MessageType.HEARTBEAT -> {
                logger.info { "$INSTANCE$localId - Heartbeat Message handling..." }
                handleHeartbeatMessage(message)
            }
            MessageType.ELECTION_INVOKE -> {
                logger.info { "$INSTANCE$localId - Election Invoke Message handling..." }
                handleElectionInvokeMessage()
            }
            MessageType.LEADER_INVOKE -> {
                logger.info { "$INSTANCE$localId - Leader Invoke Message handling..." }
                handleLeaderInvokeMessage()
            }
            MessageType.HEARTBEAT_INVOKE -> {
                logger.info { "$INSTANCE$localId - Heartbeat Invoke Message handling..." }
                handleHeartbeatInvokeMessage()
            }
            else -> {}
        }
    }

    /**
     * Abstract methods to handle different types of message. These methods need to be implemented in
     * concrete instance class to implement corresponding leader-selection pattern.
     */
    internal abstract fun handleElectionMessage(message: Message)

    internal abstract fun handleElectionInvokeMessage()

    internal abstract fun handleLeaderMessage(message: Message)

    internal abstract fun handleLeaderInvokeMessage()

    internal abstract fun handleHeartbeatMessage(message: Message)

    internal abstract fun handleHeartbeatInvokeMessage()

    companion object {
        internal const val HEARTBEAT_INTERVAL = 5000
        private const val INSTANCE = "Instance "
    }
}
