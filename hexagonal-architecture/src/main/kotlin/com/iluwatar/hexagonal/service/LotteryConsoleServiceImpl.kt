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

// ABOUTME: Implementation of console lottery service for player interactions.
// ABOUTME: Handles ticket submission, fund management, and result checking via console.
package com.iluwatar.hexagonal.service

import com.iluwatar.hexagonal.banking.WireTransfers
import com.iluwatar.hexagonal.domain.LotteryNumbers
import com.iluwatar.hexagonal.domain.LotteryService
import com.iluwatar.hexagonal.domain.LotteryTicket
import com.iluwatar.hexagonal.domain.LotteryTicketCheckResult
import com.iluwatar.hexagonal.domain.LotteryTicketId
import com.iluwatar.hexagonal.domain.PlayerDetails
import org.slf4j.Logger
import java.util.Scanner

/**
 * Console implementation for lottery console service.
 */
class LotteryConsoleServiceImpl(private val logger: Logger) : LotteryConsoleService {

    override fun checkTicket(service: LotteryService, scanner: Scanner) {
        logger.info("What is the ID of the lottery ticket?")
        val id = readString(scanner)
        logger.info("Give the 4 comma separated winning numbers?")
        val numbers = readString(scanner)
        try {
            val winningNumbers = numbers.split(",")
                .map { it.toInt() }
                .take(4)
                .toSet()

            val lotteryTicketId = LotteryTicketId(id.toInt())
            val lotteryNumbers = LotteryNumbers.create(winningNumbers)
            val result = service.checkTicketForPrize(lotteryTicketId, lotteryNumbers)

            when (result.result) {
                LotteryTicketCheckResult.CheckResult.WIN_PRIZE ->
                    logger.info("Congratulations! The lottery ticket has won!")
                LotteryTicketCheckResult.CheckResult.NO_PRIZE ->
                    logger.info("Unfortunately the lottery ticket did not win.")
                else ->
                    logger.info("Such lottery ticket has not been submitted.")
            }
        } catch (e: Exception) {
            logger.info("Failed checking the lottery ticket - please try again.")
        }
    }

    override fun submitTicket(service: LotteryService, scanner: Scanner) {
        logger.info("What is your email address?")
        val email = readString(scanner)
        logger.info("What is your bank account number?")
        val account = readString(scanner)
        logger.info("What is your phone number?")
        val phone = readString(scanner)
        val details = PlayerDetails(email, account, phone)
        logger.info("Give 4 comma separated lottery numbers?")
        val numbers = readString(scanner)
        try {
            val chosen = numbers.split(",")
                .map { it.toInt() }
                .toSet()
            val lotteryNumbers = LotteryNumbers.create(chosen)
            val lotteryTicket = LotteryTicket(LotteryTicketId(), details, lotteryNumbers)
            service.submitTicket(lotteryTicket).ifPresentOrElse(
                { id -> logger.info("Submitted lottery ticket with id: {}", id) },
                { logger.info("Failed submitting lottery ticket - please try again.") }
            )
        } catch (e: Exception) {
            logger.info("Failed submitting lottery ticket - please try again.")
        }
    }

    override fun addFundsToLotteryAccount(bank: WireTransfers, scanner: Scanner) {
        logger.info("What is the account number?")
        val account = readString(scanner)
        logger.info("How many credits do you want to deposit?")
        val amount = readString(scanner)
        bank.setFunds(account, amount.toInt())
        logger.info("The account {} now has {} credits.", account, bank.getFunds(account))
    }

    override fun queryLotteryAccountFunds(bank: WireTransfers, scanner: Scanner) {
        logger.info("What is the account number?")
        val account = readString(scanner)
        logger.info("The account {} has {} credits.", account, bank.getFunds(account))
    }

    private fun readString(scanner: Scanner): String {
        logger.info("> ")
        return scanner.next()
    }
}
