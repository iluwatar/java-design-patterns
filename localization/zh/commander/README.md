---
title: Commander
category: Concurrency
language: zh
tag:
 - Cloud distributed
---

## 目的

> 用于处理执行分布式事务时可能遇到的所有问题。

## 类图
![alt text](./etc/commander.urm.png "Commander class diagram")

## 适用场合
当我们需要提交两个数据库去完成事务，提交不是原子性且可能因此造成问题时，适合用这个设计模式。

## 解释
处理分布式事务很棘手，但如果我们不仔细处理，可能会带来不想要的后果。假设我们有一个电子商务网站，它有一个支付微服务和一个运输微服务。如果当前运输可用，但支付服务不可用，或者反之，当我们已经收到用户的订单后，我们应该如何处理？我们需要有一个机制来处理这些情况。我们必须将订单指向其中一个服务（在这个例子中是运输），然后将订单添加到另一个服务的数据库中（在这个例子中是支付），因为两个数据库不能原子地更新。如果我们当前无法做到这一点，应该有一个队列，可以将这个请求排队，并且必须有一个机制，允许队列中出现失败。所有这些都需要通过不断的重试，在保证幂等性（即使请求多次，变化只应用一次）的情况下，由一个指挥类来完成，以达到最终一致性的状态。

## 鸣谢

* [Distributed Transactions: The Icebergs of Microservices](https://www.grahamlea.com/2016/08/distributed-transactions-microservices-icebergs/)
