package com.iluwatar.monitor;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        AtomicReference<Bank> bank = new AtomicReference<>();
        bank.set(new Bank(4, 1000));
        Runnable runnable = () -> {
            try {
                Thread.sleep((long) (Math.random() * 1000));
                Random random = new Random();
                for (int i = 0; i < 1000000; i++)
                    bank.get().transfer(random.nextInt(4), random.nextInt(4), (int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(runnable);
        }
    }
}

//We call this class as monitor !
class Bank {

    private int[] accounts;
    Logger logger = Logger.getLogger("monitor");

    public Bank(int accountNum, int baseAmount) {
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

    private synchronized int getBalance() {
        int balance = 0;
        for (int account : accounts) {
            balance += account;
        }
        return balance;
    }
}
