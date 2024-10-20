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
package com.iluwatar.hexagonal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.iluwatar.hexagonal.banking.WireTransfers;
import com.iluwatar.hexagonal.domain.LotteryTicketCheckResult.CheckResult;
import com.iluwatar.hexagonal.module.LotteryTestingModule;
import com.iluwatar.hexagonal.test.LotteryTestUtils;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test the lottery system
 */
class LotteryTest {

  private final Injector injector;
  @Inject
  private LotteryAdministration administration;
  @Inject
  private LotteryService service;
  @Inject
  private WireTransfers wireTransfers;

  LotteryTest() {
    this.injector = Guice.createInjector(new LotteryTestingModule());
  }

  @BeforeEach
  void setup() {
    injector.injectMembers(this);
    // add funds to the test player's bank account
    wireTransfers.setFunds("123-12312", 100);
  }

  @Test
  void testLottery() {
    // admin resets the lottery
    administration.resetLottery();
    assertEquals(0, administration.getAllSubmittedTickets().size());

    // players submit the lottery tickets
    var ticket1 = service.submitTicket(LotteryTestUtils.createLotteryTicket("cvt@bbb.com",
        "123-12312", "+32425255", Set.of(1, 2, 3, 4)));
    assertTrue(ticket1.isPresent());
    var ticket2 = service.submitTicket(LotteryTestUtils.createLotteryTicket("ant@bac.com",
        "123-12312", "+32423455", Set.of(11, 12, 13, 14)));
    assertTrue(ticket2.isPresent());
    var ticket3 = service.submitTicket(LotteryTestUtils.createLotteryTicket("arg@boo.com",
        "123-12312", "+32421255", Set.of(6, 8, 13, 19)));
    assertTrue(ticket3.isPresent());
    assertEquals(3, administration.getAllSubmittedTickets().size());

    // perform lottery
    var winningNumbers = administration.performLottery();

    // cheat a bit for testing sake, use winning numbers to submit another ticket
    var ticket4 = service.submitTicket(LotteryTestUtils.createLotteryTicket("lucky@orb.com",
        "123-12312", "+12421255", winningNumbers.getNumbers()));
    assertTrue(ticket4.isPresent());
    assertEquals(4, administration.getAllSubmittedTickets().size());

    // check winners
    var tickets = administration.getAllSubmittedTickets();
    for (var id : tickets.keySet()) {
      var checkResult = service.checkTicketForPrize(id, winningNumbers);
      assertNotEquals(CheckResult.TICKET_NOT_SUBMITTED, checkResult.getResult());
      if (checkResult.getResult().equals(CheckResult.WIN_PRIZE)) {
        assertTrue(checkResult.getPrizeAmount() > 0);
      } else {
        assertEquals(0, checkResult.getPrizeAmount());
      }
    }

    // check another ticket that has not been submitted
    var checkResult = service.checkTicketForPrize(new LotteryTicketId(), winningNumbers);
    assertEquals(CheckResult.TICKET_NOT_SUBMITTED, checkResult.getResult());
    assertEquals(0, checkResult.getPrizeAmount());
  }
}
