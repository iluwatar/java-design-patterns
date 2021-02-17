/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.monitor;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 *
 * <p>The Monitor pattern is use in concurrent algorithm and a good way to achieve mutual exclusive
 * and a good solution to prevent critical section </p>
 *
 * <p>Bank class is a simple class that transfer amount from an account to another account using
 * {@link Bank#transfer} and get balance return total amount that store in bank (default amount specify in constructor)</p>
 *
 * <p>In Main class uses ThreadPool to run some threads for do some transactions</p>
 *
 */

public class Main {

    public static void main(String[] args) {
        var bank = new Bank(4, 1000);
        Runnable runnable = () -> {
            try {
                Thread.sleep((long) (Math.random() * 1000));
                Random random = new Random();
                for (int i = 0; i < 1000000; i++)
                    bank.transfer(random.nextInt(4), random.nextInt(4), (int) (Math.random() * 1000));
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
