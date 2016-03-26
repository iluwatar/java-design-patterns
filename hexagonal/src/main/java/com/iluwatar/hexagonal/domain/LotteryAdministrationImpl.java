package com.iluwatar.hexagonal.domain;

import java.util.Map;

import com.iluwatar.hexagonal.database.LotteryTicketRepositoryMock;

/**
 * 
 * Lottery administration implementation
 *
 */
public class LotteryAdministrationImpl implements LotteryAdministration {

  private final LotteryTicketRepository repository;

  public LotteryAdministrationImpl() {
    repository = new LotteryTicketRepositoryMock();
  }
  
  @Override
  public Map<LotteryTicketId, LotteryTicket> getAllSubmittedTickets() {
    return repository.findAll();
  }

  @Override
  public LotteryNumbers performLottery() {
    return LotteryNumbers.createRandom();
  }
}
