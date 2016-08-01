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
  
  private static List<PlayerDetails> allPlayerDetails;
  
  static {
    allPlayerDetails = new ArrayList<>();
    allPlayerDetails.add(PlayerDetails.create("john@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("mary@google.com", "234-987", "+23452346"));
    allPlayerDetails.add(PlayerDetails.create("steve@google.com", "833-836", "+63457543"));
    allPlayerDetails.add(PlayerDetails.create("wayne@google.com", "319-826", "+24626"));
    allPlayerDetails.add(PlayerDetails.create("johnie@google.com", "983-322", "+3635635"));
    allPlayerDetails.add(PlayerDetails.create("andy@google.com", "934-734", "+0898245"));
    allPlayerDetails.add(PlayerDetails.create("richard@google.com", "536-738", "+09845325"));
    allPlayerDetails.add(PlayerDetails.create("kevin@google.com", "453-936", "+2423532"));
    allPlayerDetails.add(PlayerDetails.create("arnold@google.com", "114-988", "+5646346524"));
    allPlayerDetails.add(PlayerDetails.create("ian@google.com", "663-765", "+928394235"));
    allPlayerDetails.add(PlayerDetails.create("robin@google.com", "334-763", "+35448"));
    allPlayerDetails.add(PlayerDetails.create("ted@google.com", "735-964", "+98752345"));
    allPlayerDetails.add(PlayerDetails.create("larry@google.com", "734-853", "+043842423"));
    allPlayerDetails.add(PlayerDetails.create("calvin@google.com", "334-746", "+73294135"));
    allPlayerDetails.add(PlayerDetails.create("jacob@google.com", "444-766", "+358042354"));
    allPlayerDetails.add(PlayerDetails.create("edwin@google.com", "895-345", "+9752435"));
    allPlayerDetails.add(PlayerDetails.create("mary@google.com", "760-009", "+34203542"));
    allPlayerDetails.add(PlayerDetails.create("lolita@google.com", "425-907", "+9872342"));
    allPlayerDetails.add(PlayerDetails.create("bruno@google.com", "023-638", "+673824122"));
    allPlayerDetails.add(PlayerDetails.create("peter@google.com", "335-886", "+5432503945"));
    allPlayerDetails.add(PlayerDetails.create("warren@google.com", "225-946", "+9872341324"));
    allPlayerDetails.add(PlayerDetails.create("monica@google.com", "265-748", "+134124"));
    allPlayerDetails.add(PlayerDetails.create("ollie@google.com", "190-045", "+34453452"));
    allPlayerDetails.add(PlayerDetails.create("yngwie@google.com", "241-465", "+9897641231"));
    allPlayerDetails.add(PlayerDetails.create("lars@google.com", "746-936", "+42345298345"));
    allPlayerDetails.add(PlayerDetails.create("bobbie@google.com", "946-384", "+79831742"));
    allPlayerDetails.add(PlayerDetails.create("tyron@google.com", "310-992", "+0498837412"));
    allPlayerDetails.add(PlayerDetails.create("tyrell@google.com", "032-045", "+67834134"));
    allPlayerDetails.add(PlayerDetails.create("nadja@google.com", "000-346", "+498723"));
    allPlayerDetails.add(PlayerDetails.create("wendy@google.com", "994-989", "+987324454"));
    allPlayerDetails.add(PlayerDetails.create("luke@google.com", "546-634", "+987642435"));
    allPlayerDetails.add(PlayerDetails.create("bjorn@google.com", "342-874", "+7834325"));
    allPlayerDetails.add(PlayerDetails.create("lisa@google.com", "024-653", "+980742154"));
    allPlayerDetails.add(PlayerDetails.create("anton@google.com", "834-935", "+876423145"));
    allPlayerDetails.add(PlayerDetails.create("bruce@google.com", "284-936", "+09843212345"));
    allPlayerDetails.add(PlayerDetails.create("ray@google.com", "843-073", "+678324123"));
    allPlayerDetails.add(PlayerDetails.create("ron@google.com", "637-738", "+09842354"));
    allPlayerDetails.add(PlayerDetails.create("xavier@google.com", "143-947", "+375245"));
    allPlayerDetails.add(PlayerDetails.create("harriet@google.com", "842-404", "+131243252"));
    WireTransfersImpl wireTransfers = new WireTransfersImpl();
    Random random = new Random();
    for (int i = 0; i < allPlayerDetails.size(); i++) {
      wireTransfers.setFunds(allPlayerDetails.get(i).getBankAccount(), 
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
    int idx = random.nextInt(allPlayerDetails.size());
    return allPlayerDetails.get(idx);
  }
}
