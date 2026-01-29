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

// ABOUTME: Console application entry point for lottery players.
// ABOUTME: Provides interactive menu for players to submit tickets and check results.
package com.iluwatar.hexagonal.service

import com.google.inject.Guice
import com.iluwatar.hexagonal.banking.WireTransfers
import com.iluwatar.hexagonal.domain.LotteryService
import com.iluwatar.hexagonal.module.LotteryModule
import com.iluwatar.hexagonal.mongo.MongoConnectionPropertiesLoader
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.Scanner

private val logger = KotlinLogging.logger {}

/**
 * Console interface for lottery players.
 */
object ConsoleLottery {

    /**
     * Program entry point.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        MongoConnectionPropertiesLoader.load()
        val injector = Guice.createInjector(LotteryModule())
        val service = injector.getInstance(LotteryService::class.java)
        val bank = injector.getInstance(WireTransfers::class.java)
        Scanner(System.`in`).use { scanner ->
            var exit = false
            while (!exit) {
                printMainMenu()
                val cmd = readString(scanner)
                val lotteryConsoleService = LotteryConsoleServiceImpl(
                    org.slf4j.LoggerFactory.getLogger(ConsoleLottery::class.java)
                )
                when (cmd) {
                    "1" -> lotteryConsoleService.queryLotteryAccountFunds(bank, scanner)
                    "2" -> lotteryConsoleService.addFundsToLotteryAccount(bank, scanner)
                    "3" -> lotteryConsoleService.submitTicket(service, scanner)
                    "4" -> lotteryConsoleService.checkTicket(service, scanner)
                    "5" -> exit = true
                    else -> logger.info { "Unknown command" }
                }
            }
        }
    }

    private fun printMainMenu() {
        logger.info { "" }
        logger.info { "### Lottery Service Console ###" }
        logger.info { "(1) Query lottery account funds" }
        logger.info { "(2) Add funds to lottery account" }
        logger.info { "(3) Submit ticket" }
        logger.info { "(4) Check ticket" }
        logger.info { "(5) Exit" }
    }

    private fun readString(scanner: Scanner): String {
        logger.info { "> " }
        return scanner.next()
    }
}
