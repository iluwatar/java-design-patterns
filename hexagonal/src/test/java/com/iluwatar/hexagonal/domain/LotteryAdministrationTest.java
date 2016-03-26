package com.iluwatar.hexagonal.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.iluwatar.hexagonal.database.LotteryTicketRepositoryMock;

/**
 * 
 * Tests for lottery administration
 *
 */
public class LotteryAdministrationTest {

  private LotteryTicketRepository repository = new LotteryTicketRepositoryMock();
  private LotteryAdministration admin = new LotteryAdministrationImpl();
  
  @Before
  public void submitTickets() {
    repository.save(LotteryTestUtils.createLotteryTicket());
    repository.save(LotteryTestUtils.createLotteryTicket());
    repository.save(LotteryTestUtils.createLotteryTicket());
    repository.save(LotteryTestUtils.createLotteryTicket());
    repository.save(LotteryTestUtils.createLotteryTicket());
  }
  
  @Test
  public void testGetAllTickets() {
    assertEquals(admin.getAllSubmittedTickets().size(), 4);
  }
  
  @Test
  public void testPerformLottery() {
    assertEquals(admin.performLottery().getNumbers().size(), 4);
  }
}
