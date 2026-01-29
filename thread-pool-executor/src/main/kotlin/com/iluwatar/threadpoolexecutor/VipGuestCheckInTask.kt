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

// ABOUTME: Represents a VIP guest check-in task as a Callable returning a confirmation string.
// ABOUTME: Simulates the VIP check-in process with a 1-second delay.
package com.iluwatar.threadpoolexecutor

import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.concurrent.Callable

private val logger = KotlinLogging.logger {}

/**
 * VipGuestCheckInTask represents a VIP guest check-in process. Implements Callable because it
 * returns a result (check-in confirmation).
 *
 * @property vipGuestName the name of the VIP guest to check in
 */
class VipGuestCheckInTask(private val vipGuestName: String) : Callable<String> {

    @Throws(Exception::class)
    override fun call(): String {
        val employeeName = Thread.currentThread().name
        logger.info { "$employeeName is checking in VIP guest $vipGuestName..." }

        Thread.sleep(1000)

        val result = "$vipGuestName has been successfully checked in!"
        logger.info { "VIP check-in completed: $result" }
        return result
    }
}
