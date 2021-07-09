---
layout: pattern
title: Commander
folder: commander
permalink: /patterns/commander/zh
categories: Concurrency
language: zh
tags:
 - Cloud distributed
---

## 目的

> 用于处理做分布式事务时可能遇到的所有问题。

## 类图
![alt text](../../../commander/etc/commander.urm.png "Commander class diagram")

## 适用性
当我们需要提交到 2 个（或更多）数据库以完成事务时，可以使用此模式，这不能以原子方式完成，因此会产生问题。

## 解释
处理分布式事务可能很棘手，但如果我们选择不小心处理它，可能会产生不必要的后果。比如说，我们有一个电子商务网站，
它有一个支付微服务和一个运输微服务。 如果目前可以发货，但是支付服务没有开通，反之亦然，我们已经收到用户的订单后如何处理？
我们需要一种机制来处理这些情况。我们必须将订单定向到其中一项服务（在此示例中为发货），然后将订单添加到另一项服务（在此示例中为支付）的数据库中，因为无法自动更新两个数据库。如果当前无法做到，则应该有一个队列可以将这个请求排队，并且必须有一种机制来允许排队失败。所有这一切都需要通过不断重试来完成，同时确保指挥官类的幂等性（即使请求多次，更改也应该只应用一次），以达到最终一致性的状态。
## Credits

* [https://www.grahamlea.com/2016/08/distributed-transactions-microservices-icebergs/]
