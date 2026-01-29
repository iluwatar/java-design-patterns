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

// ABOUTME: Integration tests for the complete lottery system.
// ABOUTME: Tests ticket submission, lottery drawing, and prize checking.
package com.iluwatar.hexagonal.domain

import com.google.inject.Guice
import com.google.inject.Inject
import com.google.inject.Injector
import com.iluwatar.hexagonal.banking.WireTransfers
import com.iluwatar.hexagonal.module.LotteryTestingModule
import com.iluwatar.hexagonal.test.LotteryTestUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test the lottery system
 */
class LotteryTest {

    private val injector: Injector = Guice.createInjector(LotteryTestingModule())

    @Inject
    private lateinit var administration: LotteryAdministration

    @Inject
    private lateinit var service: LotteryService

    @Inject
    private lateinit var wireTransfers: WireTransfers

    @BeforeEach
    fun setup() {
        injector.injectMembers(this)
        // add funds to the test player's bank account
        wireTransfers.setFunds("123-12312", 100)
    }

    @Test
    fun testLottery() {
        // admin resets the lottery
        administration.resetLottery()
        assertEquals(0, administration.getAllSubmittedTickets().size)

        // players submit the lottery tickets
        val ticket1 = service.submitTicket(
            LotteryTestUtils.createLotteryTicket(
                "cvt@bbb.com", "123-12312", "+32425255", setOf(1, 2, 3, 4)
            )
        )
        assertTrue(ticket1.isPresent)
        val ticket2 = service.submitTicket(
            LotteryTestUtils.createLotteryTicket(
                "ant@bac.com", "123-12312", "+32423455", setOf(11, 12, 13, 14)
            )
        )
        assertTrue(ticket2.isPresent)
        val ticket3 = service.submitTicket(
            LotteryTestUtils.createLotteryTicket(
                "arg@boo.com", "123-12312", "+32421255", setOf(6, 8, 13, 19)
            )
        )
        assertTrue(ticket3.isPresent)
        assertEquals(3, administration.getAllSubmittedTickets().size)

        // perform lottery
        val winningNumbers = administration.performLottery()

        // cheat a bit for testing sake, use winning numbers to submit another ticket
        val ticket4 = service.submitTicket(
            LotteryTestUtils.createLotteryTicket(
                "lucky@orb.com", "123-12312", "+12421255", winningNumbers.getNumbers()
            )
        )
        assertTrue(ticket4.isPresent)
        assertEquals(4, administration.getAllSubmittedTickets().size)

        // check winners
        val tickets = administration.getAllSubmittedTickets()
        for (id in tickets.keys) {
            val checkResult = service.checkTicketForPrize(id, winningNumbers)
            assertNotEquals(LotteryTicketCheckResult.CheckResult.TICKET_NOT_SUBMITTED, checkResult.result)
            if (checkResult.result == LotteryTicketCheckResult.CheckResult.WIN_PRIZE) {
                assertTrue(checkResult.prizeAmount > 0)
            } else {
                assertEquals(0, checkResult.prizeAmount)
            }
        }

        // check another ticket that has not been submitted
        val checkResult = service.checkTicketForPrize(LotteryTicketId(), winningNumbers)
        assertEquals(LotteryTicketCheckResult.CheckResult.TICKET_NOT_SUBMITTED, checkResult.result)
        assertEquals(0, checkResult.prizeAmount)
    }
}
