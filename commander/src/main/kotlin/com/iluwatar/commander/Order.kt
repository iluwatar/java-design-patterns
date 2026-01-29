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

// ABOUTME: Order class holds details of the order.
// ABOUTME: Tracks payment status, message sent status, and employee handle addition.
package com.iluwatar.commander

import java.security.SecureRandom

class Order(
    val user: User,
    val item: String,
    val price: Float,
) {
    enum class PaymentStatus {
        NOT_DONE,
        TRYING,
        DONE,
    }

    enum class MessageSent {
        NONE_SENT,
        PAYMENT_FAIL,
        PAYMENT_TRYING,
        PAYMENT_SUCCESSFUL,
    }

    val id: String
    val createdTime: Long = System.currentTimeMillis()
    var paid: PaymentStatus = PaymentStatus.TRYING
    var messageSent: MessageSent = MessageSent.NONE_SENT
    var addedToEmployeeHandle: Boolean = false

    init {
        var generatedId = createUniqueId()
        while (USED_IDS[generatedId] == true) {
            generatedId = createUniqueId()
        }
        this.id = generatedId
        USED_IDS[this.id] = true
    }

    private fun createUniqueId(): String {
        val random = StringBuilder()
        while (random.length < 12) {
            val index = (RANDOM.nextFloat() * ALL_CHARS.length).toInt()
            random.append(ALL_CHARS[index])
        }
        return random.toString()
    }

    companion object {
        private val RANDOM = SecureRandom()
        private const val ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        private val USED_IDS = HashMap<String, Boolean>()
    }
}