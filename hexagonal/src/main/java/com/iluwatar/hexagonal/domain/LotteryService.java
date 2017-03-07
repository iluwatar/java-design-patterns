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

import com.google.inject.Inject;
import com.iluwatar.hexagonal.banking.WireTransfers;
import com.iluwatar.hexagonal.database.LotteryTicketRepository;
import com.iluwatar.hexagonal.eventlog.LotteryEventLog;

import java.util.Optional;

/**
 * 
 * Implementation for lottery service
 *
 */
public class LotteryService {

  private final LotteryTicketRepository repository;
  private final LotteryEventLog notifications;
  private final WireTransfers wireTransfers;

  /**
   * Constructor
   */
  @Inject
  public LotteryService(LotteryTicketRepository repository, LotteryEventLog notifications,
                        WireTransfers wireTransfers) {
    this.repository = repository;
    this.notifications = notifications;
    this.wireTransfers = wireTransfers;
  }

  /**
   * Submit lottery ticket to participate in the lottery
   */
  public Optional<LotteryTicketId> submitTicket(LotteryTicket ticket) {
    boolean result = wireTransfers.transferFunds(LotteryConstants.TICKET_PRIZE,
        ticket.getPlayerDetails().getBankAccount(), LotteryConstants.SERVICE_BANK_ACCOUNT);
    if (!result) {
      notifications.ticketSubmitError(ticket.getPlayerDetails());
      return Optional.empty();
    }
    Optional<LotteryTicketId> optional = repository.save(ticket);
    if (optional.isPresent()) {
      notifications.ticketSubmitted(ticket.getPlayerDetails());
    }
    return optional;
  }

  /**
   * Check if lottery ticket has won
   */
  public LotteryTicketCheckResult checkTicketForPrize(LotteryTicketId id, LotteryNumbers winningNumbers) {
    return LotteryUtils.checkTicketForPrize(repository, id, winningNumbers);
  }
}
