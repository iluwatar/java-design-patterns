---
layout: pattern
title: Retry
folder: retry
permalink: /patterns/retry/zh
categories: Behavioral
language: zh
tags:
  - Performance
  - Cloud distributed
---
## 意图

透明地重试某些涉及与外部资源通信的操作，
特别是在网络上，将调用代码与重试实现细节隔离开来。

＃＃ 解释

重试模式包括通过网络重试对远程资源的操作一定数量的
次。这在很大程度上取决于业务和技术要求：
业务允许最终用户在操作完成时等待吗？有什么表现
峰值负载期间远程资源的特征以及我们的应用程序作为更多线程
正在等待远程资源的可用性？在远程服务返回的错误中，
哪些可以安全地忽略以重试？是否操作
[idempotent](https://en.wikipedia.org/wiki/Idempotence)?

另一个问题是实现重试机制对调用代码的影响。重试
理想情况下，机制应该对调用代码完全透明（服务接口仍然存在）
不变）。这个问题有两种通用方法： 从企业架构
立场（战略）和共享库的立场（战术）。

从战略角度来看，这可以通过将请求重定向到单独的
中介系统，传统上是 [ESB](https://en.wikipedia.org/wiki/Enterprise_service_bus)，
但最近的 [Service Mesh](https://medium.com/microservices-in-practice/service-mesh-for-microservices-2953109a3c9a)。

从战术的角度来看，这可以通过重用共享库来解决，例如
[Hystrix](https://github.com/Netflix/Hystrix)（请注意Hystrix是一个完整的实现
[断路器](https://java-design-patterns.com/patterns/circuit-breaker/) 模式的，
可以将重试模式视为其子集）。这是展示的解决方案类型
随附此 `README.md` 的简单示例。

真实世界的例子

> 我们的应用程序使用提供客户信息的服务。有时服务似乎
> 不稳定并且可能返回错误，或者有时它只是超时。为了规避这些问题，我们
> 应用重试模式。

简单来说

> 重试模式通过网络透明地重试失败的操作。

[Microsoft documentation](https://docs.microsoft.com/en-us/azure/architecture/patterns/retry) 说到
> 使应用程序能够在尝试连接到服务时处理暂时性故障或
> 网络资源，通过透明地重试失败的操作。这样可以提高稳定性
> 应用程序。

**程序示例**

在我们假设的应用程序中，我们有一个用于远程所有操作的通用接口
接口。

```java
public interface BusinessOperation<T> {
  T perform() throws BusinessException;
}
```

我们有这个接口的实现，它通过查找数据库来找到我们的客户。
```java
public final class FindCustomer implements BusinessOperation<String> {
  @Override
  public String perform() throws BusinessException {
    ...
  }
}
```

我们的 `FindCustomer` 实现可以配置为在返回之前抛出 `BusinessException`s
客户的 ID，从而模拟间歇性失败的不稳定服务。有些例外，
像`CustomerNotFoundException`，经过一些假设分析后被认为是可恢复的
因为错误的根本原因源于“某些数据库锁定问题”。然而
`DatabaseNotAvailableException` 被认为是一个明确的表现——应用程序应该
不尝试从此错误中恢复。

我们可以通过像这样实例化 FindCustomer 来模拟一个可恢复的场景：
```java
final var op = new FindCustomer(
    "12345",
    new CustomerNotFoundException("not found"),
    new CustomerNotFoundException("still not found"),
    new CustomerNotFoundException("don't give up yet!")
);
```

在这个配置中，`FindCustomer` 会抛出 `CustomerNotFoundException` 3 次，之后
它将始终返回客户的 ID (`12345`)。

在我们的假设场景中，我们的分析师表示，此操作通常会失败 2-4 次
对于高峰时段的给定输入，并且数据库子系统中的每个工作线程通常
需要 50 毫秒才能“从错误中恢复”。应用这些策略会产生如下结果：
```java
final var op = new Retry<>(
    new FindCustomer(
        "1235",
        new CustomerNotFoundException("not found"),
        new CustomerNotFoundException("still not found"),
        new CustomerNotFoundException("don't give up yet!")
    ),
    5,
    100,
    e -> CustomerNotFoundException.class.isAssignableFrom(e.getClass())
);
```

执行一次 `op` 将自动触发最多 5 次重试尝试，时间为 100 毫秒
尝试之间的延迟，忽略尝试时抛出的任何 `CustomerNotFoundException`。在这
特定场景，由于`FindCustomer`的配置，会有1次初始尝试
并在最终返回所需的结果“12345”之前进行了 3 次额外的重试。

如果我们的“FindCustomer”操作抛出一个致命的“DatabaseNotFoundException”，我们
被指示不要忽略，但更重要的是我们没有指示我们的“重试”忽略，然后
无论尝试多少次，操作都会在收到错误后立即失败
被留下了。

## 类图
![alt text](./etc/retry.png "Retry")

## 适用性

当应用程序需要与外部资源通信时，特别是在云中
环境，如果业务需求允许的话。

## 后果

* *优点:* *

* 弹性
* 提供外部故障的硬数据

* *缺点:* *

* 复杂性
* 操作维护

## 相关模式

* [Circuit Breaker](https://java-design-patterns.com/patterns/circuit-breaker/)

## 鸣谢

* [Retry pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/retry)
* [Cloud Design Patterns: Prescriptive Architecture Guidance for Cloud Applications](https://www.amazon.com/gp/product/1621140369/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=1621140369&linkId=3e3f686af5e60a7a453b48adb286797b)
