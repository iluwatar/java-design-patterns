/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.hexagonal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.iluwatar.hexagonal.administration.LotteryAdministration;
import com.iluwatar.hexagonal.administration.LotteryAdministrationImpl;
import com.iluwatar.hexagonal.banking.WireTransfersImpl;
import com.iluwatar.hexagonal.domain.LotteryConstants;
import com.iluwatar.hexagonal.domain.LotteryNumbers;
import com.iluwatar.hexagonal.domain.LotteryTicket;
import com.iluwatar.hexagonal.domain.PlayerDetails;
import com.iluwatar.hexagonal.service.LotteryService;
import com.iluwatar.hexagonal.service.LotteryServiceImpl;

/**
 * 
 * Hexagonal Architecture pattern decouples the application core from the
 * services it uses. This allows the services to be plugged in and the 
 * application will run with or without the services.<p>
 * 
 * The core logic, or business logic, of an application consists of the 
 * algorithms that are essential to its purpose. They implement the use 
 * cases that are the heart of the application. When you change them, you 
 * change the essence of the application.<p>
 * 
 * The services are not essential. They can be replaced without changing 
 * the purpose of the application. Examples: database access and other 
 * types of storage, user interface components, e-mail and other 
 * communication components, hardware devices.<p>
 * 
 * This example demonstrates Hexagonal Architecture with a lottery system.
 * The application core is separate from the services that drive it and
 * from the services it uses.<p>
 * 
 * The primary ports for the application are {@link LotteryAdministration} 
 * through which the lottery round is initiated and run and 
 * {@link LotteryService} that allows players to submit lottery tickets for 
 * the draw.<p>
 * 
 * The secondary ports that application core uses are {@link WireTransfers}
 * which is a banking service, {@link LotteryNotifications} that delivers
 * notifications as lottery events occur and {@link LotteryTicketRepository}
 * that is the storage for the lottery tickets.
 *
 */
public class App {
  
  private static final List<PlayerDetails> PLAYERS;
  
  static {
    PLAYERS = new ArrayList<>();
    PLAYERS.add(PlayerDetails.create("john@google.com", "312-342", "+3242434242"));
    PLAYERS.add(PlayerDetails.create("mary@google.com", "234-987", "+23452346"));
    PLAYERS.add(PlayerDetails.create("steve@google.com", "833-836", "+63457543"));
    PLAYERS.add(PlayerDetails.create("wayne@google.com", "319-826", "+24626"));
    PLAYERS.add(PlayerDetails.create("johnie@google.com", "983-322", "+3635635"));
    PLAYERS.add(PlayerDetails.create("andy@google.com", "934-734", "+0898245"));
    PLAYERS.add(PlayerDetails.create("richard@google.com", "536-738", "+09845325"));
    PLAYERS.add(PlayerDetails.create("kevin@google.com", "453-936", "+2423532"));
    PLAYERS.add(PlayerDetails.create("arnold@google.com", "114-988", "+5646346524"));
    PLAYERS.add(PlayerDetails.create("ian@google.com", "663-765", "+928394235"));
    PLAYERS.add(PlayerDetails.create("robin@google.com", "334-763", "+35448"));
    PLAYERS.add(PlayerDetails.create("ted@google.com", "735-964", "+98752345"));
    PLAYERS.add(PlayerDetails.create("larry@google.com", "734-853", "+043842423"));
    PLAYERS.add(PlayerDetails.create("calvin@google.com", "334-746", "+73294135"));
    PLAYERS.add(PlayerDetails.create("jacob@google.com", "444-766", "+358042354"));
    PLAYERS.add(PlayerDetails.create("edwin@google.com", "895-345", "+9752435"));
    PLAYERS.add(PlayerDetails.create("mary@google.com", "760-009", "+34203542"));
    PLAYERS.add(PlayerDetails.create("lolita@google.com", "425-907", "+9872342"));
    PLAYERS.add(PlayerDetails.create("bruno@google.com", "023-638", "+673824122"));
    PLAYERS.add(PlayerDetails.create("peter@google.com", "335-886", "+5432503945"));
    PLAYERS.add(PlayerDetails.create("warren@google.com", "225-946", "+9872341324"));
    PLAYERS.add(PlayerDetails.create("monica@google.com", "265-748", "+134124"));
    PLAYERS.add(PlayerDetails.create("ollie@google.com", "190-045", "+34453452"));
    PLAYERS.add(PlayerDetails.create("yngwie@google.com", "241-465", "+9897641231"));
    PLAYERS.add(PlayerDetails.create("lars@google.com", "746-936", "+42345298345"));
    PLAYERS.add(PlayerDetails.create("bobbie@google.com", "946-384", "+79831742"));
    PLAYERS.add(PlayerDetails.create("tyron@google.com", "310-992", "+0498837412"));
    PLAYERS.add(PlayerDetails.create("tyrell@google.com", "032-045", "+67834134"));
    PLAYERS.add(PlayerDetails.create("nadja@google.com", "000-346", "+498723"));
    PLAYERS.add(PlayerDetails.create("wendy@google.com", "994-989", "+987324454"));
    PLAYERS.add(PlayerDetails.create("luke@google.com", "546-634", "+987642435"));
    PLAYERS.add(PlayerDetails.create("bjorn@google.com", "342-874", "+7834325"));
    PLAYERS.add(PlayerDetails.create("lisa@google.com", "024-653", "+980742154"));
    PLAYERS.add(PlayerDetails.create("anton@google.com", "834-935", "+876423145"));
    PLAYERS.add(PlayerDetails.create("bruce@google.com", "284-936", "+09843212345"));
    PLAYERS.add(PlayerDetails.create("ray@google.com", "843-073", "+678324123"));
    PLAYERS.add(PlayerDetails.create("ron@google.com", "637-738", "+09842354"));
    PLAYERS.add(PlayerDetails.create("xavier@google.com", "143-947", "+375245"));
    PLAYERS.add(PlayerDetails.create("harriet@google.com", "842-404", "+131243252"));
    WireTransfersImpl wireTransfers = new WireTransfersImpl();
    Random random = new Random();
    for (int i = 0; i < PLAYERS.size(); i++) {
      wireTransfers.setFunds(PLAYERS.get(i).getBankAccount(), 
          random.nextInt(LotteryConstants.PLAYER_MAX_SALDO));
    }
  }
  
  /**
   * Program entry point
   */
  public static void main(String[] args) {
    // start new lottery round
    LotteryAdministration administartion = new LotteryAdministrationImpl();
    administartion.resetLottery();
    
    // submit some lottery tickets
    LotteryServiceImpl service = new LotteryServiceImpl();
    submitTickets(service, 20);
    
    // perform lottery
    administartion.performLottery();
  }
  
  private static void submitTickets(LotteryService lotteryService, int numTickets) {
    for (int i = 0; i < numTickets; i++) {
      LotteryTicket ticket = LotteryTicket.create(getRandomPlayerDetails(), LotteryNumbers.createRandom());
      lotteryService.submitTicket(ticket);
    }
  }
  
  private static PlayerDetails getRandomPlayerDetails() {
    Random random = new Random();
    int idx = random.nextInt(PLAYERS.size());
    return PLAYERS.get(idx);
  }
}
