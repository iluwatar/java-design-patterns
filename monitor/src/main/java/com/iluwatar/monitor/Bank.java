package com.iluwatar.monitor;

import java.util.Arrays;
import java.util.logging.Logger;

// Bank class implements the Monitor pattern
public class Bank {

    private int[] accounts;
    Logger logger;

    public Bank(int accountNum, int baseAmount, Logger logger) {
        this.logger = logger;
        accounts = new int[accountNum];
        Arrays.fill(accounts, baseAmount);
    }

    public synchronized void transfer(int accountA, int accountB, int amount) {
        if (accounts[accountA] >= amount) {
            accounts[accountB] += amount;
            accounts[accountA] -= amount;
            logger.info("Transferred from account :" + accountA + " to account :" + accountB + " , amount :" + amount + " . balance :" + getBalance());
        }
    }

    public synchronized int getBalance() {
        int balance = 0;
        for (int account : accounts) {
            balance += account;
        }
        return balance;
    }

    public int[] getAccounts() {
        return accounts;
    }
}
