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
package com.iluwatar.hexagonal.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.iluwatar.hexagonal.module.LotteryTestingModule;
import org.junit.Before;
import org.junit.Test;

import com.iluwatar.hexagonal.banking.WireTransfers;
import com.iluwatar.hexagonal.domain.LotteryTicketCheckResult.CheckResult;
import com.iluwatar.hexagonal.test.LotteryTestUtils;

/**
 * 
 * Test the lottery system
 *
 */
public class LotteryTest {

  private Injector injector;
  @Inject
  private LotteryAdministration administration;
  @Inject
  private LotteryService service;
  @Inject
  private WireTransfers wireTransfers;

  public LotteryTest() {
    this.injector = Guice.createInjector(new LotteryTestingModule());
  }

  @Before
  public void setup() {
    injector.injectMembers(this);
    // add funds to the test player's bank account
    wireTransfers.setFunds("123-12312", 100);
  }
  
  @Test
  public void testLottery() {
    // admin resets the lottery
    administration.resetLottery();
    assertEquals(administration.getAllSubmittedTickets().size(), 0);
    
    // players submit the lottery tickets
    Optional<LotteryTicketId> ticket1 = service.submitTicket(LotteryTestUtils.createLotteryTicket("cvt@bbb.com",
        "123-12312", "+32425255", new HashSet<>(Arrays.asList(1, 2, 3, 4))));
    assertTrue(ticket1.isPresent());
    Optional<LotteryTicketId> ticket2 = service.submitTicket(LotteryTestUtils.createLotteryTicket("ant@bac.com",
        "123-12312", "+32423455", new HashSet<>(Arrays.asList(11, 12, 13, 14))));
    assertTrue(ticket2.isPresent());
    Optional<LotteryTicketId> ticket3 = service.submitTicket(LotteryTestUtils.createLotteryTicket("arg@boo.com",
        "123-12312", "+32421255", new HashSet<>(Arrays.asList(6, 8, 13, 19))));
    assertTrue(ticket3.isPresent());
    assertEquals(administration.getAllSubmittedTickets().size(), 3);
    
    // perform lottery
    LotteryNumbers winningNumbers = administration.performLottery();

    // cheat a bit for testing sake, use winning numbers to submit another ticket
    Optional<LotteryTicketId> ticket4 = service.submitTicket(LotteryTestUtils.createLotteryTicket("lucky@orb.com",
        "123-12312", "+12421255", winningNumbers.getNumbers()));
    assertTrue(ticket4.isPresent());
    assertEquals(administration.getAllSubmittedTickets().size(), 4);
    
    // check winners
    Map<LotteryTicketId, LotteryTicket> tickets = administration.getAllSubmittedTickets();
    for (LotteryTicketId id: tickets.keySet()) {
      LotteryTicketCheckResult checkResult = service.checkTicketForPrize(id, winningNumbers);
      assertTrue(checkResult.getResult() != CheckResult.TICKET_NOT_SUBMITTED);
      if (checkResult.getResult().equals(CheckResult.WIN_PRIZE)) {
        assertTrue(checkResult.getPrizeAmount() > 0);
      } else if (checkResult.getResult().equals(CheckResult.WIN_PRIZE)) {
        assertEquals(checkResult.getPrizeAmount(), 0);
      }
    }
    
    // check another ticket that has not been submitted
    LotteryTicketCheckResult checkResult = service.checkTicketForPrize(new LotteryTicketId(), winningNumbers);
    assertTrue(checkResult.getResult() == CheckResult.TICKET_NOT_SUBMITTED);
    assertEquals(checkResult.getPrizeAmount(), 0);
  }
}
