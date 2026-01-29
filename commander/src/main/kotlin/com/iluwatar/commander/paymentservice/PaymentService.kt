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

// ABOUTME: The PaymentService class receives request from Commander and adds to the PaymentDatabase.
// ABOUTME: Handles payment processing and transaction management.
package com.iluwatar.commander.paymentservice

import com.iluwatar.commander.Service
import com.iluwatar.commander.exceptions.DatabaseUnavailableException

class PaymentService(
    db: PaymentDatabase?,
    vararg exc: Exception,
) : Service(db, *exc) {
    class PaymentRequest(
        val transactionId: String,
        val payment: Float,
    ) {
        var paid: Boolean = false
    }

    @Throws(DatabaseUnavailableException::class)
    override fun receiveRequest(vararg parameters: Any?): String? {
        val id = generateId()
        val req = PaymentRequest(id, parameters[0] as Float)
        return updateDb(req)
    }

    @Throws(DatabaseUnavailableException::class)
    @Suppress("UNCHECKED_CAST")
    override fun updateDb(vararg parameters: Any?): String? {
        val req = parameters[0] as PaymentRequest
        if ((database as PaymentDatabase?)?.get(req.transactionId) == null || !req.paid) {
            database?.add(req)
            req.paid = true
            return req.transactionId
        }
        return null
    }
}