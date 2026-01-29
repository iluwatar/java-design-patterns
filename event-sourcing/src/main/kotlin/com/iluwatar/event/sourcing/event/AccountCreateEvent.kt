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

// ABOUTME: Domain event representing the creation of a bank account.
// ABOUTME: Processes by creating a new Account and storing it in the AccountAggregate.
package com.iluwatar.event.sourcing.event

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.iluwatar.event.sourcing.domain.Account
import com.iluwatar.event.sourcing.state.AccountAggregate

/**
 * This is the class that implements account created event. Holds the necessary info for an account
 * created event. Implements the process function that finds the event-related domain objects and
 * calls the related domain object's handle event functions
 */
class AccountCreateEvent @JsonCreator constructor(
    @JsonProperty("sequenceId") sequenceId: Long,
    @JsonProperty("createdTime") createdTime: Long,
    @JsonProperty("accountNo") val accountNo: Int,
    @JsonProperty("owner") val owner: String
) : DomainEvent(sequenceId, createdTime, "AccountCreateEvent") {

    override fun process() {
        var account = AccountAggregate.getAccount(accountNo)
        if (account != null) {
            throw RuntimeException("Account already exists")
        }
        account = Account(accountNo, owner)
        account.handleEvent(this)
    }
}
