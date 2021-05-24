---
layout: pattern
title: Circuit Breaker
folder: circuit-breaker
permalink: /patterns/circuit-breaker/
categories: Behavioral
tags:
  - Performance
  - Decoupling
  - Cloud distributed
---

## 含义

以这样的方式（译者：指断路器方式）处理昂贵的远程服务调用，可以防止单个服务/组件的故障导致整个应用程序崩溃，同时我们可以尽快地进行服务重连。

## 解释

现实世界案例

> 设想一下，一个网络应用程序既有本地文件/图像，又有用于获取数据的远程服务。这些远程服务可能在某些时候是健康的、有反应的，也可能在某些时候由于各种原因而变得缓慢和无反应。因此，如果其中一个远程服务速度慢或不能成功响应，我们的应用程序将尝试使用多个线程/进程从远程服务中获取响应，很快所有的线程/进程都会挂起（也称为线程饥饿 thread starvation），从而导致我们整个 Web 应用程序崩溃。我们应该能够检测到这种情况，并向用户显示一个适当的信息，以便用户可以探索应用程序的其他部分，而不受远程服务故障的影响。同时，其他正常工作的服务应该保持运作，不受这次故障的影响。

简而言之

> 断路器允许优雅地处理失败的远程服务。当我们的应用程序的所有部分都高度解耦时，这种方式的效果会很好，一个组件的失败并不会导致其他部分停止工作。

维基百科的解释

> 断路器是现代软件开发中使用的一种设计模式。它用于检测故障，并封装了防止故障不断复发的逻辑，在维护期间，临时地处理外部系统故障或意外的系统问题。

## Programmatic Example

那么，这一切是如何实现的呢？考虑到上面的例子，我们将在一个简单的例子中模拟这个功能。一个监控服务（译者：下图的 Monitoring Service）模拟了网络应用，进行本地和远程调用。

该服务架构如下：

![alt text](../../circuit-breaker/etc/ServiceDiagram.png "Service Diagram")

终端用户（译者：上图的 End User）应用的代码如下：

```java
@Slf4j
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    var serverStartTime = System.nanoTime();

    var delayedService = new DelayedRemoteService(serverStartTime, 5);
    var delayedServiceCircuitBreaker = new DefaultCircuitBreaker(delayedService, 3000, 2,
        2000 * 1000 * 1000);

    var quickService = new QuickRemoteService();
    var quickServiceCircuitBreaker = new DefaultCircuitBreaker(quickService, 3000, 2,
        2000 * 1000 * 1000);

    //Create an object of monitoring service which makes both local and remote calls
    var monitoringService = new MonitoringService(delayedServiceCircuitBreaker,
        quickServiceCircuitBreaker);

    //Fetch response from local resource
    LOGGER.info(monitoringService.localResourceResponse());

    //Fetch response from delayed service 2 times, to meet the failure threshold
    LOGGER.info(monitoringService.delayedServiceResponse());
    LOGGER.info(monitoringService.delayedServiceResponse());

    //Fetch current state of delayed service circuit breaker after crossing failure threshold limit
    //which is OPEN now
    LOGGER.info(delayedServiceCircuitBreaker.getState());

    //Meanwhile, the delayed service is down, fetch response from the healthy quick service
    LOGGER.info(monitoringService.quickServiceResponse());
    LOGGER.info(quickServiceCircuitBreaker.getState());

    //Wait for the delayed service to become responsive
    try {
      LOGGER.info("Waiting for delayed service to become responsive");
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //Check the state of delayed circuit breaker, should be HALF_OPEN
    LOGGER.info(delayedServiceCircuitBreaker.getState());

    //Fetch response from delayed service, which should be healthy by now
    LOGGER.info(monitoringService.delayedServiceResponse());
    //As successful response is fetched, it should be CLOSED again.
    LOGGER.info(delayedServiceCircuitBreaker.getState());
  }
}
```

监控服务代码（译者：上图的 monitoring service）：

```java
public class MonitoringService {

  private final CircuitBreaker delayedService;

  private final CircuitBreaker quickService;

  public MonitoringService(CircuitBreaker delayedService, CircuitBreaker quickService) {
    this.delayedService = delayedService;
    this.quickService = quickService;
  }

  //Assumption: Local service won't fail, no need to wrap it in a circuit breaker logic
  public String localResourceResponse() {
    return "Local Service is working";
  }

  /**
   * Fetch response from the delayed service (with some simulated startup time).
   *
   * @return response string
   */
  public String delayedServiceResponse() {
    try {
      return this.delayedService.attemptRequest();
    } catch (RemoteServiceException e) {
      return e.getMessage();
    }
  }

  /**
   * Fetches response from a healthy service without any failure.
   *
   * @return response string
   */
  public String quickServiceResponse() {
    try {
      return this.quickService.attemptRequest();
    } catch (RemoteServiceException e) {
      return e.getMessage();
    }
  }
}
```
可以看出，它直接进行了获取本地资源的调用，但它把对远程（昂贵的）服务的调用包装在一个断路器对象中，这样可以防止出现如下故障：

```java
public class DefaultCircuitBreaker implements CircuitBreaker {

  private final long timeout;
  private final long retryTimePeriod;
  private final RemoteService service;
  long lastFailureTime;
  private String lastFailureResponse;
  int failureCount;
  private final int failureThreshold;
  private State state;
  private final long futureTime = 1000 * 1000 * 1000 * 1000;

  /**
   * Constructor to create an instance of Circuit Breaker.
   *
   * @param timeout          Timeout for the API request. Not necessary for this simple example
   * @param failureThreshold Number of failures we receive from the depended service before changing
   *                         state to 'OPEN'
   * @param retryTimePeriod  Time period after which a new request is made to remote service for
   *                         status check.
   */
  DefaultCircuitBreaker(RemoteService serviceToCall, long timeout, int failureThreshold,
      long retryTimePeriod) {
    this.service = serviceToCall;
    // We start in a closed state hoping that everything is fine
    this.state = State.CLOSED;
    this.failureThreshold = failureThreshold;
    // Timeout for the API request.
    // Used to break the calls made to remote resource if it exceeds the limit
    this.timeout = timeout;
    this.retryTimePeriod = retryTimePeriod;
    //An absurd amount of time in future which basically indicates the last failure never happened
    this.lastFailureTime = System.nanoTime() + futureTime;
    this.failureCount = 0;
  }

  // Reset everything to defaults
  @Override
  public void recordSuccess() {
    this.failureCount = 0;
    this.lastFailureTime = System.nanoTime() + futureTime;
    this.state = State.CLOSED;
  }

  @Override
  public void recordFailure(String response) {
    failureCount = failureCount + 1;
    this.lastFailureTime = System.nanoTime();
    // Cache the failure response for returning on open state
    this.lastFailureResponse = response;
  }

  // Evaluate the current state based on failureThreshold, failureCount and lastFailureTime.
  protected void evaluateState() {
    if (failureCount >= failureThreshold) { //Then something is wrong with remote service
      if ((System.nanoTime() - lastFailureTime) > retryTimePeriod) {
        //We have waited long enough and should try checking if service is up
        state = State.HALF_OPEN;
      } else {
        //Service would still probably be down
        state = State.OPEN;
      }
    } else {
      //Everything is working fine
      state = State.CLOSED;
    }
  }

  @Override
  public String getState() {
    evaluateState();
    return state.name();
  }

  /**
   * Break the circuit beforehand if it is known service is down Or connect the circuit manually if
   * service comes online before expected.
   *
   * @param state State at which circuit is in
   */
  @Override
  public void setState(State state) {
    this.state = state;
    switch (state) {
      case OPEN:
        this.failureCount = failureThreshold;
        this.lastFailureTime = System.nanoTime();
        break;
      case HALF_OPEN:
        this.failureCount = failureThreshold;
        this.lastFailureTime = System.nanoTime() - retryTimePeriod;
        break;
      default:
        this.failureCount = 0;
    }
  }

  /**
   * Executes service call.
   *
   * @return Value from the remote resource, stale response or a custom exception
   */
  @Override
  public String attemptRequest() throws RemoteServiceException {
    evaluateState();
    if (state == State.OPEN) {
      // return cached response if the circuit is in OPEN state
      return this.lastFailureResponse;
    } else {
      // Make the API request if the circuit is not OPEN
      try {
        //In a real application, this would be run in a thread and the timeout
        //parameter of the circuit breaker would be utilized to know if service
        //is working. Here, we simulate that based on server response itself
        var response = service.call();
        // Yay!! the API responded fine. Let's reset everything.
        recordSuccess();
        return response;
      } catch (RemoteServiceException ex) {
        recordFailure(ex.getMessage());
        throw ex;
      }
    }
  }
}
```

上述模式是如何防止失败的呢？让我们通过它所实现的这个有限状态机来了解。

![alt text](../../circuit-breaker/etc/StateDiagram.png "State Diagram")

- 我们用 `timeout`（超时）、 `failureThreshold` （失败阈值）、`retryTimePeriod`（重试时间周期） 参数初始化断路器对象 ，用于确定 API 的适应性。
- 最初，断路器处于 `closed`  关闭状态，没有发生对 API 的远程调用。
- 每次调用成功，我们就把状态重置为开始时的样子。
- 如果失败的次数超过了一定的阈值（`failureThreshold`），断路器就会进入 `open`  开启状态，它的作用就像一个开启的电路，阻止远程服务的调用，从而节省资源。
- 一旦我们超过重试时间周期（`retryTimePeriod`），断路器就会转到 `half-open` 半启用状态，并再次调用远程服务，检查服务是否正常，以便我们可以提供最新的响应内容。如果远程服务调用失败会使断路器回到  `open`  状态，并在重试超时后进行另一次尝试；如果远程服务调用成功则使断路器进入 `closed` 状态，这样一切又开始正常工作。

## 类图

![alt text](../../circuit-breaker/etc/circuit-breaker.urm.png "Circuit Breaker class diagram")

## 适用场景

在以下场景下，可以使用断路器模式：

- 构建一个高可用的应用程序，某些些服务的失败不会导致整个应用程序的崩溃。
- 构建一个持续运行（长期在线）的应用程序，以便其组件可以在不完全关闭的情况下进行升级。

## 相关模式

- [Retry Pattern](https://github.com/iluwatar/java-design-patterns/tree/master/retry)

## 现实案例

* [Spring Circuit Breaker module](https://spring.io/guides/gs/circuit-breaker)
* [Netflix Hystrix API](https://github.com/Netflix/Hystrix)

## 引用

* [Understanding Circuit Breaker Pattern](https://itnext.io/understand-circuitbreaker-design-pattern-with-simple-practical-example-92a752615b42)
* [Martin Fowler on Circuit Breaker](https://martinfowler.com/bliki/CircuitBreaker.html)
* [Fault tolerance in a high volume, distributed system](https://medium.com/netflix-techblog/fault-tolerance-in-a-high-volume-distributed-system-91ab4faae74a)
* [Circuit Breaker pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/circuit-breaker)