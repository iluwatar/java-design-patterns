package com.iluwatar.hexagonal.administration;

import com.iluwatar.hexagonal.domain.LotteryAdministration;
import com.iluwatar.hexagonal.domain.LotteryNumbers;
import org.slf4j.Logger;

/**
 * Console implementation for lottery administration
 */
public class ConsoleAdministrationSrvImpl implements ConsoleAdministrationSrv {
  private final LotteryAdministration administration;
  private final Logger logger;

  /**
   * Constructor
   */
  public ConsoleAdministrationSrvImpl(LotteryAdministration administration, Logger logger) {
    this.administration = administration;
    this.logger = logger;
  }

  @Override
  public void getAllSubmittedTickets() {
    administration.getAllSubmittedTickets().forEach((k, v) -> logger.info("Key: {}, Value: {}", k, v));
  }

  @Override
  public void performLottery() {
    LotteryNumbers numbers = administration.performLottery();
    logger.info("The winning numbers: {}", numbers.getNumbersAsString());
    logger.info("Time to reset the database for next round, eh?");
  }

  @Override
  public void resetLottery() {
    administration.resetLottery();
    logger.info("The lottery ticket database was cleared.");
  }
}
