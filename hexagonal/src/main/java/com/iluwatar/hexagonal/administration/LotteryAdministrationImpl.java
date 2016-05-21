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
package com.iluwatar.hexagonal.administration;

import java.util.Map;

import com.iluwatar.hexagonal.banking.WireTransfers;
import com.iluwatar.hexagonal.banking.WireTransfersImpl;
import com.iluwatar.hexagonal.database.LotteryTicketRepository;
import com.iluwatar.hexagonal.database.LotteryTicketInMemoryRepository;
import com.iluwatar.hexagonal.domain.LotteryConstants;
import com.iluwatar.hexagonal.domain.LotteryNumbers;
import com.iluwatar.hexagonal.domain.LotteryTicket;
import com.iluwatar.hexagonal.domain.LotteryTicketCheckResult;
import com.iluwatar.hexagonal.domain.LotteryTicketCheckResult.CheckResult;
import com.iluwatar.hexagonal.domain.LotteryTicketId;
import com.iluwatar.hexagonal.notifications.LotteryNotifications;
import com.iluwatar.hexagonal.notifications.LotteryNotificationsImpl;
import com.iluwatar.hexagonal.service.LotteryService;
import com.iluwatar.hexagonal.service.LotteryServiceImpl;

/**
 * 
 * Lottery administration implementation
 *
 */
public class LotteryAdministrationImpl implements LotteryAdministration {

  private final LotteryTicketRepository repository;
  private final LotteryService service = new LotteryServiceImpl();
  private final LotteryNotifications notifications = new LotteryNotificationsImpl();
  private final WireTransfers bank = new WireTransfersImpl();
  public LotteryAdministrationImpl() {
    repository = new LotteryTicketInMemoryRepository();
  }
  
  @Override
  public Map<LotteryTicketId, LotteryTicket> getAllSubmittedTickets() {
    return repository.findAll();
  }

  @Override
  public LotteryNumbers performLottery() {
    LotteryNumbers numbers = LotteryNumbers.createRandom();
    Map<LotteryTicketId, LotteryTicket> tickets = getAllSubmittedTickets();
    for (LotteryTicketId id: tickets.keySet()) {
      LotteryTicketCheckResult result = service.checkTicketForPrize(id, numbers);
      if (result.getResult().equals(CheckResult.WIN_PRIZE)) {
        boolean transferred = bank.transferFunds(LotteryConstants.PRIZE_AMOUNT, LotteryConstants.SERVICE_BANK_ACCOUNT, 
            tickets.get(id).getPlayerDetails().getBankAccount());
        if (transferred) {
          notifications.notifyPrize(tickets.get(id).getPlayerDetails(), LotteryConstants.PRIZE_AMOUNT);
        } else {
          notifications.notifyPrizeError(tickets.get(id).getPlayerDetails(), LotteryConstants.PRIZE_AMOUNT);
        }
      } else if (result.getResult().equals(CheckResult.NO_PRIZE)) {
        notifications.notifyNoWin(tickets.get(id).getPlayerDetails());
      }
    }
    return numbers;
  }

  @Override
  public void resetLottery() {
    repository.deleteAll();
  }
}
