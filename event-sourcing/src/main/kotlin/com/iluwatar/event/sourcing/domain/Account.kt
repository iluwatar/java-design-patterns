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

// ABOUTME: Represents a bank account with balance management and event handling capabilities.
// ABOUTME: Processes domain events for deposits, withdrawals, and transfers.
package com.iluwatar.event.sourcing.domain

import com.iluwatar.event.sourcing.event.AccountCreateEvent
import com.iluwatar.event.sourcing.event.MoneyDepositEvent
import com.iluwatar.event.sourcing.event.MoneyTransferEvent
import com.iluwatar.event.sourcing.state.AccountAggregate
import io.github.oshai.kotlinlogging.KotlinLogging
import java.math.BigDecimal

private val logger = KotlinLogging.logger {}

/**
 * This is the Account class that holds the account info, the account number, account owner name and
 * money of the account. Account class also have the business logic of events that effects this
 * account.
 */
class Account(
    val accountNo: Int,
    val owner: String
) {
    var money: BigDecimal = BigDecimal.ZERO

    companion object {
        private const val MSG = "Some external api for only realtime execution could be called here."
    }

    /**
     * Copy account.
     *
     * @return the account
     */
    fun copy(): Account {
        val account = Account(accountNo, owner)
        account.money = money
        return account
    }

    override fun toString(): String {
        return "Account{accountNo=$accountNo, owner='$owner', money=$money}"
    }

    private fun depositMoney(amount: BigDecimal) {
        money = money.add(amount)
    }

    private fun withdrawMoney(amount: BigDecimal) {
        money = money.subtract(amount)
    }

    private fun handleDeposit(amount: BigDecimal, realTime: Boolean) {
        depositMoney(amount)
        AccountAggregate.putAccount(this)
        if (realTime) {
            logger.info { MSG }
        }
    }

    private fun handleWithdrawal(amount: BigDecimal, realTime: Boolean) {
        if (money.compareTo(amount) < 0) {
            throw RuntimeException("Insufficient Account Balance")
        }

        withdrawMoney(amount)
        AccountAggregate.putAccount(this)
        if (realTime) {
            logger.info { MSG }
        }
    }

    /**
     * Handles the MoneyDepositEvent.
     *
     * @param moneyDepositEvent the money deposit event
     */
    fun handleEvent(moneyDepositEvent: MoneyDepositEvent) {
        handleDeposit(moneyDepositEvent.money, moneyDepositEvent.realTime)
    }

    /**
     * Handles the AccountCreateEvent.
     *
     * @param accountCreateEvent the account created event
     */
    fun handleEvent(accountCreateEvent: AccountCreateEvent) {
        AccountAggregate.putAccount(this)
        if (accountCreateEvent.realTime) {
            logger.info { MSG }
        }
    }

    /**
     * Handles transfer from account event.
     *
     * @param moneyTransferEvent the money transfer event
     */
    fun handleTransferFromEvent(moneyTransferEvent: MoneyTransferEvent) {
        handleWithdrawal(moneyTransferEvent.money, moneyTransferEvent.realTime)
    }

    /**
     * Handles transfer to account event.
     *
     * @param moneyTransferEvent the money transfer event
     */
    fun handleTransferToEvent(moneyTransferEvent: MoneyTransferEvent) {
        handleDeposit(moneyTransferEvent.money, moneyTransferEvent.realTime)
    }
}
