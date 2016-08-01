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
package com.iluwatar.hexagonal.service;

import java.util.Optional;

import com.iluwatar.hexagonal.banking.WireTransfers;
import com.iluwatar.hexagonal.banking.WireTransfersImpl;
import com.iluwatar.hexagonal.database.LotteryTicketRepository;
import com.iluwatar.hexagonal.database.LotteryTicketInMemoryRepository;
import com.iluwatar.hexagonal.domain.LotteryConstants;
import com.iluwatar.hexagonal.domain.LotteryNumbers;
import com.iluwatar.hexagonal.domain.LotteryTicket;
import com.iluwatar.hexagonal.domain.LotteryTicketCheckResult;
import com.iluwatar.hexagonal.domain.LotteryTicketId;
import com.iluwatar.hexagonal.domain.LotteryTicketCheckResult.CheckResult;
import com.iluwatar.hexagonal.notifications.LotteryNotifications;
import com.iluwatar.hexagonal.notifications.LotteryNotificationsImpl;

/**
 * 
 * Implementation for lottery service
 *
 */
public class LotteryServiceImpl implements LotteryService {

  private final LotteryTicketRepository repository;

  private final WireTransfers bank = new WireTransfersImpl();

  private final LotteryNotifications notifications = new LotteryNotificationsImpl();

  /**
   * Constructor
   */
  public LotteryServiceImpl() {
    repository = new LotteryTicketInMemoryRepository();
  }
  
  @Override
  public Optional<LotteryTicketId> submitTicket(LotteryTicket ticket) {
    boolean result = bank.transferFunds(LotteryConstants.TICKET_PRIZE, ticket.getPlayerDetails().getBankAccount(),
        LotteryConstants.SERVICE_BANK_ACCOUNT);
    if (result == false) {
      notifications.notifyTicketSubmitError(ticket.getPlayerDetails());
      return Optional.empty();
    }
    Optional<LotteryTicketId> optional = repository.save(ticket);
    if (optional.isPresent()) {
      notifications.notifyTicketSubmitted(ticket.getPlayerDetails());
    }
    return optional;
  }

  @Override
  public LotteryTicketCheckResult checkTicketForPrize(LotteryTicketId id, LotteryNumbers winningNumbers) {
    Optional<LotteryTicket> optional = repository.findById(id);
    if (optional.isPresent()) {
      if (optional.get().getNumbers().equals(winningNumbers)) {
        return new LotteryTicketCheckResult(CheckResult.WIN_PRIZE, 1000);
      } else {
        return new LotteryTicketCheckResult(CheckResult.NO_PRIZE);
      }
    } else {
      return new LotteryTicketCheckResult(CheckResult.TICKET_NOT_SUBMITTED);
    }
  }
}
