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

// ABOUTME: The MessagingService sends messages to user regarding their order and payment status.
// ABOUTME: Handles payment success, failure, and error notifications.
package com.iluwatar.commander.messagingservice

import com.iluwatar.commander.Service
import com.iluwatar.commander.exceptions.DatabaseUnavailableException
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

class MessagingService(
    db: MessagingDatabase?,
    vararg exc: Exception,
) : Service(db, *exc) {
    enum class MessageToSend {
        PAYMENT_FAIL,
        PAYMENT_TRYING,
        PAYMENT_SUCCESSFUL,
    }

    data class MessageRequest(
        val reqId: String,
        val msg: MessageToSend,
    )

    @Throws(DatabaseUnavailableException::class)
    override fun receiveRequest(vararg parameters: Any?): String? {
        val messageToSend = parameters[0] as Int
        val id = generateId()
        val msg =
            when (messageToSend) {
                0 -> MessageToSend.PAYMENT_FAIL
                1 -> MessageToSend.PAYMENT_TRYING
                else -> MessageToSend.PAYMENT_SUCCESSFUL
            }
        val req = MessageRequest(id, msg)
        return updateDb(req)
    }

    @Throws(DatabaseUnavailableException::class)
    @Suppress("UNCHECKED_CAST")
    override fun updateDb(vararg parameters: Any?): String? {
        val req = parameters[0] as MessageRequest
        if ((database as MessagingDatabase?)?.get(req.reqId) == null) {
            database?.add(req)
            logger.info { sendMessage(req.msg) }
            return req.reqId
        }
        return null
    }

    internal fun sendMessage(m: MessageToSend): String =
        when (m) {
            MessageToSend.PAYMENT_SUCCESSFUL ->
                "Msg: Your order has been placed and paid for successfully! Thank you for shopping with us!"
            MessageToSend.PAYMENT_TRYING ->
                "Msg: There was an error in your payment process, we are working on it and will return back to you shortly. Meanwhile, your order has been placed and will be shipped."
            MessageToSend.PAYMENT_FAIL ->
                "Msg: There was an error in your payment process. Your order is placed and has been converted to COD. Please reach us on CUSTOMER-CARE-NUBER in case of any queries. Thank you for shopping with us!"
        }
}