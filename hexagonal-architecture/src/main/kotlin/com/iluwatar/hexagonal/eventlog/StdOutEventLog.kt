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

// ABOUTME: Standard output implementation of the event log adapter.
// ABOUTME: Logs lottery events to console for development and testing.
package com.iluwatar.hexagonal.eventlog

import com.iluwatar.hexagonal.domain.PlayerDetails
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Standard output event log.
 */
class StdOutEventLog : LotteryEventLog {

    override fun ticketSubmitted(details: PlayerDetails) {
        logger.info {
            "Lottery ticket for ${details.email} was submitted. Bank account ${details.bankAccount} was charged for 3 credits."
        }
    }

    override fun ticketDidNotWin(details: PlayerDetails) {
        logger.info {
            "Lottery ticket for ${details.email} was checked and unfortunately did not win this time."
        }
    }

    override fun ticketWon(details: PlayerDetails, prizeAmount: Int) {
        logger.info {
            "Lottery ticket for ${details.email} has won! The bank account ${details.bankAccount} was deposited with $prizeAmount credits."
        }
    }

    override fun prizeError(details: PlayerDetails, prizeAmount: Int) {
        logger.error {
            "Lottery ticket for ${details.email} has won! Unfortunately the bank credit transfer of $prizeAmount failed."
        }
    }

    override fun ticketSubmitError(details: PlayerDetails) {
        logger.error {
            "Lottery ticket for ${details.email} could not be submitted because the credit transfer of 3 credits failed."
        }
    }
}
