package com.iluwatar.monitor;

import java.util.Arrays;
import java.util.logging.Logger;

/** Bank Definition. */
public class Bank {

  private final int[] accounts;
  Logger logger;

  /**
   * Constructor.
   *
   * @param accountNum - account number
   * @param baseAmount - base amount
   * @param logger - logger object
   */
  public Bank(int accountNum, int baseAmount, Logger logger) {
    this.logger = logger;
    accounts = new int[accountNum];
    Arrays.fill(accounts, baseAmount);
  }

  /**
   * Transfer amounts from one account to another.
   *
   * @param accountA - source account
   * @param accountB - destination account
   * @param amount - amount to be transferred
   */
  public synchronized void transfer(int accountA, int accountB, int amount) {
    if (accounts[accountA] >= amount) {
      accounts[accountB] += amount;
      accounts[accountA] -= amount;
      logger.info(
          String.format(
              "Transferred from account: %s to account: %s , amount: %s , balance: %s",
              accountA, accountB, amount, getBalance()));
    }
  }

  /**
   * Calculates the total balance.
   *
   * @return balance
   */
  public synchronized int getBalance() {
    int balance = 0;
    for (int account : accounts) {
      balance += account;
    }
    return balance;
  }
}
