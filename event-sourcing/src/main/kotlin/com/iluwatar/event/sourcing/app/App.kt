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

// ABOUTME: Main application demonstrating Event Sourcing pattern with bank accounts.
// ABOUTME: Creates accounts, performs operations, simulates shutdown, and recovers state from event journal.
package com.iluwatar.event.sourcing.app

import com.iluwatar.event.sourcing.event.AccountCreateEvent
import com.iluwatar.event.sourcing.event.MoneyDepositEvent
import com.iluwatar.event.sourcing.event.MoneyTransferEvent
import com.iluwatar.event.sourcing.processor.DomainEventProcessor
import com.iluwatar.event.sourcing.processor.JsonFileJournal
import com.iluwatar.event.sourcing.state.AccountAggregate
import io.github.oshai.kotlinlogging.KotlinLogging
import java.math.BigDecimal
import java.util.Date

private val logger = KotlinLogging.logger {}

/** The constant ACCOUNT OF DAENERYS. */
const val ACCOUNT_OF_DAENERYS = 1

/** The constant ACCOUNT OF JON. */
const val ACCOUNT_OF_JON = 2

/**
 * Event Sourcing: Instead of storing just the current state of the data in a domain, use an
 * append-only store to record the full series of actions taken on that data. The store acts as the
 * system of record and can be used to materialize the domain objects. This can simplify tasks in
 * complex domains, by avoiding the need to synchronize the data model and the business domain,
 * while improving performance, scalability, and responsiveness. It can also provide consistency for
 * transactional data, and maintain full audit trails and history that can enable compensating
 * actions.
 *
 * This App is an example usage of an Event Sourcing pattern. As an example, two bank
 * accounts are created, then some money deposit and transfer actions are taken, so a new state of
 * accounts is created. At that point, state is cleared in order to represent a system shut-down.
 * After the shut-down, system state is recovered by re-creating the past events from event
 * journals. Then state is printed so a user can view the last state is same with the state before a
 * system shut-down.
 */
fun main() {
    var eventProcessor = DomainEventProcessor(JsonFileJournal())

    logger.info { "Running the system first time............" }
    eventProcessor.reset()

    logger.info { "Creating the accounts............" }

    eventProcessor.process(
        AccountCreateEvent(0, Date().time, ACCOUNT_OF_DAENERYS, "Daenerys Targaryen")
    )

    eventProcessor.process(
        AccountCreateEvent(1, Date().time, ACCOUNT_OF_JON, "Jon Snow")
    )

    logger.info { "Do some money operations............" }

    eventProcessor.process(
        MoneyDepositEvent(2, Date().time, ACCOUNT_OF_DAENERYS, BigDecimal("100000"))
    )

    eventProcessor.process(
        MoneyDepositEvent(3, Date().time, ACCOUNT_OF_JON, BigDecimal("100"))
    )

    eventProcessor.process(
        MoneyTransferEvent(4, Date().time, BigDecimal("10000"), ACCOUNT_OF_DAENERYS, ACCOUNT_OF_JON)
    )

    logger.info { "...............State:............" }
    logger.info { AccountAggregate.getAccount(ACCOUNT_OF_DAENERYS).toString() }
    logger.info { AccountAggregate.getAccount(ACCOUNT_OF_JON).toString() }

    logger.info { "At that point system had a shut down, state in memory is cleared............" }
    AccountAggregate.resetState()

    logger.info { "Recover the system by the events in journal file............" }

    eventProcessor = DomainEventProcessor(JsonFileJournal())
    eventProcessor.recover()

    logger.info { "...............Recovered State:............" }
    logger.info { AccountAggregate.getAccount(ACCOUNT_OF_DAENERYS).toString() }
    logger.info { AccountAggregate.getAccount(ACCOUNT_OF_JON).toString() }
}
