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

// ABOUTME: Console application entry point for lottery administration.
// ABOUTME: Provides interactive menu for administrators to manage lottery rounds.
package com.iluwatar.hexagonal.administration

import com.google.inject.Guice
import com.iluwatar.hexagonal.domain.LotteryAdministration
import com.iluwatar.hexagonal.domain.LotteryService
import com.iluwatar.hexagonal.module.LotteryModule
import com.iluwatar.hexagonal.mongo.MongoConnectionPropertiesLoader
import com.iluwatar.hexagonal.sampledata.SampleData
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.Scanner

private val logger = KotlinLogging.logger {}

/**
 * Console interface for lottery administration.
 */
object ConsoleAdministration {

    /**
     * Program entry point.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        MongoConnectionPropertiesLoader.load()
        val injector = Guice.createInjector(LotteryModule())
        val administration = injector.getInstance(LotteryAdministration::class.java)
        val service = injector.getInstance(LotteryService::class.java)
        SampleData.submitTickets(service, 20)
        val consoleAdministration = ConsoleAdministrationSrvImpl(
            administration,
            org.slf4j.LoggerFactory.getLogger(ConsoleAdministration::class.java)
        )
        Scanner(System.`in`).use { scanner ->
            var exit = false
            while (!exit) {
                printMainMenu()
                val cmd = readString(scanner)
                when (cmd) {
                    "1" -> consoleAdministration.getAllSubmittedTickets()
                    "2" -> consoleAdministration.performLottery()
                    "3" -> consoleAdministration.resetLottery()
                    "4" -> exit = true
                    else -> logger.info { "Unknown command: $cmd" }
                }
            }
        }
    }

    private fun printMainMenu() {
        logger.info { "" }
        logger.info { "### Lottery Administration Console ###" }
        logger.info { "(1) Show all submitted tickets" }
        logger.info { "(2) Perform lottery draw" }
        logger.info { "(3) Reset lottery ticket database" }
        logger.info { "(4) Exit" }
    }

    private fun readString(scanner: Scanner): String {
        logger.info { "> " }
        return scanner.next()
    }
}
