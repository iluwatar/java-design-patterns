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

// ABOUTME: Tests for the Bank class verifying thread-safe operations.
// ABOUTME: Validates account management, transfers, and balance calculations.

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assumptions.assumeTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankTest {

    private val accountNum = 4
    private val baseAmount = 1000
    private lateinit var bank: Bank

    @BeforeAll
    fun setup() {
        bank = Bank(accountNum, baseAmount)
    }

    @AfterAll
    fun tearDown() {
    }

    @Test
    fun getAccountHaveNotBeNull() {
        assertNotNull(bank.accounts)
    }

    @Test
    fun lengthOfAccountsHaveToEqualsToAccountNumConstant() {
        assumeTrue(bank.accounts != null)
        assertEquals(accountNum, bank.accounts.size)
    }

    @Test
    fun transferMethodHaveToTransferAmountFromAnAccountToOtherAccount() {
        bank.transfer(0, 1, 1000)
        val accounts = bank.accounts
        assertEquals(0, accounts[0])
        assertEquals(2000, accounts[1])
    }

    @Test
    fun balanceHaveToBeOK() {
        assertEquals(4000, bank.getBalance())
    }

    @Test
    fun returnBalanceWhenGivenAccountNumber() {
        bank.transfer(0, 1, 1000)
        assertEquals(0, bank.getBalance(0))
        assertEquals(2000, bank.getBalance(1))
    }
}
