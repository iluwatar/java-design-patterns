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

// ABOUTME: Core administration service for the lottery domain.
// ABOUTME: Handles lottery operations like drawing numbers, checking winners, and resetting rounds.
package com.iluwatar.hexagonal.domain

import com.google.inject.Inject
import com.iluwatar.hexagonal.banking.WireTransfers
import com.iluwatar.hexagonal.database.LotteryTicketRepository
import com.iluwatar.hexagonal.eventlog.LotteryEventLog

/**
 * Lottery administration implementation.
 */
class LotteryAdministration @Inject constructor(
    private val repository: LotteryTicketRepository,
    private val notifications: LotteryEventLog,
    private val wireTransfers: WireTransfers
) {
    /**
     * Get all the lottery tickets submitted for lottery.
     */
    fun getAllSubmittedTickets(): Map<LotteryTicketId, LotteryTicket> = repository.findAll()

    /**
     * Draw lottery numbers.
     */
    fun performLottery(): LotteryNumbers {
        val numbers = LotteryNumbers.createRandom()
        val tickets = getAllSubmittedTickets()
        for (id in tickets.keys) {
            val lotteryTicket = tickets[id]!!
            val playerDetails = lotteryTicket.playerDetails
            val playerAccount = playerDetails.bankAccount
            val result = LotteryUtils.checkTicketForPrize(repository, id, numbers).result
            if (result == LotteryTicketCheckResult.CheckResult.WIN_PRIZE) {
                if (wireTransfers.transferFunds(
                        LotteryConstants.PRIZE_AMOUNT,
                        LotteryConstants.SERVICE_BANK_ACCOUNT,
                        playerAccount
                    )
                ) {
                    notifications.ticketWon(playerDetails, LotteryConstants.PRIZE_AMOUNT)
                } else {
                    notifications.prizeError(playerDetails, LotteryConstants.PRIZE_AMOUNT)
                }
            } else if (result == LotteryTicketCheckResult.CheckResult.NO_PRIZE) {
                notifications.ticketDidNotWin(playerDetails)
            }
        }
        return numbers
    }

    /**
     * Begin new lottery round.
     */
    fun resetLottery() {
        repository.deleteAll()
    }
}
