package com.iluwatar.monitor;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

/** Bank Definition. */
@Slf4j
public class Bank {

  private final int[] accounts;

  /**
   * Constructor.
   *
   * @param accountNum - account number
   * @param baseAmount - base amount
   */
  public Bank(int accountNum, int baseAmount) {
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
      LOGGER.info(
          "Transferred from account: {} to account: {} , amount: {} , balance: {}",
          accountA,
          accountB,
          amount,
          getBalance());
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
