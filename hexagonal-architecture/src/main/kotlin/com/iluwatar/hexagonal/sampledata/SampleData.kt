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

// ABOUTME: Provides sample player data for lottery demonstrations.
// ABOUTME: Creates randomized lottery tickets for testing scenarios.
package com.iluwatar.hexagonal.sampledata

import com.iluwatar.hexagonal.banking.InMemoryBank
import com.iluwatar.hexagonal.domain.LotteryConstants
import com.iluwatar.hexagonal.domain.LotteryNumbers
import com.iluwatar.hexagonal.domain.LotteryService
import com.iluwatar.hexagonal.domain.LotteryTicket
import com.iluwatar.hexagonal.domain.LotteryTicketId
import com.iluwatar.hexagonal.domain.PlayerDetails
import java.security.SecureRandom

/**
 * Utilities for creating sample lottery tickets.
 */
object SampleData {

    private val PLAYERS: List<PlayerDetails>
    private val RANDOM = SecureRandom()

    init {
        PLAYERS = listOf(
            PlayerDetails("john@google.com", "312-342", "+3242434242"),
            PlayerDetails("mary@google.com", "234-987", "+23452346"),
            PlayerDetails("steve@google.com", "833-836", "+63457543"),
            PlayerDetails("wayne@google.com", "319-826", "+24626"),
            PlayerDetails("johnie@google.com", "983-322", "+3635635"),
            PlayerDetails("andy@google.com", "934-734", "+0898245"),
            PlayerDetails("richard@google.com", "536-738", "+09845325"),
            PlayerDetails("kevin@google.com", "453-936", "+2423532"),
            PlayerDetails("arnold@google.com", "114-988", "+5646346524"),
            PlayerDetails("ian@google.com", "663-765", "+928394235"),
            PlayerDetails("robin@google.com", "334-763", "+35448"),
            PlayerDetails("ted@google.com", "735-964", "+98752345"),
            PlayerDetails("larry@google.com", "734-853", "+043842423"),
            PlayerDetails("calvin@google.com", "334-746", "+73294135"),
            PlayerDetails("jacob@google.com", "444-766", "+358042354"),
            PlayerDetails("edwin@google.com", "895-345", "+9752435"),
            PlayerDetails("mary@google.com", "760-009", "+34203542"),
            PlayerDetails("lolita@google.com", "425-907", "+9872342"),
            PlayerDetails("bruno@google.com", "023-638", "+673824122"),
            PlayerDetails("peter@google.com", "335-886", "+5432503945"),
            PlayerDetails("warren@google.com", "225-946", "+9872341324"),
            PlayerDetails("monica@google.com", "265-748", "+134124"),
            PlayerDetails("ollie@google.com", "190-045", "+34453452"),
            PlayerDetails("yngwie@google.com", "241-465", "+9897641231"),
            PlayerDetails("lars@google.com", "746-936", "+42345298345"),
            PlayerDetails("bobbie@google.com", "946-384", "+79831742"),
            PlayerDetails("tyron@google.com", "310-992", "+0498837412"),
            PlayerDetails("tyrell@google.com", "032-045", "+67834134"),
            PlayerDetails("nadja@google.com", "000-346", "+498723"),
            PlayerDetails("wendy@google.com", "994-989", "+987324454"),
            PlayerDetails("luke@google.com", "546-634", "+987642435"),
            PlayerDetails("bjorn@google.com", "342-874", "+7834325"),
            PlayerDetails("lisa@google.com", "024-653", "+980742154"),
            PlayerDetails("anton@google.com", "834-935", "+876423145"),
            PlayerDetails("bruce@google.com", "284-936", "+09843212345"),
            PlayerDetails("ray@google.com", "843-073", "+678324123"),
            PlayerDetails("ron@google.com", "637-738", "+09842354"),
            PlayerDetails("xavier@google.com", "143-947", "+375245"),
            PlayerDetails("harriet@google.com", "842-404", "+131243252")
        )
        val wireTransfers = InMemoryBank()
        PLAYERS
            .map { it.bankAccount }
            .associateWith { RANDOM.nextInt(LotteryConstants.PLAYER_MAX_BALANCE) }
            .forEach { (account, balance) -> wireTransfers.setFunds(account, balance) }
    }

    /**
     * Inserts lottery tickets into the database based on the sample data.
     */
    fun submitTickets(lotteryService: LotteryService, numTickets: Int) {
        repeat(numTickets) {
            val randomPlayerDetails = getRandomPlayerDetails()
            val lotteryNumbers = LotteryNumbers.createRandom()
            val lotteryTicketId = LotteryTicketId()
            val ticket = LotteryTicket(lotteryTicketId, randomPlayerDetails, lotteryNumbers)
            lotteryService.submitTicket(ticket)
        }
    }

    private fun getRandomPlayerDetails(): PlayerDetails = PLAYERS[RANDOM.nextInt(PLAYERS.size)]
}
