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
package com.iluwatar.monitor

// ABOUTME: Bank class demonstrating the Monitor pattern with synchronized methods.
// ABOUTME: Provides thread-safe account balance management and transfers.

import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Bank Definition.
 *
 * @param accountNum number of accounts
 * @param baseAmount initial balance for each account
 */
class Bank(accountNum: Int, baseAmount: Int) {

    val accounts: IntArray = IntArray(accountNum) { baseAmount }

    /**
     * Transfer amounts from one account to another.
     *
     * @param accountA source account
     * @param accountB destination account
     * @param amount amount to be transferred
     */
    @Synchronized
    fun transfer(accountA: Int, accountB: Int, amount: Int) {
        if (accounts[accountA] >= amount && accountA != accountB) {
            accounts[accountB] += amount
            accounts[accountA] -= amount
            logger.debug {
                "Transferred from account: $accountA to account: $accountB , amount: $amount , " +
                    "bank balance at: ${getBalance()}, source account balance: ${getBalance(accountA)}, " +
                    "destination account balance: ${getBalance(accountB)}"
            }
        }
    }

    /**
     * Calculates the total balance.
     *
     * @return balance
     */
    @Synchronized
    fun getBalance(): Int = accounts.sum()

    /**
     * Get the accountNumber balance.
     *
     * @param accountNumber account number
     * @return account balance
     */
    @Synchronized
    fun getBalance(accountNumber: Int): Int = accounts[accountNumber]
}
