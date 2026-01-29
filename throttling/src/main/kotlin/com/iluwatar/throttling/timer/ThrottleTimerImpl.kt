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
package com.iluwatar.throttling.timer

import com.iluwatar.throttling.CallsCount
import java.util.Timer
import java.util.TimerTask

// ABOUTME: Implementation of the Throttler interface using a Timer.
// ABOUTME: Resets the call counter at specified intervals to enforce throttling limits.

/**
 * Implementation of throttler interface. This class resets the counter every second.
 */
class ThrottleTimerImpl(
    private val throttlePeriod: Int,
    private val callsCount: CallsCount
) : Throttler {

    /**
     * A timer is initiated with this method. The timer runs every second and resets the counter.
     */
    override fun start() {
        Timer(true).schedule(
            object : TimerTask() {
                override fun run() {
                    callsCount.reset()
                }
            },
            0,
            throttlePeriod.toLong()
        )
    }
}
