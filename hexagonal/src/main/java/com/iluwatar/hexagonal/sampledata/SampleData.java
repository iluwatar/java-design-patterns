/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utilities for creating sample lottery tickets
 */
public class SampleData {

  private static final List<PlayerDetails> PLAYERS;

  static {
    PLAYERS = new ArrayList<>();
    PLAYERS.add(new PlayerDetails("john@google.com", "312-342", "+3242434242"));
    PLAYERS.add(new PlayerDetails("mary@google.com", "234-987", "+23452346"));
    PLAYERS.add(new PlayerDetails("steve@google.com", "833-836", "+63457543"));
    PLAYERS.add(new PlayerDetails("wayne@google.com", "319-826", "+24626"));
    PLAYERS.add(new PlayerDetails("johnie@google.com", "983-322", "+3635635"));
    PLAYERS.add(new PlayerDetails("andy@google.com", "934-734", "+0898245"));
    PLAYERS.add(new PlayerDetails("richard@google.com", "536-738", "+09845325"));
    PLAYERS.add(new PlayerDetails("kevin@google.com", "453-936", "+2423532"));
    PLAYERS.add(new PlayerDetails("arnold@google.com", "114-988", "+5646346524"));
    PLAYERS.add(new PlayerDetails("ian@google.com", "663-765", "+928394235"));
    PLAYERS.add(new PlayerDetails("robin@google.com", "334-763", "+35448"));
    PLAYERS.add(new PlayerDetails("ted@google.com", "735-964", "+98752345"));
    PLAYERS.add(new PlayerDetails("larry@google.com", "734-853", "+043842423"));
    PLAYERS.add(new PlayerDetails("calvin@google.com", "334-746", "+73294135"));
    PLAYERS.add(new PlayerDetails("jacob@google.com", "444-766", "+358042354"));
    PLAYERS.add(new PlayerDetails("edwin@google.com", "895-345", "+9752435"));
    PLAYERS.add(new PlayerDetails("mary@google.com", "760-009", "+34203542"));
    PLAYERS.add(new PlayerDetails("lolita@google.com", "425-907", "+9872342"));
    PLAYERS.add(new PlayerDetails("bruno@google.com", "023-638", "+673824122"));
    PLAYERS.add(new PlayerDetails("peter@google.com", "335-886", "+5432503945"));
    PLAYERS.add(new PlayerDetails("warren@google.com", "225-946", "+9872341324"));
    PLAYERS.add(new PlayerDetails("monica@google.com", "265-748", "+134124"));
    PLAYERS.add(new PlayerDetails("ollie@google.com", "190-045", "+34453452"));
    PLAYERS.add(new PlayerDetails("yngwie@google.com", "241-465", "+9897641231"));
    PLAYERS.add(new PlayerDetails("lars@google.com", "746-936", "+42345298345"));
    PLAYERS.add(new PlayerDetails("bobbie@google.com", "946-384", "+79831742"));
    PLAYERS.add(new PlayerDetails("tyron@google.com", "310-992", "+0498837412"));
    PLAYERS.add(new PlayerDetails("tyrell@google.com", "032-045", "+67834134"));
    PLAYERS.add(new PlayerDetails("nadja@google.com", "000-346", "+498723"));
    PLAYERS.add(new PlayerDetails("wendy@google.com", "994-989", "+987324454"));
    PLAYERS.add(new PlayerDetails("luke@google.com", "546-634", "+987642435"));
    PLAYERS.add(new PlayerDetails("bjorn@google.com", "342-874", "+7834325"));
    PLAYERS.add(new PlayerDetails("lisa@google.com", "024-653", "+980742154"));
    PLAYERS.add(new PlayerDetails("anton@google.com", "834-935", "+876423145"));
    PLAYERS.add(new PlayerDetails("bruce@google.com", "284-936", "+09843212345"));
    PLAYERS.add(new PlayerDetails("ray@google.com", "843-073", "+678324123"));
    PLAYERS.add(new PlayerDetails("ron@google.com", "637-738", "+09842354"));
    PLAYERS.add(new PlayerDetails("xavier@google.com", "143-947", "+375245"));
    PLAYERS.add(new PlayerDetails("harriet@google.com", "842-404", "+131243252"));
    InMemoryBank wireTransfers = new InMemoryBank();
    Random random = new Random();
    for (int i = 0; i < PLAYERS.size(); i++) {
      wireTransfers.setFunds(PLAYERS.get(i).getBankAccount(),
          random.nextInt(LotteryConstants.PLAYER_MAX_SALDO));
    }
  }

  /**
   * Inserts lottery tickets into the database based on the sample data
   */
  public static void submitTickets(LotteryService lotteryService, int numTickets) {
    for (int i = 0; i < numTickets; i++) {
      LotteryTicket ticket = new LotteryTicket(new LotteryTicketId(),
          getRandomPlayerDetails(), LotteryNumbers.createRandom());
      lotteryService.submitTicket(ticket);
    }
  }

  private static PlayerDetails getRandomPlayerDetails() {
    Random random = new Random();
    int idx = random.nextInt(PLAYERS.size());
    return PLAYERS.get(idx);
  }
}
