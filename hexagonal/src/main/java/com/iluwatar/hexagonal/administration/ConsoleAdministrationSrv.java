package com.iluwatar.hexagonal.administration;

/**
 * Console interface for lottery administration
 */
public interface ConsoleAdministrationSrv {

  /**
   * Get all submitted tickets
   */
  void getAllSubmittedTickets();

  /**
   * Draw lottery numbers
   */
  void performLottery();

  /**
   * Begin new lottery round
   */
  void resetLottery();
}
