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

// ABOUTME: Core service for lottery ticket submission and checking.
// ABOUTME: Handles player interactions with the lottery system.
package com.iluwatar.hexagonal.domain

import com.google.inject.Inject
import com.iluwatar.hexagonal.banking.WireTransfers
import com.iluwatar.hexagonal.database.LotteryTicketRepository
import com.iluwatar.hexagonal.eventlog.LotteryEventLog
import java.util.Optional

/**
 * Implementation for lottery service.
 */
class LotteryService @Inject constructor(
    private val repository: LotteryTicketRepository,
    private val notifications: LotteryEventLog,
    private val wireTransfers: WireTransfers
) {
    /**
     * Submit lottery ticket to participate in the lottery.
     */
    fun submitTicket(ticket: LotteryTicket): Optional<LotteryTicketId> {
        val playerDetails = ticket.playerDetails
        val playerAccount = playerDetails.bankAccount
        val result = wireTransfers.transferFunds(
            LotteryConstants.TICKET_PRIZE,
            playerAccount,
            LotteryConstants.SERVICE_BANK_ACCOUNT
        )
        if (!result) {
            notifications.ticketSubmitError(playerDetails)
            return Optional.empty()
        }
        val optional = repository.save(ticket)
        if (optional.isPresent) {
            notifications.ticketSubmitted(playerDetails)
        }
        return optional
    }

    /**
     * Check if lottery ticket has won.
     */
    fun checkTicketForPrize(id: LotteryTicketId, winningNumbers: LotteryNumbers): LotteryTicketCheckResult =
        LotteryUtils.checkTicketForPrize(repository, id, winningNumbers)
}
