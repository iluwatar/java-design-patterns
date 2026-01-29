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

// ABOUTME: ShippingService class receives request from Commander and adds it to ShippingDatabase.
// ABOUTME: Handles shipping request processing and database updates.
package com.iluwatar.commander.shippingservice

import com.iluwatar.commander.Service
import com.iluwatar.commander.exceptions.DatabaseUnavailableException

class ShippingService(
    db: ShippingDatabase?,
    vararg exc: Exception,
) : Service(db, *exc) {
    class ShippingRequest(
        val transactionId: String,
        val item: String,
        val address: String?,
    )

    @Throws(DatabaseUnavailableException::class)
    override fun receiveRequest(vararg parameters: Any?): String? {
        val id = generateId()
        val item = parameters[0] as String
        val address = parameters[1] as String?
        val req = ShippingRequest(id, item, address)
        return updateDb(req)
    }

    @Throws(DatabaseUnavailableException::class)
    @Suppress("UNCHECKED_CAST")
    override fun updateDb(vararg parameters: Any?): String? {
        val req = parameters[0] as ShippingRequest
        if ((database as ShippingDatabase?)?.get(req.transactionId) == null) {
            database?.add(req)
            return req.transactionId
        }
        return null
    }
}