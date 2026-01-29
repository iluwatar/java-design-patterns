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

// ABOUTME: Integration test for Event Sourcing state recovery functionality.
// ABOUTME: Verifies that account state can be recovered from the event journal after shutdown.
package com.iluwatar.event.sourcing

import com.iluwatar.event.sourcing.app.ACCOUNT_OF_DAENERYS
import com.iluwatar.event.sourcing.app.ACCOUNT_OF_JON
import com.iluwatar.event.sourcing.event.AccountCreateEvent
import com.iluwatar.event.sourcing.event.MoneyDepositEvent
import com.iluwatar.event.sourcing.event.MoneyTransferEvent
import com.iluwatar.event.sourcing.processor.DomainEventProcessor
import com.iluwatar.event.sourcing.processor.JsonFileJournal
import com.iluwatar.event.sourcing.state.AccountAggregate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.Date

/**
 * Integration Test for Event-Sourcing state recovery
 */
class IntegrationTest {

    /** The Domain event processor. */
    private lateinit var eventProcessor: DomainEventProcessor

    /** Initialize. */
    @BeforeEach
    fun initialize() {
        eventProcessor = DomainEventProcessor(JsonFileJournal())
    }

    /** Test state recovery. */
    @Test
    fun testStateRecovery() {
        eventProcessor.reset()

        eventProcessor.process(
            AccountCreateEvent(0, Date().time, ACCOUNT_OF_DAENERYS, "Daenerys Targaryen")
        )

        eventProcessor.process(
            AccountCreateEvent(1, Date().time, ACCOUNT_OF_JON, "Jon Snow")
        )

        eventProcessor.process(
            MoneyDepositEvent(2, Date().time, ACCOUNT_OF_DAENERYS, BigDecimal("100000"))
        )

        eventProcessor.process(
            MoneyDepositEvent(3, Date().time, ACCOUNT_OF_JON, BigDecimal("100"))
        )

        eventProcessor.process(
            MoneyTransferEvent(4, Date().time, BigDecimal("10000"), ACCOUNT_OF_DAENERYS, ACCOUNT_OF_JON)
        )

        val accountOfDaenerysBeforeShutDown = AccountAggregate.getAccount(ACCOUNT_OF_DAENERYS)
        val accountOfJonBeforeShutDown = AccountAggregate.getAccount(ACCOUNT_OF_JON)

        AccountAggregate.resetState()

        eventProcessor = DomainEventProcessor(JsonFileJournal())
        eventProcessor.recover()

        val accountOfDaenerysAfterShutDown = AccountAggregate.getAccount(ACCOUNT_OF_DAENERYS)
        val accountOfJonAfterShutDown = AccountAggregate.getAccount(ACCOUNT_OF_JON)

        assertEquals(accountOfDaenerysBeforeShutDown!!.money, accountOfDaenerysAfterShutDown!!.money)
        assertEquals(accountOfJonBeforeShutDown!!.money, accountOfJonAfterShutDown!!.money)
    }
}
