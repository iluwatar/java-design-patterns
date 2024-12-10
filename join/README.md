---
title: "Join Pattern in Java: Synchronizing Concurrent Tasks"
shortTitle: Join
description: "Learn the Join Design Pattern in Java with detailed examples and explanations. Understand how to synchronize concurrent tasks and manage execution flow using the Join Pattern. Ideal for developers looking to improve their multithreading and synchronization skills."
category: Behavioral
language: en
issue: #70
tag:
  - Concurrency
  - Synchronization
  - Threads
  - Multithreading
  - Parallel Execution
---

## Intent of Join Design Pattern

The **Join Design Pattern** in Java is used to synchronize multiple concurrent processes or threads so that they must all complete before any subsequent tasks can proceed. This pattern is essential when tasks are executed in parallel, but the subsequent tasks need to wait until all parallel tasks are finished. It allows threads to "join" at a synchronization point and ensures correct execution order and timing.

## Detailed Explanation of Join Pattern with Real-World Examples

#### Real-World Example

Imagine a **construction project** where multiple contractors are working on different aspects of the building simultaneously. The project manager doesn't want to proceed with the final inspection of the building until all the contractors have finished their respective tasks. Using the **Join Design Pattern**, the manager waits for all contractors (threads) to complete their work before proceeding with the inspection (subsequent task). 

This pattern allows the project manager to synchronize all contractors' tasks to ensure that the inspection is only performed once all work is completed.

#### Wikipedia Definition:

> "Join is a synchronization technique that allows multiple concurrent threads or processes to synchronize and wait for the completion of other threads before proceeding to subsequent tasks."

## Programmatic Example of Join Pattern in Java

In this example, we simulate a scenario where four demo tasks run concurrently, and the main thread waits for their completion before proceeding. This is achieved using the **Thread#join()** method, which ensures that the main thread waits for all demo tasks to finish before continuing.

### DemoThreadClass

```java
/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.join;

import lombok.extern.slf4j.Slf4j;

/*
 * DemoThreads implementing Runnable
 */
@Slf4j
public class DemoThread implements Runnable {

    private static int[] executionOrder;
    private static int[] actualExecutionOrder;
    private static int index = 0;
    private static JoinPattern pattern;
    private int id;
    private Thread previous;

    public DemoThread(int id, Thread previous) {
        this.id = id;
        this.previous = previous;

    }

    public static int[] getActualExecutionOrder() {
        return actualExecutionOrder;
    }

    public static void setExecutionOrder(int[] executionOrder, JoinPattern pattern) {
        DemoThread.executionOrder = executionOrder;
        DemoThread.pattern = pattern;
        actualExecutionOrder = new int[executionOrder.length];
    }

    public void run() {
        if (previous != null) {
            try {
                previous.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Logger.info("Thread " + id + " starts");
        try {
            Thread.sleep(id * 250);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Logger.info("Thread " + id + " ends");
            actualExecutionOrder[index++] = id;
            pattern.countdown();

        }
    }

}

