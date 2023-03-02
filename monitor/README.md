---
title: Monitor
category: Concurrency
language: en
tag:
 - Performance
---

## Intent
Monitor pattern is used to create thread-safe objects and prevent conflicts between threads in concurrent applications.

## Explanation

In plain words

> Monitor pattern is used to enforce single-threaded access to data. Only one thread at a time is allowed to execute code within the monitor object.

Wikipedia says

> In concurrent programming (also known as parallel programming), a monitor is a synchronization construct that allows threads to have both mutual exclusion and the ability to wait (block) for a certain condition to become false. Monitors also have a mechanism for signaling other threads that their condition has been met.

**Programmatic Examples**

Consider there is a bank that transfers money from an account to another account with transfer method . it is `synchronized` mean just one thread can access to this method because if many threads access to it and transfer money from an account to another account in same time balance changed !   
 
```
class Bank {

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
```

getBalance always return total amount and the total amount should be same after each transfers 

```
     private synchronized int getBalance() {
         int balance = 0;
         for (int account : accounts) {
             balance += account;
         }
         return balance;
     }
 }
```

## Class diagram
![alt text](./etc/monitor.urm.png "Monitor class diagram")

## Applicability
Use the Monitor pattern when

* we have a shared resource and there is critical section .
* you want to create thread-safe objects .
* you want to achieve mutual exclusion in high level programming language .
