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
 * <p>The Monitor pattern is used in concurrent algorithms to achieve mutual exclusion.</p>
 *
 * <p>Bank is a simple class that transfers money from an account to another account using
 * {@link Bank#transfer}. It can also return the balance of the bank account stored in the bank.</p>
 *
 * <p>Main class uses ThreadPool to run threads that do transactions on the bank accounts.</p>
 */

public class Main {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("monitor");
        var bank = new Bank(4, 1000, logger);
        Runnable runnable = () -> {
            try {
                Thread.sleep((long) (Math.random() * 1000));
                Random random = new Random();
                for (int i = 0; i < 1000000; i++)
                    bank.transfer(random.nextInt(4), random.nextInt(4), (int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                logger.info(e.getMessage());
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(runnable);
        }
    }
}