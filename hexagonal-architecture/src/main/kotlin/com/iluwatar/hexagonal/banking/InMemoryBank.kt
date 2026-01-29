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

// ABOUTME: In-memory implementation of the banking adapter.
// ABOUTME: Stores account balances in a HashMap for testing purposes.
package com.iluwatar.hexagonal.banking

import com.iluwatar.hexagonal.domain.LotteryConstants

/**
 * Banking implementation.
 */
class InMemoryBank : WireTransfers {

    override fun setFunds(bankAccount: String, amount: Int) {
        accounts[bankAccount] = amount
    }

    override fun getFunds(bankAccount: String): Int = accounts.getOrDefault(bankAccount, 0)

    override fun transferFunds(amount: Int, sourceBackAccount: String, destinationBankAccount: String): Boolean {
        return if (accounts.getOrDefault(sourceBackAccount, 0) >= amount) {
            accounts[sourceBackAccount] = accounts[sourceBackAccount]!! - amount
            accounts[destinationBankAccount] = (accounts[destinationBankAccount] ?: 0) + amount
            true
        } else {
            false
        }
    }

    companion object {
        private val accounts: MutableMap<String, Int> = HashMap()

        init {
            accounts[LotteryConstants.SERVICE_BANK_ACCOUNT] = LotteryConstants.SERVICE_BANK_ACCOUNT_BALANCE
        }
    }
}
