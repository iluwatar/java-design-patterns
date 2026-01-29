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

// ABOUTME: A Data-Bus member that tracks application start/stop status.
// ABOUTME: Records timestamps and sends goodbye messages when the application stops.

package com.iluwatar.databus.members

import com.iluwatar.databus.DataType
import com.iluwatar.databus.Member
import com.iluwatar.databus.data.MessageData
import com.iluwatar.databus.data.StartingData
import com.iluwatar.databus.data.StoppingData
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

/**
 * Receiver of Data-Bus events.
 */
class StatusMember(val id: Int) : Member {

    var started: LocalDateTime? = null
        private set

    var stopped: LocalDateTime? = null
        private set

    override fun accept(data: DataType) {
        when (data) {
            is StartingData -> handleEvent(data)
            is StoppingData -> handleEvent(data)
        }
    }

    private fun handleEvent(data: StartingData) {
        started = data.`when`
        logger.info { "Receiver $id sees application started at $started" }
    }

    private fun handleEvent(data: StoppingData) {
        stopped = data.`when`
        logger.info { "Receiver $id sees application stopping at $stopped" }
        logger.info { "Receiver $id sending goodbye message" }
        data.dataBus?.publish(MessageData.of("Goodbye cruel world from #$id!"))
    }
}
