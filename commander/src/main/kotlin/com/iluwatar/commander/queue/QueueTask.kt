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

// ABOUTME: QueueTask object is the object enqueued in queue.
// ABOUTME: Contains order, task type, and message type information for processing.
package com.iluwatar.commander.queue

import com.iluwatar.commander.Order

class QueueTask(
    val order: Order,
    val taskType: TaskType,
    val messageType: Int,
) {
    enum class TaskType {
        MESSAGING,
        PAYMENT,
        EMPLOYEE_DB,
    }

    var firstAttemptTime: Long = -1L

    fun getType(): String =
        if (taskType != TaskType.MESSAGING) {
            taskType.toString()
        } else {
            when (messageType) {
                0 -> "Payment Failure Message"
                1 -> "Payment Error Message"
                else -> "Payment Success Message"
            }
        }

    fun isFirstAttempt(): Boolean = firstAttemptTime == -1L
}