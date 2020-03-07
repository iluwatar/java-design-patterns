/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.hexagonal.sampledata;

import com.iluwatar.hexagonal.banking.InMemoryBank;
import com.iluwatar.hexagonal.domain.LotteryConstants;
import com.iluwatar.hexagonal.domain.LotteryNumbers;
import com.iluwatar.hexagonal.domain.LotteryService;
import com.iluwatar.hexagonal.domain.LotteryTicket;
import com.iluwatar.hexagonal.domain.LotteryTicketId;
import com.iluwatar.hexagonal.domain.PlayerDetails;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Utilities for creating sample lottery tickets.
 */
public class SampleData {

  private static final List<PlayerDetails> PLAYERS;
  private static final Random RANDOM = new Random();

  static {
    PLAYERS = List.of(
        new PlayerDetails("john@google.com", "312-342", "+3242434242"),
        new PlayerDetails("mary@google.com", "234-987", "+23452346"),
        new PlayerDetails("steve@google.com", "833-836", "+63457543"),
        new PlayerDetails("wayne@google.com", "319-826", "+24626"),
        new PlayerDetails("johnie@google.com", "983-322", "+3635635"),
        new PlayerDetails("andy@google.com", "934-734", "+0898245"),
        new PlayerDetails("richard@google.com", "536-738", "+09845325"),
        new PlayerDetails("kevin@google.com", "453-936", "+2423532"),
        new PlayerDetails("arnold@google.com", "114-988", "+5646346524"),
        new PlayerDetails("ian@google.com", "663-765", "+928394235"),
        new PlayerDetails("robin@google.com", "334-763", "+35448"),
        new PlayerDetails("ted@google.com", "735-964", "+98752345"),
        new PlayerDetails("larry@google.com", "734-853", "+043842423"),
        new PlayerDetails("calvin@google.com", "334-746", "+73294135"),
        new PlayerDetails("jacob@google.com", "444-766", "+358042354"),
        new PlayerDetails("edwin@google.com", "895-345", "+9752435"),
        new PlayerDetails("mary@google.com", "760-009", "+34203542"),
        new PlayerDetails("lolita@google.com", "425-907", "+9872342"),
        new PlayerDetails("bruno@google.com", "023-638", "+673824122"),
        new PlayerDetails("peter@google.com", "335-886", "+5432503945"),
        new PlayerDetails("warren@google.com", "225-946", "+9872341324"),
        new PlayerDetails("monica@google.com", "265-748", "+134124"),
        new PlayerDetails("ollie@google.com", "190-045", "+34453452"),
        new PlayerDetails("yngwie@google.com", "241-465", "+9897641231"),
        new PlayerDetails("lars@google.com", "746-936", "+42345298345"),
        new PlayerDetails("bobbie@google.com", "946-384", "+79831742"),
        new PlayerDetails("tyron@google.com", "310-992", "+0498837412"),
        new PlayerDetails("tyrell@google.com", "032-045", "+67834134"),
        new PlayerDetails("nadja@google.com", "000-346", "+498723"),
        new PlayerDetails("wendy@google.com", "994-989", "+987324454"),
        new PlayerDetails("luke@google.com", "546-634", "+987642435"),
        new PlayerDetails("bjorn@google.com", "342-874", "+7834325"),
        new PlayerDetails("lisa@google.com", "024-653", "+980742154"),
        new PlayerDetails("anton@google.com", "834-935", "+876423145"),
        new PlayerDetails("bruce@google.com", "284-936", "+09843212345"),
        new PlayerDetails("ray@google.com", "843-073", "+678324123"),
        new PlayerDetails("ron@google.com", "637-738", "+09842354"),
        new PlayerDetails("xavier@google.com", "143-947", "+375245"),
        new PlayerDetails("harriet@google.com", "842-404", "+131243252")
    );
    var wireTransfers = new InMemoryBank();
    PLAYERS.stream()
        .map(PlayerDetails::getBankAccount)
        .map(e -> new SimpleEntry<>(e, RANDOM.nextInt(LotteryConstants.PLAYER_MAX_BALANCE)))
        .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue))
        .forEach(wireTransfers::setFunds);
  }

  /**
   * Inserts lottery tickets into the database based on the sample data.
   */
  public static void submitTickets(LotteryService lotteryService, int numTickets) {
    for (var i = 0; i < numTickets; i++) {
      var randomPlayerDetails = getRandomPlayerDetails();
      var lotteryNumbers = LotteryNumbers.createRandom();
      var lotteryTicketId = new LotteryTicketId();
      var ticket = new LotteryTicket(lotteryTicketId, randomPlayerDetails, lotteryNumbers);
      lotteryService.submitTicket(ticket);
    }
  }

  private static PlayerDetails getRandomPlayerDetails() {
    return PLAYERS.get(RANDOM.nextInt(PLAYERS.size()));
  }
}
