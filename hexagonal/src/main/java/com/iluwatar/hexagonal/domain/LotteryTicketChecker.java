package com.iluwatar.hexagonal.domain;

import com.iluwatar.hexagonal.database.LotteryTicketRepository;

import java.util.Optional;

/**
 * Lottery ticket checker
 */
public class LotteryTicketChecker {

  private final LotteryTicketRepository repository;

  public LotteryTicketChecker(LotteryTicketRepository repository) {
    this.repository = repository;
  }

  /**
   * Check if lottery ticket has won
   */
  public LotteryTicketCheckResult checkTicketForPrize(LotteryTicketId id, LotteryNumbers winningNumbers) {
    Optional<LotteryTicket> optional = repository.findById(id);
    if (optional.isPresent()) {
      if (optional.get().getNumbers().equals(winningNumbers)) {
        return new LotteryTicketCheckResult(LotteryTicketCheckResult.CheckResult.WIN_PRIZE, 1000);
      } else {
        return new LotteryTicketCheckResult(LotteryTicketCheckResult.CheckResult.NO_PRIZE);
      }
    } else {
      return new LotteryTicketCheckResult(LotteryTicketCheckResult.CheckResult.TICKET_NOT_SUBMITTED);
    }
  }
}
