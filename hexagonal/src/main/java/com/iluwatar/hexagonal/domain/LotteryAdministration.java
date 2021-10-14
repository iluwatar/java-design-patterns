/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

import static com.iluwatar.hexagonal.domain.LotteryConstants.PRIZE_AMOUNT;
import static com.iluwatar.hexagonal.domain.LotteryConstants.SERVICE_BANK_ACCOUNT;

import com.google.inject.Inject;
import com.iluwatar.hexagonal.banking.WireTransfers;
import com.iluwatar.hexagonal.database.LotteryTicketRepository;
import com.iluwatar.hexagonal.eventlog.LotteryEventLog;
import java.util.Map;

/**
 * Lottery administration implementation.
 */
public class LotteryAdministration {

  private final LotteryTicketRepository repository;
  private final LotteryEventLog notifications;
  private final WireTransfers wireTransfers;

  /**
   * Constructor.
   */
  @Inject
  public LotteryAdministration(LotteryTicketRepository repository, LotteryEventLog notifications,
                               WireTransfers wireTransfers) {
    this.repository = repository;
    this.notifications = notifications;
    this.wireTransfers = wireTransfers;
  }

  /**
   * Get all the lottery tickets submitted for lottery.
   */
  public Map<LotteryTicketId, LotteryTicket> getAllSubmittedTickets() {
    return repository.findAll();
  }

  /**
   * Draw lottery numbers.
   */
  public LotteryNumbers performLottery() {
    var numbers = LotteryNumbers.createRandom();
    var tickets = getAllSubmittedTickets();
    for (var id : tickets.keySet()) {
      var lotteryTicket = tickets.get(id);
      var playerDetails = lotteryTicket.getPlayerDetails();
      var playerAccount = playerDetails.getBankAccount();
      var result = LotteryUtils.checkTicketForPrize(repository, id, numbers).getResult();
      if (result == LotteryTicketCheckResult.CheckResult.WIN_PRIZE) {
        if (wireTransfers.transferFunds(PRIZE_AMOUNT, SERVICE_BANK_ACCOUNT, playerAccount)) {
          notifications.ticketWon(playerDetails, PRIZE_AMOUNT);
        } else {
          notifications.prizeError(playerDetails, PRIZE_AMOUNT);
        }
      } else if (result == LotteryTicketCheckResult.CheckResult.NO_PRIZE) {
        notifications.ticketDidNotWin(playerDetails);
      }
    }
    return numbers;
  }

  /**
   * Begin new lottery round.
   */
  public void resetLottery() {
    repository.deleteAll();
  }
}
