---
title: Ambassador
category: Structural
language: zh
tag:
  - Decoupling
  - Cloud distributed
---

## 目的

在客户端上提供帮助程序服务实例，并从共享资源上转移常用功能。

## 解释

真实世界例子

> 远程服务有许多客户端访问它提供的功能。 该服务是旧版应用程序，无法更新。 用户的大量请求导致连接问题。新的请求频率规则需要同时实现延迟检测和客户端日志功能。

通俗的说

> 使用“大使”模式，我们可以实现来自客户端的频率较低的轮询以及延迟检查和日志记录。

微软文档做了如下阐述

> 可以将大使服务视为与客户端位于同一位置的进程外代理。 此模式对于以语言不可知的方式减轻常见的客户端连接任务（例如监视，日志记录，路由，安全性（如TLS）和弹性模式）的工作很有用。 它通常与旧版应用程序或其他难以修改的应用程序一起使用，以扩展其网络功能。 它还可以使专业团队实现这些功能。

**程序示例**

有了上面的介绍我们将在这个例子中模仿功能。我们有一个用远程服务实现的接口，同时也是大使服务。

```java
interface RemoteServiceInterface {
    long doRemoteFunction(int value) throws Exception;
}
```

表示为单例的远程服务。

```java
public class RemoteService implements RemoteServiceInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteService.class);
    private static RemoteService service = null;

    static synchronized RemoteService getRemoteService() {
        if (service == null) {
            service = new RemoteService();
        }
        return service;
    }

    private RemoteService() {}

    @Override
    public long doRemoteFunction(int value) {
        long waitTime = (long) Math.floor(Math.random() * 1000);

        try {
            sleep(waitTime);
        } catch (InterruptedException e) {
            LOGGER.error("Thread sleep interrupted", e);
        }

        return waitTime >= 200 ? value * 10 : -1;
    }
}
```

服务大使添加了像日志和延迟检测的额外功能

```java
public class ServiceAmbassador implements RemoteServiceInterface {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServiceAmbassador.class);
  private static final int RETRIES = 3;
  private static final int DELAY_MS = 3000;

  ServiceAmbassador() {
  }

  @Override
  public long doRemoteFunction(int value) {
    return safeCall(value);
  }

  private long checkLatency(int value) {
    var startTime = System.currentTimeMillis();
    var result = RemoteService.getRemoteService().doRemoteFunction(value);
    var timeTaken = System.currentTimeMillis() - startTime;

    LOGGER.info("Time taken (ms): " + timeTaken);
    return result;
  }

  private long safeCall(int value) {
    var retries = 0;
    var result = (long) FAILURE;

    for (int i = 0; i < RETRIES; i++) {
      if (retries >= RETRIES) {
        return FAILURE;
      }

      if ((result = checkLatency(value)) == FAILURE) {
        LOGGER.info("Failed to reach remote: (" + (i + 1) + ")");
        retries++;
        try {
          sleep(DELAY_MS);
        } catch (InterruptedException e) {
          LOGGER.error("Thread sleep state interrupted", e);
        }
      } else {
        break;
      }
    }
    return result;
  }
}
```

客户端具有用于与远程服务进行交互的本地服务大使：

```java
public class Client {

  private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
  private final ServiceAmbassador serviceAmbassador = new ServiceAmbassador();

  long useService(int value) {
    var result = serviceAmbassador.doRemoteFunction(value);
    LOGGER.info("Service result: " + result);
    return result;
  }
}
```

这是两个使用该服务的客户端。

```java
public class App {
  public static void main(String[] args) {
    var host1 = new Client();
    var host2 = new Client();
    host1.useService(12);
    host2.useService(73);
  }
}
```

Here's the output for running the example:

```java
Time taken (ms): 111
Service result: 120
Time taken (ms): 931
Failed to reach remote: (1)
Time taken (ms): 665
Failed to reach remote: (2)
Time taken (ms): 538
Failed to reach remote: (3)
Service result: -1
```

## 类图

![alt text](./etc/ambassador.urm.png "Ambassador class diagram")

## 适用性

大使适用于无法修改或极难修改的旧式远程服务。 可以在客户端上实现连接性的功能，而无需更改远程服务。

* 大使提供了用于远程服务的本地接口。
* 大使在客户端上提供日志记录，断路，重试和安全性。

## 典型用例

* 控制对另一个对象的访问
* 实现日志
* 卸载远程服务任务
* 简化网络连接

## 已知使用

* [Kubernetes-native API gateway for microservices](https://github.com/datawire/ambassador)

## 相关模式

* [Proxy](https://java-design-patterns.com/patterns/proxy/)

## 鸣谢

* [Ambassador pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/ambassador)
* [Designing Distributed Systems: Patterns and Paradigms for Scalable, Reliable Services](https://books.google.co.uk/books?id=6BJNDwAAQBAJ&pg=PT35&lpg=PT35&dq=ambassador+pattern+in+real+world&source=bl&ots=d2e7GhYdHi&sig=Lfl_MDnCgn6lUcjzOg4GXrN13bQ&hl=en&sa=X&ved=0ahUKEwjk9L_18rrbAhVpKcAKHX_KA7EQ6AEIWTAI#v=onepage&q=ambassador%20pattern%20in%20real%20world&f=false)
