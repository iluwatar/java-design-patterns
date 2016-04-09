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
import com.iluwatar.hexagonal.domain.LotteryNumbers;
import com.iluwatar.hexagonal.domain.LotteryTicket;
import com.iluwatar.hexagonal.domain.PlayerDetails;
import com.iluwatar.hexagonal.service.LotteryService;
import com.iluwatar.hexagonal.service.LotteryServiceImpl;

/**
 * 
 * Example application demonstrating Hexagonal Architecture
 *
 */
public class App {
  
  private static List<PlayerDetails> allPlayerDetails;
  
  static {
    allPlayerDetails = new ArrayList<>();
    allPlayerDetails.add(PlayerDetails.create("john@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("mary@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("steve@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("wayne@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("johnie@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("andy@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("richard@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("kevin@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("arnold@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("ian@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("robin@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("ted@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("larry@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("calvin@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("jacob@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("edwin@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("mary@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("lolita@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("bruno@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("peter@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("warren@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("monica@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("ollie@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("yngwie@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("lars@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("bobbie@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("tyron@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("tyrell@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("nadja@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("wendy@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("luke@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("bjorn@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("lisa@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("anton@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("bruce@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("ray@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("ron@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("xavier@google.com", "312-342", "+3242434242"));
    allPlayerDetails.add(PlayerDetails.create("harriet@google.com", "312-342", "+3242434242"));
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
    
    int i = administartion.getAllSubmittedTickets().size();
    
    // perform lottery
    administartion.performLottery();
  }
  
  private static void submitTickets(LotteryService lotteryService, int numTickets) {
    for (int i=0; i<numTickets; i++) {
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
