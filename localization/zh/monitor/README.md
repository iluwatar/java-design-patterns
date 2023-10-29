---
title: Monitor
category: Concurrency
language: zh
tag:
 - Performance
---

## 或称

监控对象模式

## 目的

主要目的是为多个线程或进程提供一种结构化和受控的方式来安全地访问和操作共享资源，例如变量、数据结构或代码的关键部分，而不会导致冲突或竞争条件。

## 解释

通俗的说

> 监视器模式用于强制对数据进行单线程访问。 一次只允许一个线程在监视器对象内执行代码。

维基百科说

> 在并发编程（也称为并行编程）中，监视器是一种同步构造，它允许线程具有互斥性和等待（阻止）特定条件变为假的能力。 监视器还具有向其他线程发出信号通知其条件已满足的机制。

**程序示例**

考虑有一家银行通过转账方式将钱从一个帐户转移到另一个帐户。 它是`同步`意味着只有一个线程可以访问此方法，因为如果许多线程访问它并在同一时间将资金从一个帐户转移到另一个帐户，则余额会发生变化！
 
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

getBalance 始终返回总金额，并且每次转账后总金额应相同

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

## 类图
![alt text](./etc/monitor.urm.png "Monitor class diagram")

## 适用性

监视器设计模式应该用于具有需要由多个线程或进程同时访问和操作的共享资源的情况。 此模式在需要同步以防止竞争条件、数据损坏和不一致状态的情况下特别有用。 以下是您应该考虑使用监视器模式的一些情况：

1. **共享数据**：当您的应用程序涉及需要由多个线程访问和更新的共享数据结构、变量或资源时。 监视器确保一次只有一个线程可以访问共享资源，从而防止冲突并确保数据一致性。

2. **关键部分**：当您有代码的关键部分一次只需要由一个线程执行时。 关键部分是操作共享资源的代码部分，并发访问可能会导致问题。 监视器有助于确保在任何给定时间只有一个线程可以执行关键部分。

3. **线程安全**：当您需要确保线程安全而不是仅仅依赖锁和信号量等低级同步机制时。 监视器提供了封装同步和资源管理的更高级别的抽象。

4. **等待和发信号**：当您遇到线程需要等待满足某些条件才能继续操作时。 监视器通常包含线程等待特定条件以及其他线程在满足条件时通知它们的机制。

5. **死锁预防**：当您希望通过提供结构化方式来获取和释放共享资源上的锁来防止死锁时。 监视器通过确保资源访问得到良好管理来帮助避免常见的死锁情况。

6. **并发数据结构**：当您实现并发数据结构（例如队列、堆栈或哈希表）时，多个线程需要操作该结构，同时保持其完整性。

7. **资源共享**：当多个线程需要共享有限的资源时，例如连接数据库或访问网络套接字。 监视器可以帮助以受控方式管理这些资源的分配和释放。

8. **改进可维护性**：当您想要将同步逻辑和共享资源管理封装在单个对象中时，改进代码组织并使并发相关代码更容易推理。

但是，需要注意的是，监视器模式可能并不最适合所有并发场景。 在某些情况下，其他同步机制（例如锁、信号量或并发数据结构）可能更合适。 此外，现代编程语言和框架通常提供更高级别的并发结构，抽象出低级别同步的复杂性。

在应用监视器模式之前，建议彻底分析应用程序的并发需求，并选择最适合您需求的同步方法，同时考虑性能、复杂性和可用语言功能等因素。

## 相关模式

* Active object
* Double-checked locking
